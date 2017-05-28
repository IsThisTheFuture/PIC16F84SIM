package de.dhbw.Microcontroller;

/**
 * Hier wird der Stack mit 8 Einträgen angelegt
 */
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

    /**
     * Legt eine Adresse auf den Stack
     * @param b
     */
    public void push(int b) {
        stack[++top] = b;
    }

    /**
     * Holt das oberste Element des Stacks heraus
     * @return
     */
    public int pop() {
        return stack[top--];
    }

    /**
     * Liefert den gesamten Inhalt des Stacks
     * @return stack
     */
    public Integer[] getStackContent(){
        return this.stack;
    }
}
