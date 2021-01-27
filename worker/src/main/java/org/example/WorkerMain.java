package org.example;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class WorkerMain {
    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            int x = new Random().nextInt();
            new WorkerMain().test(x);
        }
    }


    public void test(int x) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("i'm working " + x);
    }
}
