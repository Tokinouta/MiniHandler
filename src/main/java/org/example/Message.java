package org.example;

public class Message {
    public Runnable callback;
    public Object obj;
    Message next;
    long when;

    public Message(Runnable callback) {
        this.callback = callback;
    }
}
