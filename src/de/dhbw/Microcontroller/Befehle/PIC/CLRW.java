package de.dhbw.Microcontroller.Befehle.PIC;

/*
 * Clear W
 * '0x00 -> W; 1 -> Z'
 */

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;


public class CLRW extends Instruction {
    public CLRW(int instruction, int opcode){
        super(instruction, opcode);
    }

    @Override
    public void execute(){
        memory.setRegisterW( (byte) 0);

        setZeroFlag();

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": CLRW" + "  f: " + argument);
    }

}
