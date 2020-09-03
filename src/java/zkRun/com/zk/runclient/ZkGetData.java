package com.zk.runclient;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZkGetData {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper client=new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("奇怪的连接测试"+event.toString());
            }
        });
        Stat stat=new Stat();
        //拿/data的数据的时候就绑定了一个监听器
        client.getData("/data", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.EventType.NodeDataChanged.equals(event.getType())){
                    System.out.println("数据发生了改变");
                }
            }
        },stat);
    }
}
