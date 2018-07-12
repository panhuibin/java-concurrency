package livelyness.fileNetworkSingleMutex.fileNetworkSameOrder;

class Task1 implements Runnable {
    private final MyFile file;
    private final MyNetworkCon networkCon;
    private final Object mutex;

    public Task1(MyFile file, MyNetworkCon networkCon, Object mutex) {
        this.file = file;
        this.networkCon = networkCon;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        System.out.println("Task1 is about to acquire mutex");
        synchronized (mutex) {
            file.access();
            networkCon.access();
        }
    }
}
