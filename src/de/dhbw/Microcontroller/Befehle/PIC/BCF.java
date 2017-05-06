package de.dhbw.Microcontroller.Befehle.PIC;

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

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": BCF" + "  b: " + argument1 + "," + " f: " + argument2);
    }


}
