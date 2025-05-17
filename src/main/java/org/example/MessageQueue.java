package org.example;

import java.util.PriorityQueue;

public class MessageQueue {
    private final PriorityQueue<Message> queue = new PriorityQueue<>(
            (a, b) -> Long.compare(a.when, b.when)
    );

    public synchronized void enqueueMessage(Message msg, long when) {
        msg.when = when;
        queue.offer(msg);
        notify();
    }

    public synchronized Message next() throws InterruptedException {
        while (true) {
            if (queue.isEmpty()) {
                wait();
            } else {
                Message msg = queue.peek();
                long now = System.currentTimeMillis();
                if (msg.when <= now) {
                    return queue.poll();
                }
                wait(msg.when - now); // wait until it's time to process the head message
            }
        }
    }
}
