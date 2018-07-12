package livelyness.fileNetworkDeadLock;

class Task1 implements Runnable{
    private final MyFile file;
    private final MyNetworkCon networkCon;

    public Task1(MyFile file, MyNetworkCon networkCon) {
        this.file = file;
        this.networkCon = networkCon;
    }

    @Override
    public void run() {
        System.out.println("Task1 is about to acquire mutex");
        synchronized(file){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            synchronized (networkCon){
                file.access();
                networkCon.access();
            }
        }
    }
}
