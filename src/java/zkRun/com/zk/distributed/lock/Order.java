package com.zk.distributed.lock;

public class Order {

    public void createOrder(){
        System.out.println(Thread.currentThread().getName()+"创建order");
    }
}
