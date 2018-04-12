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
    private static String BJ_KEY = "北京";
    private static String TJ_KEY = "天津";
    private static String CQ_KEY = "重庆";
    private static String SH_KEY = "上海";

    private static String BJ_CODE_PREFIX = "11";
    private static String CQ_CODE_PREFIX = "50";
    private static String SH_CODE_PREFIX = "31";
    private static String TJ_CODE_PREFIX = "12";

    static {
        Properties p = new Properties();
        try {
            p.load(
                    new InputStreamReader(AddressUtils.class.getResourceAsStream("/dictionary/address.properties"), "utf-8"));
            for (String code : p.stringPropertyNames()) {
                int codeInt = Integer.parseInt(code);
                if (codeInt % 10000 == 0) {//获得省、直辖市
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

        //情况2.规范地名串,且输入完整[河南省濮阳市范县王楼乡....]
        StringTokenizer stringTokenizer = new StringTokenizer(addressStr, "省市县");
        if (stringTokenizer.countTokens() == 4) {

            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                if (list.size() == 1) {
                    if (!checkCascade(//
                            provinceDictionary.get(list.get(0).concat(PROVINCE_KEY)), //
                            cityDictionary.get(nextToken.concat(CITY_KEY)), //
                            list//
                    )) {
                        return list;
                    }
                } else if (list.size() == 2) {
                    if (!checkCascade(//
                            cityDictionary.get(list.get(1).concat(CITY_KEY)), //
                            districtDictionary.get(nextToken.concat(DISTRICT_KEY)), //
                            list//
                    )) {
                        return list;
                    }
                }

                list.add(nextToken);
            }

            return list;
        }

        String provinceCode = null;
        //情况3.规范地名,但输入不完整
        for (int i = 2; i <= addressStr.length(); i++) {//获得省级区域
            String provinceName = null;
            if (addressStr.contains(PROVINCE_KEY)) {
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
            }
            if (i == addressStr.length()) {//省份错误
                return list;
            }

        }
        String cityCode = null;
        for (int i = 2; i <= addressStr.length(); i++) {//获得市级区域
            String cityName = null;
            cityName = addressStr.substring(0, i);
            cityCode = cityDictionary.get(cityName);
            if (null != cityCode) {
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
            }
            if (i == addressStr.length()) {//市级错误
                return list;
            }

        }
        String districtCode = null;
        for (int i = 1; i <= addressStr.length(); i++) {//获得县级区域
            String districtName = null;
            districtName = addressStr.substring(0, i);
            districtCode = districtDictionary.get(districtName);
            if (null != districtCode) {
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
        if (list.size() > 1) {
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
        if (list.size() > 2) {
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
        if (list.size() > 3) {
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
        if (list.size() > 4) {
            return list.get(3);
        }
        return "";
    }

}
