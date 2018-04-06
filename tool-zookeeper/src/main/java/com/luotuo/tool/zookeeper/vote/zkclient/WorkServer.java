package com.luotuo.tool.zookeeper.vote.zkclient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

public class WorkServer {

    //当前运行状态
    private volatile boolean running = false;

    private ZkClient zkClient;
    //master节点名
    private static final String MASTER_PATH = "/master";
    //zkclient中监听，等价于watcher
    private IZkDataListener dataListener;
    //当前节点数据
    private RunningData serverData;
    //master节点状态
    private RunningData masterData;
    //定时器
    private ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
    private int delayTime = 5;

    public WorkServer(RunningData rd) {
        this.serverData = rd;
        this.dataListener = new IZkDataListener() {

            public void handleDataDeleted(String dataPath) throws Exception {//节点删除事件监听

                //master选举优化，如果当前节点丢失master权利，则当前节点立刻创建master节点。防止网络抖动造成的master丢失
                if (masterData != null && masterData.getName().equals(serverData.getName())) {
                    takeMaster();
                } else {//其他节点延迟5秒进行master选举
                    delayExector.schedule(new Runnable() {
                        public void run() {
                            takeMaster();
                        }
                    }, delayTime, TimeUnit.SECONDS);

                }

            }

            public void handleDataChange(String dataPath, Object data)
                    throws Exception {
            }
        };
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public void start() throws Exception {//启动服务
        if (running) {
            throw new Exception("server has startup...");
        }
        running = true;
        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        takeMaster();//参与master选举

    }

    public void stop() throws Exception {
        if (!running) {
            throw new Exception("server has stoped");
        }
        running = false;

        delayExector.shutdown();

        zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);

        releaseMaster();//释放master节点

    }

    private void takeMaster() {
        if (!running)
            return;

        try {
            zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
            masterData = serverData;
            System.out.println(serverData.getName() + " is master");
            //为了测试，5后秒释放master
            /*            delayExector.schedule(new Runnable() {
                public void run() {
                    // TODO Auto-generated method stub
                    if (checkMaster()) {
                        releaseMaster();
                    }
                }
            }, 5, TimeUnit.SECONDS);*/

        } catch (ZkNodeExistsException e) {
            RunningData runningData = zkClient.readData(MASTER_PATH, true);
            if (runningData == null) {
                takeMaster();
            } else {
                masterData = runningData;
            }
        } catch (Exception e) {
            // ignore;
        }

    }

    private void releaseMaster() {
        if (checkMaster()) {
            zkClient.delete(MASTER_PATH);

        }

    }

    private boolean checkMaster() {//检测当前节点是否是master
        try {
            RunningData eventData = zkClient.readData(MASTER_PATH);
            masterData = eventData;
            if (masterData.getName().equals(serverData.getName())) {
                return true;
            }
            return false;
        } catch (ZkNoNodeException e) {
            return false;
        } catch (ZkInterruptedException e) {
            return checkMaster();
        } catch (ZkException e) {
            return false;
        }
    }

}
