package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Exclusive OR literal (k) with W
 * TODO: 'w xor k -> w; CheckZero'
 */

public class XORLW extends Instruction {
    public XORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        memory.setRegisterW((byte) (memory.getRegisterW() ^ k));
        if (memory.getRegisterW() == 0)
            setZeroFlag();

        incrementProgramCounter();

        //TODO Prüfen ob das Ergebnis stimmt
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": XORLW" + "  k: " + argument);
    }
}