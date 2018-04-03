/**
 * Project Name:tool-zookeeper
 * File Name:ZookeeperConstructorUsageSidPassword.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月3日下午5:41:41
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * ClassName:ZookeeperConstructorUsageSidPassword <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月3日 下午5:41:41 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public class ZookeeperConstructorUsageSidPassword implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        ZooKeeper zkServer = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperConstructorUsageSidPassword());

        countDownLatch.await();//锁存器为零前，当前线程等待，
        long sessionId = zkServer.getSessionId();
        byte[] passwd = zkServer.getSessionPasswd();

        ZooKeeper zkServiceError = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperConstructorUsageSidPassword(), 2L, "errorPass".getBytes());

        ZooKeeper zkServiceRight = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperConstructorUsageSidPassword(), sessionId, passwd);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {//客户端同步连接后，锁存器置零

        System.out.println("Receive watcher event :" + event);

        if (KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }

    }

}
