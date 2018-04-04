/**
 * Project Name:tool-zookeeper
 * File Name:GetChildentAndListenerSimple.java
 * Package Name:com.luotuo.tool.zookeeper.client.zkclient
 * Date:2018年4月4日下午1:12:02
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * ClassName:GetChildentAndListenerSimple <br/>
 * Function: 获得节点并设置监听. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月4日 下午1:12:02 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class GetChildentAndListenerSimple {


    private static String path = "/testNode";
    
    public static void main(String[] args) throws Exception {
        
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        
        //设置节点变化监听
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                
                System.out.println(parentPath+" Node childent change currentChildent Node:"+currentChilds);
                
            }
        });
        
        zkClient.createPersistent(path);
        Thread.sleep(100);
        
        zkClient.createPersistent(path+"/a");
        Thread.sleep(100);
        
        zkClient.delete(path+"/a");
        Thread.sleep(100);
        
        zkClient.delete(path);
        
        Thread.sleep(Integer.MAX_VALUE);
        
        
    }
    
    
}

