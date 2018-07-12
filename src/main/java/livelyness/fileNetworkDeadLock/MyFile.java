package livelyness.fileNetworkDeadLock;

class MyFile {
    public void access() {
        System.out.println("MyFile is accessed by " + Thread.currentThread().getName());
    }
}
