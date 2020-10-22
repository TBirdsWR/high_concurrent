import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyContains {

    private Object o;

    final static Object o2  = new Object();

    volatile List list = new ArrayList();

    public static void main(String[] args) {
        MyContains myContains = new MyContains();

//        LatchTest(myContains);


//        waitTest(myContains);


//        volatileTest(myContains);

    }

    private static void LatchTest(MyContains myContains) {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(()->{
            if (myContains.list.size()  != 4){


                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName()+" end");
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                myContains.list.add(new Object());

                System.out.println("list 的size" + myContains.list.size());
                if (myContains.list.size() %2 == 0){

                    countDownLatch.countDown();

                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

                System.out.println(Thread.currentThread().getName()+"end");
        }).start();
    }

    private static void waitTest(MyContains myContains) {
        new Thread(()->{
            synchronized (o2){
                if (myContains.list.size() != 5){
                    try {
                        o2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o2.notify();
                System.out.println(Thread.currentThread().getName()+" end");
            }

        }).start();


        new Thread(()->{
            synchronized (o2){
                for (int i = 0; i < 10; i++) {
                    myContains.list.add(new Object());

                    System.out.println("list 的size" + myContains.list.size());
                    if (myContains.list.size() == 5){
                        o2.notify();

                        try {
                            o2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName()+"end");
        }).start();
    }

    private static void volatileTest(MyContains myContains) {
        new Thread(()->{
            System.out.println("线程2开始");
            while (true){
//                System.out.println(list.size());
                if(myContains.list.size() == 5) {
                    break;
                }
            }
            System.out.println("线程2结束");
        }).start();

        new Thread(()->{
            System.out.println("线程1开始");
            for (int i = 0; i < 10; i++) {
                myContains.list.add(new Object());
                System.out.println("list 的size" + myContains.list.size());

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("线程1结束");
        }).start();
    }


}
