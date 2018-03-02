/**
 * Project Name:tool-common
 * File Name:DateUtilsUT.java
 * Package Name:com.luotuo.tool.common.utils.date
 * Date:2018年3月1日下午9:06:28
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.date;

import java.util.Arrays;
import java.util.TimeZone;

import org.junit.Test;

/**
 * ClassName:DateUtilsUT <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月1日 下午9:06:28 <br/>
 * 
 * @author 鲁济良
 * @since JDK 1.8
 */
public class DateUtilsUT {

    /**
     * timeZone_test:(测试时区). <br/>
     *
     * @author 鲁济良
     * @since JDK 1.8
     */
    @Test
    public void timeZone_test() {

        String[] availableIDs = TimeZone.getAvailableIDs();
        System.out.println(Arrays.toString(availableIDs));
    }
}
