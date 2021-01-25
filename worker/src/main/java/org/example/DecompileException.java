package org.example;

/**
 * @author: wutianbiao
 * @date: 2021/1/24
 */
public class DecompileException {
    public void test(){
        try{
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("unexpect exception");
        }
    }
}
