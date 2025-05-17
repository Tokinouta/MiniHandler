package org.example;

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
