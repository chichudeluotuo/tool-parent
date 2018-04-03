/**
 * Project Name:tool-zookeeper
 * File Name:ZookeeperConstructorUsageSimple.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月3日下午3:21:59
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * ClassName:ZookeeperConstructorUsageSimple <br/>
 * Function: Java客户端原始API简单使用. <br/>
 * Date:     2018年4月3日 下午3:21:59 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class ZookeeperConstructorUsageSimple implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    
    /**
     * main:(创建链接和一个基本的Zookeeper会话). <br/>
     * TODO(描述这个方法的注意事项 – 可选).<br/>
     * @author 鲁济良
     * @param args
     * @throws IOException
     * @since JDK 1.8
     */
    public static void main(String[] args) throws IOException {
        
        //服务器地址
        //超时时间
        //注册器
        ZooKeeper zkServer = new ZooKeeper("127.0.0.1:2181",5000,new ZookeeperConstructorUsageSimple());
        System.out.println(zkServer.getState());
        
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            
            System.out.println("zookeeper session establish");
            
        }
        
    }

    public void process(WatchedEvent event) {//当接到服务端的通知后，解除主程序线程等待。
        
        System.out.println("Receive watcher event :"+ event);

        if(KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }
        
    }
    
}

