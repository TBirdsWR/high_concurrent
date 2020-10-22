import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class T004 {

    private static int count = 10;

    public synchronized static void m() throws InterruptedException {
        count --;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
        Thread.sleep(10000);
    }

    public static void mm (){
        synchronized (T004.class){

            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }


    public static void mmm() {
        count --;
        System.out.println( "mmm:count = " + count);

    }

    public static void main(String[] args) {
        Runnable r = () -> {
            T004 t = new T004();
            for (int i = 0;i<100;i++){
                T004.mm();
                try {
                    T004.m();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        new Thread(r).start();
        new Thread(() -> {
            T004 t = new T004();
            for (int i = 0;i<100;i++){

                t.mmm();

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
