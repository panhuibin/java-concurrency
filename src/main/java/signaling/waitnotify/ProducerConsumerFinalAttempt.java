package signaling.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumerFinalAttempt {
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
            while (!Thread.currentThread().isInterrupted() && queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            work = queue.poll();
        }
        return work;
    }
}
