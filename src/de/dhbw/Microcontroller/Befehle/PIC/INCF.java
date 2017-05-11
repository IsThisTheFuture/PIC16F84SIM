package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Increment f
 * TODO: ->	'f + 1 -> d; CheckZero'
 */

public class INCF extends Instruction {
    public INCF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        Byte fValue  = memory.getAddress(f);
        fValue = (byte) ((~ fValue & 0xFF)+1);

        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);

        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": INCF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}