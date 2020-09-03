package com.zk.distributed.lock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkLock implements Lock {

    private ThreadLocal<ZooKeeper> zk =new ThreadLocal<>();
    private String LOCK_NAME="/LOCK";
    private  ThreadLocal<String> CURRENT_NODE=new ThreadLocal<>();

    public void init() {
        if(zk.get()==null){
            try {
                zk.set(new ZooKeeper("localhost:2181", 300, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {

                    }
                }));
                //这里其实应该判断下/LOCK有没有创建，如果没有创建，创建一个
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lock(){
        init();
        if (tryLock()){
            System.out.println(Thread.currentThread().getName()+"已经获取到锁了......");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock(){
        // /LOCK/zk1  zk2  zk3
        String nodeName=LOCK_NAME+"/zk_";
        try {
            CURRENT_NODE.set(zk.get().create(nodeName,new byte[0],ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT.EPHEMERAL_SEQUENTIAL));
            List<String> list=zk.get().getChildren(LOCK_NAME,false);
            Collections.sort(list);
            //zk1 zk2  zk3
            String minNodeName=list.get(0);
            if(CURRENT_NODE.equals(LOCK_NAME+"/"+minNodeName)){
                return true;
            }else {
                //拿到当前节点的下标
                Integer currentNode=list.indexOf(CURRENT_NODE.get().substring(CURRENT_NODE.get().lastIndexOf("/")+1));
                String preNode=list.get(currentNode-1);
                //监听前一个结点删除，如果触发了就要释放，如果没有触发就要阻塞
                CountDownLatch countDownLatch=new CountDownLatch(1);
                zk.get().exists(LOCK_NAME + "/" + preNode, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if(Event.EventType.NodeDeleted.equals(event.getType())){
                            countDownLatch.countDown();
                            System.out.println(Thread.currentThread().getName()+"被唤醒了......");
                        }
                    }
                });
                System.out.println(Thread.currentThread().getName()+"被阻塞住了......");
                countDownLatch.await();
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock(){
        try {
            zk.get().delete(CURRENT_NODE.get(),-1);
            CURRENT_NODE.set(null);
            zk.get().close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
