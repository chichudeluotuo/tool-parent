/**
 * Project Name:tool-zookeeper
 * File Name:IStringCallback.java
 * Package Name:com.luotuo.tool.zookeeper.client
 * Date:2018年4月3日下午10:18:38
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.zookeeper.client;

import org.apache.zookeeper.AsyncCallback;

/**
 * ClassName:IStringCallback <br/>
 * Function: 创建节点异步函数. <br/>
 * Date: 2018年4月3日 下午10:18:38 <br/>
 * 
 * @author 鲁济良
 * @since JDK 1.8
 */
public class IStringCallback implements AsyncCallback.StringCallback {

    public void processResult(int rc, String path, Object ctx, String name) {

        //rc:0表示调用成功，-4服务器与客户端断开，-110节点已经存在，-112会话过期
        System.out.println("Create path result:[" + rc + "," + path + "," + ctx + ",real path name" + name);

    }

}
