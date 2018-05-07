package com.ghost.thread;

import org.junit.Test;

/**
 * Created by ghost on 2018-05-07.
 */
public class DeadLock {
    private static Object o1 = new Object(), o2 = new Object();
    public Thread t;

    public void synA() {
        System.out.println("方法a执行中");
        synchronized (o1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println("输出o2");
            }
        }
        System.out.println("方法a执行结束");
    }

    public void synB() {
        System.out.println("方法b执行中");
        synchronized (o2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {
                System.out.println("输出o1");
            }
        }
        System.out.println("方法b执行结束");

    }


    public static void main(String[] args) throws InterruptedException {
//        互斥
//        等待/持有
//        非抢占
//        形成等待环

        final DeadLock deadLock = new DeadLock();
        final DeadLock deadLock2 = new DeadLock();

        //jdk 1.8 Lambda 表达式
        //死锁方式1
//        new Thread(() -> deadLock.synA()).start();
//        new Thread(() -> deadLock2.synB()).start();

        //死锁方式2
        Thread t = new Thread(() -> {
            try {
                deadLock.methodA();
                deadLock.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                deadLock2.methodB("");
                deadLock2.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        deadLock.t = t;
        deadLock2.t = t2;
        t.start();
        t2.start();

    }


    @Test
    public void test() {
        final DeadLock deadLock = new DeadLock();
        final DeadLock deadLock2 = new DeadLock();
        //jdk 1.8 Lambda 表达式
        new Thread(() -> deadLock.methodA()).start();
        new Thread(() -> deadLock.methodB("")).start();
    }

    public synchronized void methodA() {
        System.out.println("methodA");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        methodB("A");
    }

    public synchronized void methodB(String name) {
        System.out.println(name + " execute methodB");
    }

}
