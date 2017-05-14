package de.dhbw.Microcontroller;


public class Stack {
    private static final Stack s = new Stack();
    private static final int STACK_SIZE = 8;

    private Integer[] stack;
    private int top;


    protected Stack(){
        this.stack = new Integer[8];
        top = -1;
        initializeStack();
    }

    private void initializeStack() {
        for (int i = 0; i < stack.length; i++){
            stack[i] = null;
        }
    }

    public static Stack getInstance(){
        return s;
    }


    public void push(int b) {
        stack[++top] = b;
    }
    public int pop() {
        return stack[top--];
    }
    public int peek() {
        return stack[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == STACK_SIZE - 1);
    }







}
