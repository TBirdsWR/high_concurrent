import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 生产者 消费者 使用wait notify实现
 */
public class MyConsumerProducerTest<T> {


    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put (T t){
        while(list.size() == MAX){ //此处使用while防止多条线程醒来时候，其他线程把容器put满，while会再次检查一次list.size() == MAX条件，不满足才继续往下走
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();//通知消费者消费，此处用notifyAll不使用notify，因为notify只唤醒一个线程，
        // 可能唤醒的还是一个生产者线程，等list满了生产者线程执行此方法会wait，程序就会卡在这里，所以把生产者消费者都通知

    }


    public synchronized T get(){
        while(list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        T t = list.removeFirst();
        --count;
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyConsumerProducerTest<String> m = new MyConsumerProducerTest<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
               while(true) {
                    System.out.println(m.get());
                }
            },"c"+i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    m.put(Thread.currentThread().getName()+" "+j);
                }
            },"p"+i).start();
        }
    }

}
