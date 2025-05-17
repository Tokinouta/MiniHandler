package org.example;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A thread-safe queue for managing delayed messages based on their scheduled time.
 *
 * <p>
 * This class uses a {@link PriorityQueue} to order messages by their scheduled time
 * ({@link Message#when}). Messages are enqueued with {@link #enqueueMessage(Message)}
 * and dequeued in order of execution time using {@link #next()}.
 * </p>
 *
 * <p>
 * If the queue is empty, {@link #next()} blocks until a message is enqueued.
 * If the head message is not yet scheduled for execution, {@link #next()}
 * waits until the message's scheduled time before returning it.
 * </p>
 */
public class MessageQueue {
    private final PriorityQueue<Message> queue = new PriorityQueue<>(
            Comparator.comparingLong(a -> a.when)
    );

    public synchronized void enqueueMessage(Message msg) {
        queue.offer(msg);
        notify();
    }

    public synchronized Message next() throws InterruptedException {
        while (true) {
            if (queue.isEmpty()) {
                System.out.println("MessageQueue.next() queue is empty");
                wait();
            } else {
                Message msg = queue.peek();
                long now = System.currentTimeMillis();
                System.out.println("MessageQueue.next() queue gets " + msg.when + " now " + now);
                if (msg.when <= now) {
                    return queue.poll();
                }
                wait(msg.when - now); // wait until it's time to process the head message
            }
        }
    }
}
