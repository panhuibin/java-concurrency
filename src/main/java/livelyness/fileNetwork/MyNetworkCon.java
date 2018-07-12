package livelyness.fileNetwork;

class MyNetworkCon {
    public void access() {
        System.out.println("MyNetworkCon is accessed by " + Thread.currentThread().getName());
    }
}
