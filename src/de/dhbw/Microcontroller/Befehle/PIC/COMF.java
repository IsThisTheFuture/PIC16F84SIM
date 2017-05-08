package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Complement f
 * TODO: '1 -> Z'
 */



public class COMF extends Instruction {
    public COMF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte f = (byte) argument1;
        byte d = (byte) argument2;

        memory.setAddress(f, memory.getRegisterW());

        memory.setAddress(memory.getRegisterW(), (byte) 0);
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": COMF" + "  f: " + argument);
    }



}
