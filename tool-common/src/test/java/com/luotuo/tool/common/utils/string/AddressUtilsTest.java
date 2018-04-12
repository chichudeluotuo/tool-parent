/**
 * Project Name:tool-common
 * File Name:AddressUtilsTest.java
 * Package Name:com.luotuo.tool.common.utils.string
 * Date:2018年4月12日上午8:52:54
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.string;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * ClassName:AddressUtilsTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月12日 上午8:52:54 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class AddressUtilsTest {

    @Test
    public void splitAddress() {

        String addressStr = "吉林省延边朝鲜族自治州延吉市";
        

        List<String> splitAddress = AddressUtils.splitAddress(addressStr);

        for (String string : splitAddress) {
            System.out.println(string);
        }

    }
    
}

