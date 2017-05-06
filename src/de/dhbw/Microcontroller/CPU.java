package de.dhbw.Microcontroller;

/*
 * Hier wurde eine Singleton-Pattern implementiert.
 * So ist sichergestellt, dass es global nur eine Instanz dieser Klasse gibt.
 * Siehe: http://crunchify.com/thread-safe-and-a-fast-singleton-implementation-in-java/
 *
 * Die CPU startet einen Thread und wartet auf Input.
 */


import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;
import java.util.List;

public class CPU {//implements Runnable {
    public Register register = new Register();
    public InstructionDecoder decoder = new InstructionDecoder();
    private Controller controller = new Controller();

    private static final CPU emulator = new CPU();

    protected CPU() {
    }

    // Runtime initialization
    // By default ThreadSafe
    public static CPU getInstance() {
        return emulator;
    }


    // Einstiegspunkt des Threads
    /*
    @Override
    public void run() {
            List<Instruction> programCode;

            programCode = decoder.getProgramCode();
            if (programCode.isEmpty())
                System.err.println("programcode is empty");

            System.out.println("Anzahl der Befehle: " + programCode.size());

            for (int i = 0; i < programCode.size(); i++) {

                controller.setCurrentRow(i);
                programCode.get(i).execute();
                programCode.get(i).displayDebugInfo();





                // TODO: Nach jeder Instruktion UI neu laden. Wie??
                try {
                    Thread.sleep(500);
                } catch (Exception e){

                }

            }

        }
        */


        // TODO: Rücksetzen, Register leeren und bestimmte Register mit Werten Vorbelegen
//    }
}