package com.activeMq.basic;

public class TestMq {
    public static void main(String[] args){
        Producter producter = new Producter();
        producter.init();
        Comsumer comsumer = new Comsumer();
        comsumer.init();
        TestMq testMq = new TestMq();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //	Producter
        new Thread(testMq.new ProductorMq(producter)).start();
//        new Thread(testMq.new ProductorMq(producter)).start();
//        new Thread(testMq.new ProductorMq(producter)).start();
//        new Thread(testMq.new ProductorMq(producter)).start();
//        new Thread(testMq.new ProductorMq(producter)).start();
        
        // Consummer
        new Thread(testMq.new ComsumerMq(comsumer)).start();
        new Thread(testMq.new ComsumerMq(comsumer)).start();
        
        
    }

    private class ProductorMq implements Runnable{
        Producter producter;
        public ProductorMq(Producter producter){
            this.producter = producter;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("LiJinTest-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private class ComsumerMq implements Runnable{
    	Comsumer comsumer;
        public ComsumerMq(Comsumer comsumer){
            this.comsumer = comsumer;
        }

        @Override
        public void run() {
            while(true){
                try {
                	comsumer.getMessage("LiJinTest-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
