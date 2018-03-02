/**
 * Project Name:tool-common
 * File Name:RandomGenerate.java
 * Package Name:com.luotuo.tool.common.utils.random
 * Date:2018年3月2日下午4:27:13
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.random;

/**
 * ClassName:RandomGenerate <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月2日 下午4:27:13 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public class RandomGenerate {

    /**
     * randomWH:(Wichman-Hill 随机数产生器。Excel的随机函数曾用的方法). <br/>
     *
     * @author 鲁济良
     * @param x
     *            基准数据(1-n)
     * @return A float random value between [0.0,1.0)
     * @since JDK 1.8
     * @see 不管x如何变化，产生数据呈现了线性的趋势，波动很小
     */
    public static float randomWH(int x) {
        int[] seed = new int[3];
        seed[0] = (171 * x) % 30269;
        seed[1] = (172 * (30000 - x)) % 30307;
        seed[2] = (170 * x) % 30323;
        return (x / Math.abs(x))
                * (seed[0] / 30269.0F + seed[1] / 30307.0F + seed[2] / 30323.0F) % 1.0F;
    }

    /**
     * randomRSA:(RSA 随机数产生器). <br/>
     * TODO(公式：C = (x * exp P) mod N（P是质数，N是两个质数之积）).<br/>
     *
     * @author 鲁济良
     * @param x
     *            基准数据(1-n)
     * @return A float random value between [0.0,1.0)
     * @since JDK 1.8
     * @see 算法的得到的数据波动很大，随机性很好
     */
    public static float randomRSA(int x) {
        //可以添加获得质数方法，进行优化
        int[] seedRSA = new int[2];
        seedRSA[0] = 49993;
        seedRSA[1] = 49993 * 49999;
        return (float) (x * Math.exp(seedRSA[0]) % seedRSA[1] / seedRSA[1]);
    }

    /**
     * randomJava:(Java 随机数产生器). <br/>
     * TODO(描述这个方法的注意事项 – 可选).<br/>
     *
     * @author 鲁济良
     * @param x
     *            基准数据(1-n)
     * @return [0.0,1.0)
     * @since JDK 1.8
     * @see 非常优秀的随机数算法，速度快而且基本看不出规律
     */
    public static float randomJava(java.lang.Integer x) {
        return (float) (new java.util.Random(1000 * x).nextDouble()); //乘1000来让种子间差距增大
    }
}
