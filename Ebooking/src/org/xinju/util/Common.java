package org.xinju.util;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.parserapplications.filterbuilder.Filter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.xinju.bean.FreightBean;

public class Common {
	
	public static String nowDate(String format)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
		return df.format(new Date());
	}

	public static ArrayList<String> paginationList(String firstPageHtmlString) {
		// TODO Auto-generated method stub
		ArrayList<String> retpaginationList = new ArrayList<String>();
		try {
			Parser htmlParser = new Parser(firstPageHtmlString);
			NodeList pageNodeList = htmlParser.parse(new HasAttributeFilter("class", "pagination_h"));
			Node node = pageNodeList.elementAt(0);
			Parser liParser = new Parser(node.toHtml());
			NodeList liNodeList = liParser.parse(new TagNameFilter("li"));
			int liNodeListSize = liNodeList.size();
			if ( liNodeListSize > 3) {
				//from second page,to last page.
				for (int liNodeIndex = 2; liNodeIndex < liNodeListSize-1; liNodeIndex++) {
					Node pageNode = liNodeList.elementAt(liNodeIndex);
					Parser urlParser = new Parser(pageNode.toHtml());
					NodeList urlNodeList = urlParser.parse(new TagNameFilter("a"));
					if (urlNodeList.size() == 1) {
						LinkTag  link  = (LinkTag) urlNodeList.elementAt(0);
						String linkString = link.getLink();
						//process url encode:t/0-0-0-0-0-9-9-CNSHA-0-0-0-AEJEA-JEBEL`ALI-0-0-PRICE_20_asc-15-2.shtml': escaped absolute path not valid
						int split = linkString.lastIndexOf("/")+1;
						String preLinkString = linkString.substring(0, split);
						String sufixLinkString = linkString.substring(split);
						sufixLinkString = URLEncoder.encode(sufixLinkString);
						linkString = "http://www.sinotransbooking.com" + preLinkString + sufixLinkString;
						//String linkString = "http://www.sinotransbooking.com"+link.getLink();
						retpaginationList.add(linkString);
					}
				}
			}
			
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retpaginationList;
	}

	public static List<FreightBean> getFreightBeans(String firstPageHtmlString) {
		// TODO Auto-generated method stub
		List<FreightBean> retFreightBeans = new ArrayList<FreightBean>();		
		try {
			Parser divParser = new Parser(firstPageHtmlString);
			NodeList divNodeList = divParser.parse(new HasAttributeFilter("class", "mainBox"));
			if (divNodeList.size() == 0) {
				return null;
			}
			int divNodeListSize = divNodeList.size();
			for (int divNodelistIndex = 0; divNodelistIndex < divNodeListSize; divNodelistIndex++) {
				Node divNode = divNodeList.elementAt(divNodelistIndex);

				FreightBean freightBean = new FreightBean();
				//get carrier
				Parser carrierParser = new Parser(divNode.toHtml());
				NodeList carrierNodeList = carrierParser.parse(new HasAttributeFilter("name", "accountName"));
				if (carrierNodeList.size()==0) {
					continue;
				}
				Node node = carrierNodeList.elementAt(0);
				String carrierString = node.toPlainTextString().trim();
				carrierString = Converter.sinotransbookingCarrier2SoushippingCarrier(carrierString);
				if (carrierString == null) {
					continue;
				}
				freightBean.setCarrier(carrierString);
				//get price
				Parser ulParser = new Parser(divNode.toHtml());
				NodeList ulNodeList = ulParser.parse(new HasAttributeFilter("class","price"));
				
				if (ulNodeList.size()==0) {
					continue;
				}
				Node ulNode = ulNodeList.elementAt(0);
				Parser liParser = new Parser(ulNode.toHtml());
				NodeList liNodeList = liParser.parse(new TagNameFilter("li"));
				int liNodeListSize = liNodeList.size();
				if ( liNodeListSize != 3) {
					continue;
				}
				// set FreightBean;
				freightBean.setGp20(liNodeList.elementAt(0).toPlainTextString().trim());
				freightBean.setGp40(liNodeList.elementAt(1).toPlainTextString().trim());
				freightBean.setHq40(liNodeList.elementAt(2).toPlainTextString().trim());
				
				//get valid data
				Parser invalidParser = new Parser(divNode.toHtml());
				NodeList invalidNodeList = invalidParser.parse(new HasAttributeFilter("name", "validTimeTo"));
				if (invalidNodeList.size()==0) {
					continue;
				}
				Node invalidnode = invalidNodeList.elementAt(0);
				String invalidString = invalidnode.toPlainTextString().trim();
				//SimpleDateFormat sdfDateFormat = new SimpleDateFormat("MM-dd");
				//Date invalidDate = sdfDateFormat.parse(invalidString);
				//sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//freightBean.setInvalid(sdfDateFormat.format(invalidDate));
				Calendar cal = Calendar.getInstance();
				invalidString = cal.get(Calendar.YEAR)+"-"+invalidString;
				freightBean.setInvalid(invalidString);
				
				SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String validDate = sdfDateFormat.format(currentDate);
				freightBean.setValid(validDate);
				retFreightBeans.add(freightBean);
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retFreightBeans;
	}
}
