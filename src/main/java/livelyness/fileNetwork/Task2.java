package livelyness.fileNetwork;

class Task2 implements Runnable{
    private final MyFile file;
    private final MyNetworkCon networkCon;

    public Task2(MyFile file, MyNetworkCon networkCon) {
        this.file = file;
        this.networkCon = networkCon;
    }

    @Override
    public void run() {
        System.out.println("Task2 is about to acquire mutex");
        synchronized(networkCon){
            synchronized (file){
                networkCon.access();
                file.access();
            }
        }
    }
}
