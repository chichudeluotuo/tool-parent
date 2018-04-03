/**
 * Project Name:tool-zookeeper
 * File Name:ZookeeperCreateApiSyncUsage.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月3日下午9:39:53
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * ClassName:ZookeeperCreateApiSyncUsage <br/>
 * Function: 使用[异步]接口创建节点（zk不支持递归创建节点）. <br/>
 * Date: 2018年4月3日 下午9:39:53 <br/>
 * 
 * @author 鲁济良
 * @since JDK 1.8
 */
public class ZookeeperCreateApiASyncUsage implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zkServer = new ZooKeeper("127.0.0.1:2181", //
                5000, new ZookeeperCreateApiASyncUsage());

        countDownLatch.await();//锁存器为零前，当前线程等待，

        //节点名、节点内容、权限类型、节点类型、回调函数、可在回调中使用对象
        zkServer.create("/testNode1", //
                "123".getBytes(), ///
                Ids.OPEN_ACL_UNSAFE, //
                CreateMode.EPHEMERAL, //
                new IStringCallback(), //
                "I am context");
        //节点名、节点内容、权限类型、节点类型、回调函数、可在回调中使用对象
        zkServer.create("/testNode1", //
                "123".getBytes(), ///
                Ids.OPEN_ACL_UNSAFE, //
                CreateMode.EPHEMERAL, //
                new IStringCallback(), //
                "I am context");

        Thread.sleep(Integer.MAX_VALUE);

    }

    public void process(WatchedEvent event) {

        if (KeeperState.SyncConnected == event.getState()) {//客户端同步连接后，锁存器置零
            countDownLatch.countDown();
        }

    }

}
