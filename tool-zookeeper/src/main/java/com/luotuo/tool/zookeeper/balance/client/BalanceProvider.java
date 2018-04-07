package com.luotuo.tool.zookeeper.balance.client;

public interface BalanceProvider<T> {

    public T getBalanceItem();

}
