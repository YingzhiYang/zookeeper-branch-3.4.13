package com.zk.distributed.config;

import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String,String> cache=new HashMap<>();

    public void init(){

    }

    public void save(String name, String value){
        //存储的时候存到ZK上


    }

    //通过watch机制更新本地缓存
    public String get(String name){
        //取的时候并不需要从zk上取,可以在本地建立一个缓存，在存储之前更新缓存
        return null;
    }
}
