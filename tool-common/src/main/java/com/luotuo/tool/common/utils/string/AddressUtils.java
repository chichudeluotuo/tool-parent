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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
     * getProvince:(根据地址串获取省). <br/>
     *
     * @author 鲁济良
     * @param addressStr
     * @return 省
     * @since JDK 1.8
     */
    public static String getProvince(String addressStr) {

        return null;
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

        return null;
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

        return null;
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

        return null;
    }

    public static void main(String[] args) {

    }

}
