package org.example;

import java.util.PriorityQueue;

/**
 * A class representing a message that can be posted to a {@link MessageQueue}.
 * Each message has a scheduled time for execution, an optional callback {@link Runnable},
 * an associated object, and a message identifier.
 *
 * <p>
 * Messages are compared based on their scheduled time ({@link #when}).
 * If times are equal, they are ordered by the message identifier ({@link #what}).
 * This ordering is used in a {@link PriorityQueue} to manage message scheduling.
 * </p>
 */
public class Message implements Comparable<Message> {
    public int what;
    public Object obj;
    public Runnable callback;
    public long when;
    public int priority;

    public Message(int what, Object obj, Runnable callback, long when, int priority) {
        this.what = what;
        this.obj = obj;
        this.callback = callback;
        this.when = when;
        this.priority = priority;
    }

    @Override
    public int compareTo(Message other) {
        if (this.when != other.when) {
            return Long.compare(this.when, other.when);
        }
        return Integer.compare(this.priority, other.priority);
    }
}

