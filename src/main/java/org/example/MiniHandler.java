package org.example;

public class MiniHandler {
    private final MiniLooper mLooper;

    public MiniHandler() {
        this.mLooper = MiniLooper.myLooper();
        if (this.mLooper == null) {
            throw new RuntimeException("MiniHandler must be created after MiniLooper.prepare()");
        }
    }

    public void post(Runnable r) {
        sendMessageDelayed(new Message(r), 0);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        sendMessageDelayed(new Message(r), delayMillis);
    }

    private void sendMessageDelayed(Message msg, long delayMillis) {
        long when = System.currentTimeMillis() + delayMillis;
        mLooper.queue.enqueueMessage(msg, when);
    }

    public void handleMessage(Message msg) {
        // Override this for custom uses
    }
}
