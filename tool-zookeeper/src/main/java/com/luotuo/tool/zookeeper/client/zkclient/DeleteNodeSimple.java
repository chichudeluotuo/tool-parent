/**
 * Project Name:tool-zookeeper
 * File Name:DeleteNodeSimple.java
 * Package Name:com.luotuo.tool.zookeeper.client.zkclient
 * Date:2018年4月4日下午1:07:11
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * ClassName:DeleteNodeSimple <br/>
 * Function: 递归删除节点. <br/>
 * Date:     2018年4月4日 下午1:07:11 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class DeleteNodeSimple {

    public static void main(String[] args) {
        
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        
        boolean b = zkClient.deleteRecursive("/parent");
        System.out.println(b);
        
        
    }
    
}

