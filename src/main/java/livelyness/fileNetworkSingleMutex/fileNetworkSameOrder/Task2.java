package livelyness.fileNetworkSingleMutex.fileNetworkSameOrder;

class Task2 implements Runnable {
    private final MyFile file;
    private final MyNetworkCon networkCon;
    private final Object mutex;

    public Task2(MyFile file, MyNetworkCon networkCon, Object mutex) {
        this.file = file;
        this.networkCon = networkCon;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        System.out.println("Task2 is about to acquire mutex");
        synchronized (mutex) {
            networkCon.access();
            file.access();
        }
    }
}
