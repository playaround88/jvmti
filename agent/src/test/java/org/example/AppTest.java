package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static volatile long timer;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {

        timer -= System.currentTimeMillis();

        Thread.sleep(2000);
        System.out.println("i'm working ");

        timer += System.currentTimeMillis();
        System.out.println(timer);
    }
}
