/**
 * 演示死锁
 */
public class DeadLock {

    private Object o1 = new Object();

    private Object o2 = new Object();

    public  void f1() throws InterruptedException {
        synchronized(o1){
            System.out.println(Thread.currentThread().getName()+"获得o1锁");
            Thread.sleep(2000);
            synchronized(o2){
                System.out.println(Thread.currentThread().getName()+"获得o2锁");

            }
        }
    }

    public  void f2() throws InterruptedException {
        synchronized(o2){
            System.out.println(Thread.currentThread().getName()+"获得o2锁");
            Thread.sleep(2000);
            synchronized(o1){
                System.out.println(Thread.currentThread().getName()+"获得o1锁");

            }
        }
    }

    public static void main(String[] args) throws Exception{
        DeadLock deadLock = new DeadLock();


        new Thread(() -> {
            try {
                deadLock.f1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                deadLock.f2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
