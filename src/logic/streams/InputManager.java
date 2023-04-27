package main.java.src.logic.streams;

import java.util.*;

/**
 * Controls data providing
 */
public abstract class InputManager {

//    private Queue<String> buffer = new LinkedList<>();
    private Deque<String> buffer = new LinkedList<>();

    /**
     * @return returns line from user
     */
    public abstract String readLine();

    /**
     * @param line command or data to write into buffer
     */
    public void write(String line) {
        buffer.push(line);
    }

    /**
     * @param lines collection of lines to write into buffer
     */
    public void write(Collection<String> lines) {
        lines.forEach(this::write);
    }

    /**
     * @return returns value from buffers top or null
     */
    protected String getNext() {
        return buffer.pollFirst();
    }

    /**
     * @return returns true if the buffer is empty
     */
    public boolean isBufferEmpty() {
        return buffer.isEmpty();
    }

    /**
     * @return returns the size of the buffer
     */
    public int size() {
        return buffer.size();
    }
}
