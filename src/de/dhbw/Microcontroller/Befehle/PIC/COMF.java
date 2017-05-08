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


        Byte fValue  = memory.getAddress(f);
        fValue = (byte) (~ fValue & 0xFF);

        if (d==0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f,fValue);


        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": COMF" + "  f: " + argument);
    }



}
