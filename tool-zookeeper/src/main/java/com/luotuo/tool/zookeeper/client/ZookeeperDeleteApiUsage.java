/**
 * Project Name:tool-zookeeper
 * File Name:ZookeeperDeleteApiUsage.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月4日上午9:34:05
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * ClassName:ZookeeperDeleteApiUsage <br/>
 * Function: 使用API接口删除节点. <br/>
 * Date:     2018年4月4日 上午9:34:05 <br/>
 * @author   鲁济良
 * @version  1.0
 * @since    JDK 1.8
 * @see 	 
 */
public class ZookeeperDeleteApiUsage implements Watcher{
    
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    
    public static void main(String[] args) throws Exception {
        
        ZooKeeper zkServer = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperDeleteApiUsage());
        
        countDownLatch.await();
        
        zkServer.delete("/testNode1", 0);//版本不对删除出错
        
    }

    public void process(WatchedEvent event) {//客户端同步连接后，锁存器置零
        
        if(KeeperState.SyncConnected == event.getState()) {
            
            countDownLatch.countDown();
            
        }
        
        
    }

}

