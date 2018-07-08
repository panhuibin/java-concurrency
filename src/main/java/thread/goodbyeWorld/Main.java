package thread.goodbyeWorld;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {
    private static class CustomizedExceptionHandler implements Thread.UncaughtExceptionHandler{

        public ExecutionException getExecutionException() {
            return executionException;
        }

        private ExecutionException executionException;

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            executionException = new ExecutionException(e);
        }
    }

    public static void main(String[] args){
        Kenny kenny = new Kenny();
        System.out.println("Starting kenny");
        Thread t = new Thread(kenny, "kenny");
        CustomizedExceptionHandler exceptionHandler = new CustomizedExceptionHandler();
        t.setUncaughtExceptionHandler(exceptionHandler);
        t.start();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){

        }
        t.interrupt();
        try{
            t.join();
        }catch (InterruptedException e){

        }
        ExecutionException exception = exceptionHandler.getExecutionException();
        System.out.println("they killed kenny!");
        if(exception!=null){
            System.out.println("we got the exception");
            exception.printStackTrace();
        }
    }
}
