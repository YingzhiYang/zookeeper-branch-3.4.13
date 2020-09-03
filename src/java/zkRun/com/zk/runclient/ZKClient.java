package com.zk.runclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZKClient {
    public static void main(String[] args) throws IOException {
        ZooKeeper client=new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("奇怪的连接测试"+event.toString());
            }
        });
        System.in.read();
    }
}
