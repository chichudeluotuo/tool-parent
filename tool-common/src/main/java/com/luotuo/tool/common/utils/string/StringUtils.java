/**
 * Project Name:tool-common
 * File Name:StringUtils.java
 * Package Name:com.luotuo.tool.common.utils.string
 * Date:2018年3月2日下午1:42:34
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.string;

import java.nio.charset.Charset;

/**
 * ClassName:StringUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月2日 下午1:42:34 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public final class StringUtils {

    private static Charset charset = Charset.forName("UTF8");

    public String deserializeRedis(byte[] bytes) {
        return bytes == null ? null : new String(bytes, charset);
    }

    public byte[] serializeRedis(String string) {
        return string == null ? null : string.getBytes(charset);
    }

}
