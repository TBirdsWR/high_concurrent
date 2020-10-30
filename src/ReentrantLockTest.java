
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    ReentrantLock r = new ReentrantLock();

    public void m(){
        r.lock();
        for (int i = 0; i < 10; i++) {
            System.out.println("test");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        r.unlock();


    }

    public void mm(){

//        r.lock();
//        boolean b = r.tryLock();
        try {
            r.lockInterruptibly();

            for (int i = 0; i < 10; i++) {
                System.out.println("mm test");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
            }
        } catch (InterruptedException e) {

        }finally {
//            if(r.isLocked()){
//                r.unlock();
//            }

        }
//        if(b){
//            for (int i = 0; i < 10; i++) {
//                System.out.println("mm test");
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////            }
//        }

//        r.unlock();
//        if(b) r.unlock();
    }


    public static void main(String[] args) {

        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        new Thread(reentrantLockTest::m).start();
        Thread t2 = new Thread(reentrantLockTest::mm);
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();

    }
}
