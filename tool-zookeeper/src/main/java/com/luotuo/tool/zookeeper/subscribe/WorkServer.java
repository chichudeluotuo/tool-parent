package com.luotuo.tool.zookeeper.subscribe;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import com.alibaba.fastjson.JSON;

/**
 * ClassName: WorkServer <br/>
 * Function: 模拟服务. <br/>
 * date: 2018年4月6日 下午3:22:59 <br/>
 *
 * @author 鲁济良
 * @version
 * @since JDK 1.8
 */
public class WorkServer {

    private ZkClient zkClient;
    private String configPath;
    private String serversPath;
    private ServerData serverData;
    private ServerConfig serverConfig;
    private IZkDataListener dataListener;

    public WorkServer(String configPath, String serversPath,
            ServerData serverData, ZkClient zkClient, ServerConfig initConfig) {
        this.zkClient = zkClient;
        this.serversPath = serversPath;
        this.configPath = configPath;
        this.serverConfig = initConfig;
        this.serverData = serverData;

        this.dataListener = new IZkDataListener() {

            public void handleDataDeleted(String dataPath) throws Exception {
                // TODO Auto-generated method stub

            }

            public void handleDataChange(String dataPath, Object data)
                    throws Exception {
                // TODO Auto-generated method stub
                String retJson = new String((byte[]) data);
                ServerConfig serverConfigLocal = (ServerConfig) JSON.parseObject(retJson, ServerConfig.class);
                updateConfig(serverConfigLocal);
                System.out.println("new Work server config is:" + serverConfig.toString());

            }
        };

    }

    public void start() {
        System.out.println("work server start...");
        initRunning();

    }

    public void stop() {
        System.out.println("work server stop...");
        zkClient.unsubscribeDataChanges(configPath, dataListener);
    }

    /**
     * initRunning:(服务启动时，初始化动作). <br/>
     *
     * @author 鲁济良
     * @since JDK 1.8
     */
    private void initRunning() {

        regist();
        zkClient.subscribeDataChanges(configPath, dataListener);

    }

    /**
     * regist:(服务启动时，注册到zk). <br/>
     *
     * @author 鲁济良
     * @since JDK 1.8
     */
    private void regist() {
        String mePath = serversPath.concat("/").concat(serverData.getAddress());

        try {
            zkClient.createEphemeral(mePath, JSON.toJSONString(serverData)
                    .getBytes());
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(serversPath, true);
            regist();
        }
    }

    /**
     * updateConfig:(更新配置信息). <br/>
     *
     * @author 鲁济良
     * @param serverConfig
     * @since JDK 1.8
     */
    private void updateConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

}
