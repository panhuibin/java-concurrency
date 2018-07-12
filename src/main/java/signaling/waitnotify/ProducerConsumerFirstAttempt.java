package signaling.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumerFirstAttempt {
    final Queue<Object> queue = new LinkedList<>();

    void producer(Object work) {
        synchronized (queue) {
            queue.offer(work);
            queue.notify();
        }
    }

    Object consumer() {
        Object work;
        synchronized (queue) {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            work = queue.poll();
        }
        return work;
    }
}
