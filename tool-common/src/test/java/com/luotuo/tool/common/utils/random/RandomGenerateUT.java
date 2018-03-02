/**
 * Project Name:tool-common
 * File Name:RandomGenerateUT.java
 * Package Name:com.luotuo.tool.common.utils.random
 * Date:2018年3月2日下午4:30:42
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.random;

import org.junit.Test;

/**
 * ClassName:RandomGenerateUT <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年3月2日 下午4:30:42 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class RandomGenerateUT {

    @Test
    public void random_Test() {
        for (int i = 1; i < 10000; i+=1000) {
            
            float randomWH = randomWH(i);
            System.out.println(randomWH +"---"+i);
        }
    }
    
    public float randomWH(Integer x) {
        int[] seed = new int[3];
        seed[0] = (171 * x) % 30269;
        seed[1] = (172 * (30000 - x)) % 30307;
        seed[2] = (170 * x) % 30323;
        return (x / Math.abs(x)) 
                * (seed[0] / 30269.0F + seed[1] / 30307.0F + seed[2] / 30323.0F) % 1.0F; 
     }
    
}

