package org.example;

/**
 * Class responsible for managing a message loop in a background thread.
 *
 * <p>
 * A [MiniLooper](file://E:\Codes\MiniHandler\src\main\java\org\example\MiniLooper.java#L2-L45) runs a loop that continuously retrieves and processes messages from a {@link MessageQueue}.
 * It provides thread-local storage, ensuring each thread has its own instance via {@link #prepare()} and {@link #myLooper()}.
 * </p>
 *
 * <p>
 * The looper can be terminated gracefully by calling {@link #quit()}, which interrupts the background thread.
 * Messages are processed in the order of their scheduled time.
 * </p>
 */
public class MiniLooper {
    private static final ThreadLocal<MiniLooper> threadLooper = new ThreadLocal<>();

    final MessageQueue queue;
    final Thread thread;

    private MiniLooper() {
        queue = new MessageQueue();
        thread = new Thread(this::loop, "MiniLooper");
        thread.start();
    }

    private void loop() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = queue.next();
                System.out.println("Processing message: " + msg + " callback: " +  msg.callback);
                if (msg.callback != null) {
                    msg.callback.run();
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void prepare() {
        if (threadLooper.get() != null) {
            throw new RuntimeException("Only one Looper per thread");
        }
        threadLooper.set(new MiniLooper());
    }

    public static MiniLooper myLooper() {
        return threadLooper.get();
    }

    public static void quit() {
        MiniLooper looper = threadLooper.get();
        if (looper != null) {
            looper.thread.interrupt();
            threadLooper.remove();
        }
    }
}
