package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Bit clear file (set Bit b to 0)
 * TODO:  '0 -> f (Bit b)'
 */

public class BCF extends Instruction {

    public BCF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte b = (byte) argument1;
        int f = argument2;

        // TODO: Hier stimmt was nicht
        b = (byte) (b & ~(1 << (f-1))); //das bit f wird in b  auf 0 gesetzt
        memory.setAddress(f, b);
        incrementProgramCounter();

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": BCF" + "  b: " + argument1 + "," + " f: " + argument2);
    }


}
