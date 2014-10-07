import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.xinju.util.ArrayAdapterFactory;
import org.xinju.util.Common;
import org.xinju.util.Converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xinju.bean.EbookingBean;
import com.xinju.bean.EpriceBean;
import com.xinju.bean.FreightBean;
import com.xinju.bean.Get56Bean;
import com.xinju.bean.Get56ResultBean;
/*
 * Desciption:
 *  运价爬虫:
 *   1,海运订舱网:http://www.sinotransbooking.com,爬取方法sinotransbooking
 *   2,宁波航运订舱平台:http://e-booking.net.cn,爬取方法Ebooking
 *   @author:adam
 *   @date:2014-9-16
 * */

public class Freight {
	/**
	 * 海运订舱网运价爬虫
	 * @param:oringHarborName,海运订舱网出发地港口名；
	 * 		  destHarborName，海运订舱网目的地港口名
	 * @return:List<FreightBean>运价列表
	*/
	public static List<FreightBean> sinotransbooking(String oringHarborName,String destHarborName){
	
		List<FreightBean> retFreightBeans = new ArrayList<FreightBean>();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 50000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(500000);
		
		PostMethod postMethod = new PostMethod("http://www.sinotransbooking.com/sinotrans-main/shipping-price-list/0-0-0-0-0-9-9-CNSHA-0-0-0-AEJEA-JEBEL%60ALI-0-0-0-0-1.shtml");
		postMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		postMethod.addRequestHeader("Accept-Language:", "zh-CN,zh;q=0.8,en;q=0.6");
		postMethod.addRequestHeader("Connection", "keep-alive");
		postMethod.addRequestHeader("Host", "www.sinotransbooking.com");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
		
		try {
			httpClient.executeMethod(postMethod);
			String firstPageHtmlString = postMethod.getResponseBodyAsString();
			ArrayList<String> paginationList = Common.paginationList(firstPageHtmlString); 
			int pageSize = paginationList.size();
			retFreightBeans = Common.getFreightBeans(firstPageHtmlString);
			//for (int i = 0; i < retFreightBeans.size(); i++) {
			//	FreightBean f = retFreightBeans.get(i);
			//	System.out.println(f.getGp20());
			//}
			if (pageSize != 0) {
				for (int paginationIndex = 0; paginationIndex < pageSize; paginationIndex++) {
					String pageUrlString = paginationList.get(paginationIndex);
					postMethod = new PostMethod(pageUrlString);
					
					httpClient.executeMethod(postMethod);
					List<FreightBean> pageFreightBeans = Common.getFreightBeans(postMethod.getResponseBodyAsString());
					if ( pageFreightBeans != null) {
						for (int i = 0; i < pageFreightBeans.size(); i++) {
							FreightBean fBean = pageFreightBeans.get(i);
							retFreightBeans.add(fBean);
						}
					}
					
				}
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retFreightBeans;
	}
	//此方法待杰西确认需求，暂忽略 
	public static List<FreightBean> get56(String oringHarborName,String destHarborName){
		List<FreightBean> retFreightBeans = new ArrayList<FreightBean>();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 50000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(500000);
		
		PostMethod postMethod = new PostMethod("http://www.get56.com/get56n/freight/editFreight.php?actioncode=getfreightcase");

		postMethod.addRequestHeader("Accept", "*/*");
		//postMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		postMethod.addRequestHeader("Accept-Language", "en,zh-CN;q=0.8,zh;q=0.6");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Connection", "keep-alive");
		//postMethod.addRequestHeader("Content-Length", "112");
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;utf-8");
		//postMethod.addRequestHeader("Cookie", "JSESSIONID=35329BC1829FA649C3D9EA4113BFEAE7; IESESSION=alive; JSESSIONID=CA92884903CAD4E355E19BC9FD0767DF; _ga=GA1.2.1561237817.1408674593");
		postMethod.addRequestHeader("Host", "www.get56.com");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("Origin", "http://www.get56.com");
		postMethod.addRequestHeader("Referer", "http://www.get56.com/get56n/logistics/onestop.shtml");

		postMethod.addParameter("page", "1");
		postMethod.addParameter("pagesize", "10");
		postMethod.addParameter("stport", oringHarborName);//"CNSHA");
		postMethod.addParameter("end_port", destHarborName);//"30996");
		String startDate = Common.nowDate("yyyy-MM-dd");
		postMethod.addParameter("start_date", startDate);
		postMethod.addParameter("start_date_end", "");
		postMethod.addParameter("gp20num", "1");
		postMethod.addParameter("gp40num", "");
		postMethod.addParameter("hc40num", "");
		
		
		try {
			//get json from get56.com
			httpClient.executeMethod(postMethod);
			//System.out.println(postMethod.getResponseBodyAsString());
			String jsonString = postMethod.getResponseBodyAsString();
			Type type  = new TypeToken<Get56Bean>(){}.getType();			
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
			Get56Bean get56Bean = gson.fromJson(jsonString, type);
			//parser data into pojo
			int get56BeanPageCount = get56Bean.getPageCount();
			int get56BeanTotalCount = get56Bean.getTotalCount();
			int get56ResultSize = get56Bean.getResult().size();
			
			ArrayList<Get56ResultBean> get56ResultBeans = (ArrayList<Get56ResultBean>) get56Bean.getResult();
			for(int get56ResultBeanIndex =0;get56ResultBeanIndex < get56ResultSize; get56ResultBeanIndex++)
			{
				Get56ResultBean get56ResultBean = get56ResultBeans.get(get56ResultBeanIndex);
				FreightBean freightBean = Converter.get56Bean2FreightBean(get56ResultBean);
				freightBean.setOringHarborName(oringHarborName);
				freightBean.setDestHarborName(destHarborName);
				if (freightBean.getCarrier()!=null) {
					retFreightBeans.add(freightBean);
				}
			}
			
			// turn page operation.
			if ( get56BeanPageCount > 1) {
				for(int pageIndex=2;pageIndex<=get56BeanPageCount;pageIndex++)
				{
					Thread.sleep(10000);
					postMethod.setParameter("page", Integer.toString(pageIndex));
					httpClient.executeMethod(postMethod);
					
					jsonString = postMethod.getResponseBodyAsString();			
					get56Bean = gson.fromJson(jsonString, type);
					//parser data into pojo
					get56ResultSize = get56Bean.getResult().size();
					 
					get56ResultBeans = (ArrayList<Get56ResultBean>) get56Bean.getResult();
					for(int get56ResultBeanIndex =0;get56ResultBeanIndex < get56ResultSize; get56ResultBeanIndex++)
					{
						Get56ResultBean get56ResultBean = get56ResultBeans.get(get56ResultBeanIndex);
						FreightBean freightBean = Converter.get56Bean2FreightBean(get56ResultBean);
						freightBean.setOringHarborName(oringHarborName);
						freightBean.setDestHarborName(destHarborName);
						if (freightBean.getCarrier()!=null) {
							retFreightBeans.add(freightBean);
						}
					}
					
					
				}
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retFreightBeans;
	}
	
	/**
	 * 宁波航运订舱平台运价爬虫
	 * @param:oringHarborName,宁波航运订舱平台出发地港口名；
	 * 		  destHarborName,宁波航运订舱平台目的地港口名
	 * @return:List<FreightBean>运价列表
	*/
	public static List<FreightBean> Ebooking(String oringHarborName,String destHarborName){
		List<FreightBean> retFreightBeans = new ArrayList<FreightBean>();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 50000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(500000);
		
		PostMethod postMethod = new PostMethod("http://e-booking.net.cn/front?_A=VPrice_QW&_mt=json&blockOrg=1&limit=40");
		
		postMethod.addRequestHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		//postMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
		postMethod.addRequestHeader("Accept-Language", "en,zh-CN;q=0.8,zh;q=0.6");
		postMethod.addRequestHeader("Cache-Control", "no-cache");
		postMethod.addRequestHeader("Connection", "keep-alive");
		//postMethod.addRequestHeader("Content-Length", "300");
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//postMethod.addRequestHeader("Cookie", "JSESSIONID=EAF5C504A10466D19E04546EB7579B63; CNZZDATA4975379=cnzz_eid%3D148141550-1408607225-%26ntime%3D1409186581");
		postMethod.addRequestHeader("Host", "http://e-booking.net.cn");
		postMethod.addRequestHeader("Pragma", "no-cache");
		postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("Origin", "http://e-booking.net.cn");
		postMethod.addRequestHeader("Referer", "http://e-booking.net.cn/booking.html");
		
		
		postMethod.addParameter("start", "0");
		postMethod.addParameter("sort2", "createTime");
		postMethod.addParameter("dir2", "DESC");
		postMethod.addParameter("sort", "sortLevel");
		postMethod.addParameter("dir", "DESC");
		postMethod.addParameter("active", "1");
		postMethod.addParameter("isDangerous", "0");
		postMethod.addParameter("isNormalPrice", "0");
		postMethod.addParameter("xml", "{\"HtRequest\":{\"HtQuery\":[{\"key\":polNameEn,\"value\":\""+oringHarborName+"\",\"op\":1},{\"key\":podNameEn,\"value\":\""+destHarborName+"\",\"op\":1}]}}");
		
		try {
			//get json via httpclent;
			httpClient.executeMethod(postMethod);
			String jsonString = postMethod.getResponseBodyAsString();
			System.out.println(postMethod.getResponseBodyAsString());
			Type type  = new TypeToken<EbookingBean>(){}.getType();			
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
			EbookingBean ebookingNameBean = gson.fromJson(jsonString, type);
			//convert list to FreightList;
			int epriceBeanSize = ebookingNameBean.getEprice().size();
			ArrayList<EpriceBean> epriceBeanList = (ArrayList<EpriceBean>) ebookingNameBean.getEprice();
			for (int epriceIndex = 0; epriceIndex < epriceBeanSize; epriceIndex++) {
				EpriceBean epriceBean = epriceBeanList.get(epriceIndex);
				FreightBean freightBean = Converter.EpriceBean2FreightBean(epriceBean);
				freightBean.setOringHarborName(oringHarborName);
				freightBean.setDestHarborName(destHarborName);
				retFreightBeans.add(freightBean);
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retFreightBeans;
	}
	public static void main(String[] args) {
		/*
		ArrayList<FreightBean> freightBeans = (ArrayList<FreightBean>)get56("CNSHA", "30319"); 
		for (int i = 0; i < freightBeans.size(); i++) {
			System.out.println(i+":------------");
			FreightBean freightBean = freightBeans.get(i);
			System.out.println(freightBean.getCarrier());
			System.out.println(freightBean.getGp20());
			System.out.println(freightBean.getGp40());
		}
		*/
		//Ebooking("NINGBO", "HAMBURG");
		//get56("CNSHA", "30319"); tokyo
		 List<FreightBean> retBeans = sinotransbooking("CNSHA", "AEJEA-JEBEL%60ALI");
		 for (int i = 0; i < retBeans.size(); i++) {
			FreightBean freightBean = retBeans.get(i);
			System.out.println(freightBean.getGp20());
		}
	}
}
