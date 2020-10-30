public class ThreadLocalTest1 {

    private Person person;
    private ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {

    }
}

class Person{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
