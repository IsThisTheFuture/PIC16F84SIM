package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Inclusive OR literal (k) with W
 * TODO: 'w or k -> w; CheckZero'
 */

public class IORLW extends Instruction {
    public IORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        memory.setRegisterW((byte) (memory.getRegisterW() | k));

        // CheckZero
        Byte w = memory.getRegisterW();
        if(w == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": IORLW" + "  k: " + argument);
    }
}
