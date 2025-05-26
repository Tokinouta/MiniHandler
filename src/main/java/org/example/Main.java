package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("MiniHandler demo started at: " + System.currentTimeMillis());

        MiniLooper.prepare();

        MiniHandler handler = new MiniHandler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("handleMessage, msg received at " + msg.when + ", what: " + msg.what +
                        ", priority: " + msg.priority + ", obj: " + msg.obj);
            }
        };

        handler.post(() -> System.out.println("run, msg received at " + System.currentTimeMillis()));
        handler.postDelayed(() -> System.out.println("runDelayed, msg received at " + System.currentTimeMillis()),
                1000);
        Message msg_high = new Message(1, "High priority message", null, System.currentTimeMillis(), -1, handler);
        Message msg_low = new Message(2, "Low priority message", null, System.currentTimeMillis(), 5, handler);
        handler.sendMessage(msg_low);
        handler.sendMessage(msg_high);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        MiniLooper.quit();
    }
}