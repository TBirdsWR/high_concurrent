import java.util.concurrent.TimeUnit;

/**
 * synchronized锁住的是堆内存里的对象，而非栈上面的引用，当发生异常的时候，会释放锁，其他线程可执行；当变量指向一块新的对象时，其他线程可使用新的对象锁
 */
public class T5 {

    private Object o = new Object();


    public void m(){

        synchronized (o){
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }

    }

    public static void main(String[] args) {
        T5 t = new T5();

        new Thread(t::m).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        t.o = new Object();

        new Thread(t::m).start();
    }
}
