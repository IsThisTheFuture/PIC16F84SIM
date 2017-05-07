package de.dhbw.Microcontroller;

/*
 * Hier wurde eine Singleton-Pattern implementiert.
 * So ist sichergestellt, dass es global nur eine Instanz dieser Klasse gibt.
 * Siehe: http://crunchify.com/thread-safe-and-a-fast-singleton-implementation-in-java/
 */

public class CPU {
    public Register register = new Register();

    private static final CPU emulator = new CPU();

    protected CPU() {}

    public static CPU getInstance() {
        return emulator;
    }
}