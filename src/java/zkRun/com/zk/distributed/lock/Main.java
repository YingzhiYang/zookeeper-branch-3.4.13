package com.zk.distributed.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Thread t1=new Thread(new UserThread(),"user1");
        Thread t2=new Thread(new UserThread(),"user2");
        t1.start();
        t2.start();
    }
    //static Lock lock =new ReentrantLock();

    static Lock lock=new ZkLock();
    static class UserThread implements Runnable{
        @Override
        public void run() {
            new Order().createOrder();
            lock.lock();
            boolean result=new Stock().reduceStock();
            lock.unlock();
            if (result){
                System.out.println(Thread.currentThread().getName()+"购买成功");
                new Pay().pay();
            }else {
                System.out.println(Thread.currentThread().getName()+"购买失败");
            }
        }
    }
}
