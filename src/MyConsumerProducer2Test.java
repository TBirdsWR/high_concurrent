import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock 和Condition
 */
public class MyConsumerProducer2Test<T> {
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition consumer = lock.newCondition();
    private Condition producer = lock.newCondition();



    public void put(T t){
        try{
            lock.lock();
            while(list.size() == MAX){
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            System.out.println(Thread.currentThread().getName() + "增加了" + t);
            list.add(t);
            ++count;
            consumer.signalAll();

        }finally {
            lock.unlock();
        }



    }


    public T get(){
        try {
            lock.lock();
            while (list.size() == 0){
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = list.removeFirst();
            count--;
            producer.signalAll();
//            System.out.println(Thread.currentThread().getName() + "消费了" + t);
            return t;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyConsumerProducer2Test<String> m = new MyConsumerProducer2Test<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while(true) {
                    System.out.println(m.get());
                }
            },"c"+i).start();
        }
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    m.put(Thread.currentThread().getName()+" "+j);
                }
            },"p"+i).start();
        }
    }
}
