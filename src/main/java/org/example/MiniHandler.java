package org.example;

/**
 * A handler for sending and processing {@link Message} objects associated with a {@link MiniLooper}.
 *
 * <p>
 * This class allows you to send messages and runnables to be processed on the background thread
 * managed by a {@link MiniLooper}. It mimics Android's Handler/Looper pattern in a simplified form.
 * </p>
 *
 * <p>
 * Messages can be posted immediately or delayed, and they are queued in the {@link MessageQueue}
 * associated with the looper. Subclasses may override {@link #handleMessage(Message)} to process custom messages.
 * </p>
 *
 * <p>
 * Usage example:
 * <pre>
 * MiniLooper.prepare();
 * MiniHandler handler = new MiniHandler();
 * handler.post(() -> System.out.println("Hello from background thread"));
 * </pre>
 * </p>
 */
public class MiniHandler {
    private final MiniLooper mLooper;

    public MiniHandler() {
        this.mLooper = MiniLooper.myLooper();
        if (this.mLooper == null) {
            throw new RuntimeException("MiniHandler must be created after MiniLooper.prepare()");
        }
    }

    public void post(Runnable r) {
        sendMessageDelayed(new Message(0, null, r, System.currentTimeMillis(), 0, null), 0);
    }

    public void postDelayed(Runnable r, long delayMillis) {
        sendMessageDelayed(new Message(0, null, r, System.currentTimeMillis() + delayMillis, 0, null), delayMillis);
    }

    public void sendMessage(Message msg) {
        sendMessageDelayed(msg, 0);
    }

    private void sendMessageDelayed(Message msg, long delayMillis) {
        msg.when = System.currentTimeMillis() + delayMillis;
        mLooper.queue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg) {
        // Override this for custom uses
    }
}
