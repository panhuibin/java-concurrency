package livelyness.fileNetworkDeadLock;

class MyNetworkCon {
    public void access() {
        System.out.println("MyNetworkCon is accessed by " + Thread.currentThread().getName());
    }
}
