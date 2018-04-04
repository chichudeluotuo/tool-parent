/**
 * Project Name:tool-zookeeper
 * File Name:CreateSessionSimple.java
 * Package Name:com.luotuo.tool.zookeeper.client.zkclient
 * Date:2018年4月4日下午12:44:58
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * ClassName:CreateSessionSimple <br/>
 * Function: 使用zkclient客户端创建链接,递归创建节点. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月4日 下午12:44:58 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 */
public class CreateNodeSimple {

    public static void main(String[] args) {
        
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        
        zkClient.createPersistent("/parent/childent",true);//递归创建父节点
        
    }
}

