package livelyness.fileNetwork;

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
            synchronized (networkCon){
                file.access();
                networkCon.access();
            }
        }
    }
}
