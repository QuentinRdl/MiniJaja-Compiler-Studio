package fr.ufrst.m1info.gl.compGL;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

/**
 * Manages the stack for the interpreter
 * -> Handles pushing and popping frames and variables management
 */
public class Stack {
    private Deque<Frame> frames;

    /**
     * Constructor
     */
    public Stack() {
        this.frames = new ArrayDeque<>();
    }

    /**
     * Pushes a new frame into the stack
     * @param frame new frame
     */
    public void push(Frame frame) {
        frames.push(frame);
    }

    /**
     * Creates & push a new frame into the stack
     * @param funcName name of the func
     * @param callLine line where the func was called
     */
    public void pushFrame(String funcName, int callLine) {
        Frame parent = frames.isEmpty() ? null : frames.peek(); // Sets the parent of the new Frame
        Frame newFrame = new Frame(funcName, callLine, parent);
        frames.push(newFrame);
    }

    /**
     * Return the top Frame while removing it from the stack
     * @return Frame the popped frame
     * @throws EmptyStackException if stack empty
     */
    public Frame pop() {
        if(frames.isEmpty()) throw new EmptyStackException(); // If the stack is empty throw exception
        return frames.pop();
    }

    /**
     * Returns the top Frame WITHOUT removing it from the stack
     * @return Frame the top frame
     * @throws EmptyStackException if stack empty
     */
    public Frame top() {
        if(frames.isEmpty()) throw new EmptyStackException(); // If the stack is empty throw exception
        return frames.peek();
    }

    // TODO : Should we do a func like getFrameAt that returns the frame at a specific depth of the deque ?

    // TODO : The swap should be like that or head and bottom ?
    /**
     * Swaps the top 2 frames of the stack
     * @throws IllegalStateException if there are not a least 2 frames
     */
    public void swap() {
        if(frames.size() < 2) throw new IllegalStateException("At least 2 frames are needed for swapping a stack !");
        Frame top = frames.pop();
        Frame second = frames.pop();
        frames.push(top);
        frames.push(second);
    }

    /**
     * Sets a variable in the current frame (the one on top)
     * @param name name of the var
     * @param value value of the var
     */
    public void setVariable(String name, Object value) {
        top().setVar(name, value);
    }

    /**
     * Returns the var
     * @param name Name of the var
     * @return Object the variable
     */
    public Object getVariable(String name) {
       return top().getVar(name);
    }

    /**
     * Updates the given variable
     * @param name var name
     * @param value var value
     * @return true if defined and changed, false otherwise
     */
    public boolean updateVariable(String name, Object value) {
        return top().updateVar(name, value);
    }

    /**
     * Returns the current stack size
     * @return int Size of the stack
     */
    public int size() {
        return frames.size();
    }

    /**
     * Checks if the stack is empty
     * @return true is stack empty, false otherwise
     */
    public boolean isEmpty() {
        return frames.isEmpty();
    }

    /**
     * Clears all the frames from the stack
     */
    public void clear() {
        frames.clear();
    }

    /**
     * Prints the current state of the stack
     * @return String the current state of the Stack
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack Trace:\n");
        int depth = 0;
        for (Frame frame : frames) {
            sb.append("  ").append(depth++).append(": ").append(frame).append("\n");
        }
        return sb.toString();
    }
}
