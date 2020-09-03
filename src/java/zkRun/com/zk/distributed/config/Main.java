package com.zk.distributed.config;

public class Main {
    public static void main(String[] args) {
        Config config=new Config();
        config.save("123","123");
        config.get("123");
    }
}
