package thread;

import java.util.concurrent.TimeUnit;

class PrintCountries {

    public class MyThread extends Thread {
        public String country;

        public MyThread(String country) {
            super(country);
            this.country = country;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getId()+":"+country);
        }
    }

    public void printCountries(){
        String[] countries = {"France", "Italy", "China", "India"};
        for(String country: countries){
            MyThread thread = new MyThread(country);
            thread.run();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrintCountries threads = new PrintCountries();
        threads.printCountries();
        TimeUnit.MICROSECONDS.sleep(100);
    }
}
