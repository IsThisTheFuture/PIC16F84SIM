package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Exclusive OR literal (k) with W
 * 'w xor k -> w; CheckZero'
 */

public class XORLW extends Instruction {
    public XORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int k = argument;

        memory.setRegisterW(memory.getRegisterW() ^ k);

        if (memory.getRegisterW() == 0)
            setZeroFlag();
        else
            clearZeroFlag();

        incrementProgramCounter();
        incrementRuntime();


        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": XORLW" + "  k: " + argument);
    }
}