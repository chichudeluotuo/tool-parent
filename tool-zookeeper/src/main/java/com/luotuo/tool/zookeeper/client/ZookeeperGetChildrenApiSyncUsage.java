/**
 * Project Name:tool-zookeeper
 * File Name:ZookeeperCreateApiSyncUsage.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月3日下午9:39:53
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * ClassName:ZookeeperCreateApiSyncUsage <br/>
 * Function: 使用[同步]接口读取节点. <br/>
 * Date: 2018年4月3日 下午9:39:53 <br/>
 * 
 * @author 鲁济良
 * @since JDK 1.8
 */
public class ZookeeperGetChildrenApiSyncUsage implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zkServer = null;
    
    public static void main(String[] args) throws Exception {

        zkServer = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperGetChildrenApiSyncUsage());

        countDownLatch.await();//锁存器为零前，当前线程等待，
        
        zkServer.create("/parent", "111".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zkServer.create("/parent/a", "122".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        List<String> children = zkServer.getChildren("/parent", true);//获取路径下所有节点名,使用服务端检测器，节点变化
        for (String string : children) {
            System.out.println(string);
        }
        
        //再次在parent下创建节点
        zkServer.create("/parent/b", "133".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        Thread.sleep(Integer.MAX_VALUE);


    }

    public void process(WatchedEvent event) {

        if (KeeperState.SyncConnected == event.getState()) {//客户端同步连接后，锁存器置零
            countDownLatch.countDown();
            
            if(event.getType() == EventType.NodeChildrenChanged) {
                try {
                    List<String> children = zkServer.getChildren("/parent", true);
                    System.out.println("监控到变化后，现在节点");
                    for (String string : children) {
                        System.out.println(string);
                    }
                } catch (Exception e) {
                }
            }
        }

    }

}
