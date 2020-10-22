import java.util.concurrent.TimeUnit;

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
