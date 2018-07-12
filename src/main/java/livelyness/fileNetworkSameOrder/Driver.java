package livelyness.fileNetworkSameOrder;

class Driver {
    public static void main(String[] args) {
        MyFile myFile = new MyFile();
        MyNetworkCon myNetworkCon = new MyNetworkCon();
        Task1 task1 = new Task1(myFile, myNetworkCon);
        Task2 task2 = new Task2(myFile, myNetworkCon);
        Thread t1 = new Thread(task1, "Task 1");
        Thread t2 = new Thread(task2, "Task 2");
        t1.start();
        t2.start();
    }
}
