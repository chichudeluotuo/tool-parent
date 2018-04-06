package com.luotuo.tool.zookeeper.subscribe;

/**
 * ClassName: ServerConfig <br/>
 * Function: 配置信息实体. <br/>
 * date: 2018年4月6日 下午3:22:08 <br/>
 *
 * @author 鲁济良
 * @version
 * @since JDK 1.8
 */
public class ServerConfig {

    private String dbUrl;
    private String dbPwd;
    private String dbUser;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public String toString() {
        return "ServerConfig [dbUrl=" + dbUrl + ", dbPwd=" + dbPwd
                + ", dbUser=" + dbUser + "]";
    }

}
