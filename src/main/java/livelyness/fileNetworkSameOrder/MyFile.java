package livelyness.fileNetworkSameOrder;

class MyFile {
    public void access() {
        System.out.println("MyFile is accessed by " + Thread.currentThread().getName());
    }
}