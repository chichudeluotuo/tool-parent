package com.luotuo.tool.zookeeper.subscribe;

/**
 * ClassName: ServerData <br/>
 * Function: 服务信息实体. <br/>
 * date: 2018年4月6日 下午3:22:36 <br/>
 *
 * @author 鲁济良
 * @version
 * @since JDK 1.8
 */
public class ServerData {

    private String address;
    private Integer id;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerData [address=" + address + ", id=" + id + ", name="
                + name + "]";
    }

}
