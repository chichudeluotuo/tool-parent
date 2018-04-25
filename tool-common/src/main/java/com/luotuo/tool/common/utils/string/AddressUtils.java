/**
 * Project Name:tool-common
 * File Name:AddressUtils.java
 * Package Name:com.luotuo.tool.common.utils.string
 * Date:2018年4月11日下午8:02:42
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.string;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:AddressUtils <br/>
 * Function: 根据地址串获取省、市、县、详细地址. <br/>
 * Date: 2018年4月11日 下午8:02:42 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public class AddressUtils {

    private static Map<String, String> provinceDictionary = new HashMap<>();
    private static Map<String, String> cityDictionary = new HashMap<>();
    private static Map<String, String> districtDictionary = new HashMap<>();

    private static String PROVINCE_KEY = "省";
    private static String CITY_KEY = "市";
    private static String DISTRICT_KEY = "县";
    private static String ZZ_PROVINCE_KEY = "区";
    private static String ZZ_CITY_KEY = "州";
    private static String BJ_KEY = "北京";
    private static String TJ_KEY = "天津";
    private static String CQ_KEY = "重庆";
    private static String SH_KEY = "上海";
    private static String XG_TBXZQ_KEY="香港";
    private static String AM_TBXZQ_KEY="澳门";
    
    private static String BJ_CODE_PREFIX = "11";
    private static String CQ_CODE_PREFIX = "50";
    private static String SH_CODE_PREFIX = "31";
    private static String TJ_CODE_PREFIX = "12";
    private static String XG_CODE_PREFIX = "81";
    private static String AM_CODE_PREFIX = "82";
    
    private static Properties p;
    //数据准备
    static {
         p = new Properties();
        try {
            p.load(
                    new InputStreamReader(AddressUtils.class.getResourceAsStream("/dictionary/address.properties"), "utf-8"));
            for (String code : p.stringPropertyNames()) {
                int codeInt = Integer.parseInt(code);
                if (codeInt % 10000 == 0) {//获得省、直辖市、自治区、特别行政区
                    provinceDictionary.put(p.getProperty(code), code);
                } else if (codeInt % 100 == 0 && codeInt % 10000 != 0) {//获得市
                    cityDictionary.put(p.getProperty(code), code);
                } else {//获取县区
                    districtDictionary.put(p.getProperty(code), code);
                }

            }
        } catch (IOException e) {

        }
    }
    /**
     * 分解地址串，判断地址是否正确，并输出
     * @author 陈亚勇
     * @param list
     * @param stringTokenizer
     * @param shengAddress
     * @param shiAddress
     * @param xianAddress
     * @return
     */
public static List<String> correctAddress(List<String> list, StringTokenizer stringTokenizer,String shengAddress,String shiAddress,String xianAddress){
	if(stringTokenizer == null){
		return null;
	}
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (list.size() == 1) {
                if (!checkCascade(//
                        provinceDictionary.get(list.get(0).concat(shengAddress)), //
                        cityDictionary.get(nextToken.concat(shiAddress)), //
                        list//
                )) {
                    return list;
                }
            } else if (list.size() == 2) {
                if (!checkCascade(//
                        cityDictionary.get(list.get(1).concat(shiAddress)), //
                        districtDictionary.get(nextToken.concat(xianAddress)), //
                        list//
                )) {
                    return list;
                }
            }

            list.add(nextToken);
        }
        return list;
}
    /**
     * splitAddress:(地址截取具体处理). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @since JDK 1.8
     */
    public static List<String> splitAddress(String addressStr) {

        List<String> list = new ArrayList<String>();

        //情况1.地址包含直辖市
        if (addressStr.startsWith(BJ_KEY)) {
            return municipality(addressStr, list, BJ_KEY, BJ_CODE_PREFIX);
        }
        if (addressStr.startsWith(TJ_KEY)) {
            return municipality(addressStr, list, TJ_KEY, TJ_CODE_PREFIX);
        }
        if (addressStr.startsWith(SH_KEY)) {
            return municipality(addressStr, list, SH_KEY, SH_CODE_PREFIX);
        }
        if (addressStr.startsWith(CQ_KEY)) {
            return municipality(addressStr, list, CQ_KEY, CQ_CODE_PREFIX);
        }
        if(addressStr.startsWith(XG_TBXZQ_KEY)){
        	 return municipalityTbzxq(addressStr, list, XG_TBXZQ_KEY, XG_CODE_PREFIX);
        }
        if(addressStr.startsWith(AM_TBXZQ_KEY)){
        	 return municipalityTbzxq(addressStr, list, AM_TBXZQ_KEY, AM_CODE_PREFIX);
        }
        //情况2.规范地名串,且输入完整[河南省濮阳市范县....]或者地址串中包含自治地区的地址串的处理
        StringTokenizer stringTokenizer = new StringTokenizer(addressStr, "省市县|省市区|区市县|区州县|省州县|省州区|省市市|区市市|区州市|省州市|区市区|区州区");
     if(stringTokenizer.countTokens() == 3){   
       if(!addressStr.contains("自治")){   //地址中不含有自治区、自治州、自治县
    	   if(!addressStr.contains("区")&&!addressStr.contains("县")){
    		   stringTokenizer = new StringTokenizer(addressStr, "省市市");
		        return  list = correctAddress(list,stringTokenizer,PROVINCE_KEY,CITY_KEY,CITY_KEY); 
    	   }else if(!addressStr.contains("区")&& addressStr.contains("县")){
		        stringTokenizer = new StringTokenizer(addressStr, "省市县");
		        return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,CITY_KEY,DISTRICT_KEY);
    	    }else{
    	    	 stringTokenizer = new StringTokenizer(addressStr, "省市区");
    	    	 return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,CITY_KEY,ZZ_PROVINCE_KEY);
    	    }
       }else{
    	 //区市县    区州县  区市市 区州市 区州区 区市区
    	if(addressStr.contains("自治区")){ 
    	   //此情况省级单位必为自治区县级单位则可能是县或者自治县
    	    if(!addressStr.contains("自治州")&&addressStr.contains("县")&&addressStr.contains("市")){
	    	   stringTokenizer = new StringTokenizer(addressStr, "区市县"); 
	    	   return  list =correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,CITY_KEY,DISTRICT_KEY);
	    	   
            }else if(addressStr.contains("自治州")&&addressStr.contains("县")&&!addressStr.contains("市")){
        	  stringTokenizer = new StringTokenizer(addressStr, "区州县");
        	  return  list = correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,ZZ_CITY_KEY,DISTRICT_KEY);
       	  
        	}else if(!addressStr.contains("自治州")&&!addressStr.contains("县")&&addressStr.contains("市")&&countStr(addressStr,"市")==2){
        		  stringTokenizer = new StringTokenizer(addressStr, "区市市"); 
           	   return  list =correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,CITY_KEY,CITY_KEY);
           	   
        	}else if(addressStr.contains("自治州")&&!addressStr.contains("县")&&addressStr.contains("市")){
      		  stringTokenizer = new StringTokenizer(addressStr, "区州市"); 
          	   return  list = correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,ZZ_CITY_KEY,CITY_KEY);
          	   
       	    }else if(addressStr.contains("自治州")&&!addressStr.contains("县")&&!addressStr.contains("市")){
       	    	stringTokenizer = new StringTokenizer(addressStr, "区州区"); 
           	   return  list =correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,ZZ_CITY_KEY,ZZ_PROVINCE_KEY);
           	   
       	    }else if(!addressStr.contains("自治州")&&!addressStr.contains("县")&&addressStr.contains("市")&&countStr(addressStr,"市")==1){
       	    	stringTokenizer = new StringTokenizer(addressStr, "区市区"); 
            	   return  list =correctAddress(list,stringTokenizer,ZZ_PROVINCE_KEY,CITY_KEY,ZZ_PROVINCE_KEY);
            	   
        	}
    	}else{ //省州县 省市县 省市市  省州市 省州区
		    	   if(!addressStr.contains("自治州")&& addressStr.contains("县")){//省市县，此县必为自治县
		    		   stringTokenizer = new StringTokenizer(addressStr, "省市县");
		    		   return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,CITY_KEY,DISTRICT_KEY);
	    	       
		    	   }else if(!addressStr.contains("自治州")&& !addressStr.contains("县")){
		    		   stringTokenizer = new StringTokenizer(addressStr, "省市市");
		    		   return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,CITY_KEY,CITY_KEY);  
		    	   }else{
		    		   if(!addressStr.contains("区")&& addressStr.contains("县")){
		    			   stringTokenizer = new StringTokenizer(addressStr, "省州县");
			    		   return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,ZZ_CITY_KEY,DISTRICT_KEY);  
		    		   }else if(!addressStr.contains("区")&& addressStr.contains("市")){
		    			   stringTokenizer = new StringTokenizer(addressStr, "省州市");
			    		   return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,ZZ_CITY_KEY,CITY_KEY); 
		    		   }else{
		    			   stringTokenizer = new StringTokenizer(addressStr, "省州区");
			    		   return  list =correctAddress(list,stringTokenizer,PROVINCE_KEY,ZZ_CITY_KEY,ZZ_PROVINCE_KEY); 
		    		   }
		    	   }
		       }
       }
     }
    	
        String provinceCode = null;
        //情况3.规范地名,但输入不完整
        for (int i = 2; i <= addressStr.length(); i++) {//获得省级区域
            String provinceName = null;
            if (addressStr.contains(PROVINCE_KEY) || addressStr.contains("自治")) {
                provinceName = addressStr.substring(0, i);
            } else {
                provinceName = addressStr.substring(0, i).concat(PROVINCE_KEY);
            }

            provinceCode = provinceDictionary.get(provinceName);
            if (null != provinceCode) {
                list.add(provinceName);
                if (i < addressStr.length()) {
                    addressStr = addressStr.substring(i);//更新地址串
                } else {
                    return list;
                }
                break;
            }else if(provinceCode==null && i==addressStr.length()){
            	 addressStr = addressStr.substring(0,i);//更新地址串
            }
            if (i > addressStr.length()) {//省份错误
                return list;
            }

        }
        String cityCode = null;
        for (int i = 2; i <= addressStr.length(); i++) {//获得市级区域
            String cityName = null;
            cityName = addressStr.substring(0, i);
            cityCode = cityDictionary.get(cityName);
            if (null != cityCode) {
            	if(null==provinceCode){//没有省级单位的情况，用市级编码得省级编码
            		provinceCode=cityCode.substring(0,2).concat("0000");
            		list.add(p.getProperty(provinceCode));
            	}
                if (!checkCascade(provinceCode, cityCode, list)) {
                    return list;
                }
                list.add(cityName);
                if (i < addressStr.length()) {
                    addressStr = addressStr.substring(i);//更新地址串
                } else {
                    return list;
                }
                break;
            }else if(null == cityCode && i == addressStr.length()){//chenyayong
            	   addressStr = addressStr.substring(0,i);//更新地址串
            }
            if (i > addressStr.length()) {//市级错误
                return list;
            }

        }
        String districtCode = null;
        for (int i = 1; i <= addressStr.length(); i++) {//获得县级区域
            String districtName = null;
            districtName = addressStr.substring(0, i);
            districtCode = districtDictionary.get(districtName);
            if (null != districtCode) {
            	if(cityCode==null&&provinceCode!=null){//没有市级单位的情况，用县级编码得省级编码
            		cityCode=districtCode.substring(0,4).concat("00");
            		if(checkCascade(provinceCode, cityCode, list)){//判读由县级单位推出的市级单位与省级单位是否是上下级关系
            			list.add(p.getProperty(cityCode));//是 则把市级单位放入list
            		}else{
            			cityCode=null;//不是 则把cityCode重新置空
            		}
            	}
            	if(cityCode==null&&provinceCode==null){
            		provinceCode=districtCode.substring(0,2).concat("0000");
            		list.add(p.getProperty(provinceCode));
            		cityCode=districtCode.substring(0,4).concat("00");
            		list.add(p.getProperty(cityCode));
            	}
                if (!checkCascade(cityCode, districtCode, list)) {
                    return list;
                }
                list.add(districtName);
                if (i < addressStr.length()) {
                    list.add(addressStr.substring(i));//添加详细地址
                } else {
                    return list;
                }
                break;
            }
            if (i == addressStr.length()) {//县级区域错误
                return list;
            }

        }
       
        return list;
    }

    /**
     * municipality:(包含直辖市地址处理). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @param list
     * @return
     * @since JDK 1.8
     */
    private static List<String> municipality(String addressStr, List<String> list, String cityKey, String municipalityCodePrefix) {

        if (addressStr.startsWith(cityKey)) {
            if (addressStr.startsWith(CITY_KEY, 2)) {//北京市
                if (addressStr.startsWith(cityKey, 3)) {//北京市北京
                    list.add(addressStr.substring(0, 3));
                    return municipalitySplit(addressStr.substring(3), list, municipalityCodePrefix);
                } else {//北京市海淀
                    return municipalitySplit(addressStr, list, municipalityCodePrefix);
                }
            } else {
                if (addressStr.startsWith(cityKey, 2)) {//北京北京[市]
                    list.add(addressStr.substring(0, 2));
                    return municipalitySplit(addressStr.substring(2), list, municipalityCodePrefix);
                } else {//北京[市]海淀
                    return municipalitySplit(addressStr, list, municipalityCodePrefix);
                }
            }
        }

        return list;
    }

    /**
     * bjMunicipality:(处理:北京[市]海淀区XXX). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @param list
     * @return
     * @since JDK 1.8
     */
    private static List<String> municipalitySplit(String addressStr, List<String> list, String codePrefix) {//北京[市]海淀区

        String bjCode = null;
        String cityName = null;
        if (addressStr.contains(CITY_KEY)) {
            cityName = addressStr.substring(0, 3);
            bjCode = provinceDictionary.get(cityName);
        } else {
            cityName = addressStr.substring(0, 2);
            bjCode = provinceDictionary.get(cityName);
            bjCode = provinceDictionary.get(cityName.concat(CITY_KEY));
        }

        if (null != bjCode) {
            list.add(cityName);
            addressStr = addressStr.substring(cityName.length());
        }

        for (int i = 2; i <= addressStr.length(); i++) {
            String districtName = addressStr.substring(0, i);
            String districtCode = districtDictionary.get(districtName);
            if (null != districtCode) {
                if (districtCode.startsWith(codePrefix)) {
                    list.add(districtName);
                    list.add(addressStr.substring(districtName.length()));
                    return list;
                } else {
                    return list;
                }
            }
        }

        return list;
    }

    /**
     * checkCascade:(校验上下级行政区是否对应). <br/>
     *
     * @author 鲁济良
     * @param topCode
     * @param bottomCode
     * @param list
     * @return
     * @since JDK 1.8
     */
    private static boolean checkCascade(String topCode, String bottomCode, List<String> list) {

        if (StringUtils.isBlank(topCode) || StringUtils.isBlank(bottomCode)) {
            return false;
        }

        int topInt = Integer.parseInt(topCode);
        int bottomInt = Integer.parseInt(bottomCode);
        if (list.size() < 2) {
            if (Math.abs(bottomInt - topInt) < 10000) {//省市
                return true;
            }
        } else {
            if (Math.abs(bottomInt - topInt) < 100) {//市县
                return true;
            }
        }
        return false;
    }

    /**
     * getProvince:(根据地址串获取省). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @return 省
     * @since JDK 1.8
     */
    public static String getProvince(String addressStr) {

        List<String> list = splitAddress(addressStr);
        if (list.size() >= 1) {
            return list.get(0);
        }
        return "";
    }

    /**
     * getCity:(根据地址串获取市). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @return 市
     * @since JDK 1.8
     */
    public static String getCity(String addressStr) {

        List<String> list = splitAddress(addressStr);
        if (list.size() >= 2) {
            return list.get(1);
        }
        return "";
    }

    /**
     * getDistrict:(根据地址串获取区、县). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @return 区、县
     * @since JDK 1.8
     */
    public static String getDistrict(String addressStr) {

        List<String> list = splitAddress(addressStr);
        if (list.size() >= 3) {
            return list.get(2);
        }
        return "";
    }

    /**
     * getDistrict:(根据地址串获区详细地址). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @return 区、县
     * @since JDK 1.8
     */
    public static String getDetail(String addressStr) {

        List<String> list = splitAddress(addressStr);
        if (list.size() >= 4) {
            return list.get(3);
        }
        return "";
    }
    
    /**
     * municipality:(包含特别行政区的地址处理). <br/>
     *
     * @author 陈亚勇
     * @param addressStr
     * @param list
     * @return
     * @since JDK 1.8
     *   因为提供的资源文件里没有特别行政区的下级机构，所以一切含有特别行政区的地址输出都与资源文件一致
     */
    private static List<String> municipalityTbzxq(String addressStr, List<String> list, String cityKey, String municipalityCodePrefix) {

        if (addressStr.startsWith(cityKey)) {
           String code=municipalityCodePrefix.concat("0000");
           addressStr=p.getProperty(code);
           list.add(addressStr);
        }

        return list;
    }
    /**
     * 计算某个字符串中含某个字符的个数
     * @author 陈亚勇
     * @param addtress
     * @param str
     */
    private static int countStr(String addtress,String str){
	      int count=0;
		  if(addtress.indexOf(str)==-1){
				return 0;
		  }
		while(addtress.indexOf(str)!=-1){
			count++;
			addtress=addtress.substring(addtress.indexOf(str)+str.length());
		}
		return count;
    }
    }
