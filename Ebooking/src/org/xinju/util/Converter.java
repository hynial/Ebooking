/**
 * 
 */
package org.xinju.util;

import com.xinju.bean.EpriceBean;
import com.xinju.bean.FreightBean;
import com.xinju.bean.Get56ResultBean;

/**
 * @author skanda
 * @desc ebooking converter.
 */
public class Converter {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * @param EpriceBean
	 * @target: convert EpriceBean to FreightBean;
	 */
	public static FreightBean EpriceBean2FreightBean(EpriceBean epriceBean) {
		// TODO Auto-generated method stub
		FreightBean freightBean = new FreightBean();
		String eBookingCarrierCode = epriceBean.getCarrierCode();
		freightBean.setCarrier(eBookingCarrier2SoushippingCarrier(eBookingCarrierCode));
		freightBean.setGp20(epriceBean.getHyfPrice20());
		freightBean.setGp40(epriceBean.getHyfPrice40());
		freightBean.setHq40(epriceBean.getHyfPrice40Hq());
		freightBean.setValid(epriceBean.getStartDate());
		freightBean.setInvalid(epriceBean.getEndDate());
		return freightBean;
	}
	
	public static String eBookingCarrier2SoushippingCarrier(String eBookingCarrier){
		String retSoushippingCarrier=null;
		if(eBookingCarrier.equals("MAERSK"))
		{
			retSoushippingCarrier = "MSK";
		}else if (eBookingCarrier.equals("EMI")) {
			retSoushippingCarrier = "EMIRATES";
		}else if (eBookingCarrier.equals("EMC")) {
			retSoushippingCarrier = "EVERGREEN";
		}else if (eBookingCarrier.equals("HAM-SUD")) {
			retSoushippingCarrier = "HAMBURG SüD";
		}else if (eBookingCarrier.equals("HPL")) {
			retSoushippingCarrier = "HAPAG-LLOYD";
		}else if (eBookingCarrier.equals("KLINE")) {
			retSoushippingCarrier = "K.LINE";
		}else if (eBookingCarrier.equals("NCL")) {
			retSoushippingCarrier = "NORASIA";
		}else if (eBookingCarrier.equals("WHL")) {
			retSoushippingCarrier = "WANHAI";
		}else if (eBookingCarrier.equals("YML")) {
			retSoushippingCarrier = "YANGMING";
		}else {
			retSoushippingCarrier = eBookingCarrier;
		}
		
		
		return retSoushippingCarrier;
	}


	public static FreightBean get56Bean2FreightBean(
			Get56ResultBean get56ResultBean) {
		// TODO Auto-generated method stub
		FreightBean freightBean = new FreightBean();
		String get56CarrierCode = get56ResultBean.getChsnm();
		freightBean.setCarrier(get56Carrier2SoushippingCarrier(get56CarrierCode));
		freightBean.setGp20(get56ResultBean.getSs_20pr());
		freightBean.setGp40(get56ResultBean.getSs_40pr());
		freightBean.setHq40(get56ResultBean.getSs_40hq());
		return freightBean;
	}


	private static String get56Carrier2SoushippingCarrier(
			String get56CarrierCode) {
		String retSoushippingCarrier=null;
		if ( get56CarrierCode.equals("ANL")) {
			retSoushippingCarrier = "ANL";
		}else if ( get56CarrierCode.equals("APL") ) {
			retSoushippingCarrier = "APL";
		}else if ( get56CarrierCode.equals("CMA") ) {
			retSoushippingCarrier = "CMA";
		}else if ( get56CarrierCode.equals("CNC") ) {
			retSoushippingCarrier = "CNC";
		}else if ( get56CarrierCode.equals("COSCO") ) {
			retSoushippingCarrier = "COSCO";
		}else if ( get56CarrierCode.equals("CSAV") ) {
			retSoushippingCarrier = "CSAV";
		}else if ( get56CarrierCode.equals("CSCL") ) {
			retSoushippingCarrier = "CSCL";
		}else if ( get56CarrierCode.equals("DELMAS") ) {
			retSoushippingCarrier = "DELMAS";
		}else if ( get56CarrierCode.equals("ESL") ) {
			retSoushippingCarrier = "EMIRATES";
		}else if ( get56CarrierCode.equals("EMC") ) {
			retSoushippingCarrier = "EVERGREEN";
		}else if ( get56CarrierCode.equals("HAMBURG SUD") ) {
			retSoushippingCarrier = "HAMBURG SüD";
		}else if ( get56CarrierCode.equals("HANJIN") ) {
			retSoushippingCarrier = "HANJIN";
		}else if ( get56CarrierCode.equals("HPL") ) {
			retSoushippingCarrier = "HAPAG-LLOYD";
		}else if ( get56CarrierCode.equals("HMM") ) {
			retSoushippingCarrier = "HMM";
		}else if ( get56CarrierCode.equals("KLINE") ) {
			retSoushippingCarrier = "K.LINE";
		}else if ( get56CarrierCode.equals("MCC") ) {
			retSoushippingCarrier = "MCC";
		}else if ( get56CarrierCode.equals("MOSK") ) {
			retSoushippingCarrier = "MOL";
		}else if ( get56CarrierCode.equals("MSC") ) {
			retSoushippingCarrier = "MSC";
		}else if ( get56CarrierCode.equals("MSK") ) {
			retSoushippingCarrier = "MSK";
		}else if ( get56CarrierCode.equals("NYK") ) {
			retSoushippingCarrier = "NYK";
		}else if ( get56CarrierCode.equals("PIL") ) {
			retSoushippingCarrier = "PIL";
		}else if ( get56CarrierCode.equals("SAF") ) {
			retSoushippingCarrier = "SAFMARINE";
		}else if ( get56CarrierCode.equals("UASC") ) {
			retSoushippingCarrier = "UASC";
		}else if ( get56CarrierCode.equals("WHL") ) {
			retSoushippingCarrier = "WANHAI";
		}else if ( get56CarrierCode.equals("YML") ) {
			retSoushippingCarrier = "YANGMING";
		}else if ( get56CarrierCode.equals("ZIM") ) {
			retSoushippingCarrier = "ZIM";
		}
		
		return retSoushippingCarrier;
	}

	public static String sinotransbookingCarrier2SoushippingCarrier(String sinotransbookingCarrier){
		String retSoushippingCarrier=null;
		if(sinotransbookingCarrier.equals("ANL"))
		{
			retSoushippingCarrier = "ANL";
		}else if (sinotransbookingCarrier.equals("APL")){
			retSoushippingCarrier = "APL";
		}else if (sinotransbookingCarrier.equals("CMA")){
			retSoushippingCarrier = "CMA";
		}else if (sinotransbookingCarrier.equals("CNC")){
			retSoushippingCarrier = "CNC";
		}else if (sinotransbookingCarrier.equals("COSCO")){
			retSoushippingCarrier = "COSCO";
		}else if (sinotransbookingCarrier.equals("CSAV")){
			retSoushippingCarrier = "CSAV";
		}else if (sinotransbookingCarrier.equals("DELMAS")){
			retSoushippingCarrier = "DELMAS";
		}else if (sinotransbookingCarrier.equals("ESL")){
			retSoushippingCarrier = "EMIRATES";
		}else if (sinotransbookingCarrier.equals("EVG")){
			retSoushippingCarrier = "evergreen";
		}else if (sinotransbookingCarrier.equals("HBGS")){
			retSoushippingCarrier = "HAMBURG SüD";
		}else if (sinotransbookingCarrier.equals("HJ")){
			retSoushippingCarrier = "HANJIN";
		}else if (sinotransbookingCarrier.equals("HMM")){
			retSoushippingCarrier = "HMM";
		}else if (sinotransbookingCarrier.equals("KL")){
			retSoushippingCarrier = "K.LINE";
		}else if (sinotransbookingCarrier.equals("MCC")){
			retSoushippingCarrier = "MCC";
		}else if (sinotransbookingCarrier.equals("MOL")){
			retSoushippingCarrier = "MOL";
		}else if (sinotransbookingCarrier.equals("MSC")){
			retSoushippingCarrier = "MSC";
		}else if (sinotransbookingCarrier.equals("MSL")){
			retSoushippingCarrier = "MSK";
		}else if (sinotransbookingCarrier.equals("NYK")){
			retSoushippingCarrier = "NYK";
		}else if (sinotransbookingCarrier.equals("PIL")){
			retSoushippingCarrier = "PIL";
		}else if (sinotransbookingCarrier.equals("SAF")){
			retSoushippingCarrier = "SAF";
		}else if (sinotransbookingCarrier.equals("UASC")){
			retSoushippingCarrier = "UASC";
		}else if (sinotransbookingCarrier.equals("WHL")){
			retSoushippingCarrier = "WHL";
		}else if (sinotransbookingCarrier.equals("YML")){
			retSoushippingCarrier = "YML";
		}else if (sinotransbookingCarrier.equals("ZIM")){
			retSoushippingCarrier = "ZIM";
		}
		
		return retSoushippingCarrier;
	}
}
