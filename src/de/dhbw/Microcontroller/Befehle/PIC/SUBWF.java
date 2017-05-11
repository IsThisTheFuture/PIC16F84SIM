package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Subtract W from f
 * TODO: ->	'f - w -> d; Wenn w > f: 1 -> C/DC; Wenn w = f: 1 -> Z'
 */

public class SUBWF extends Instruction {
    public SUBWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1,argument2);
    }

    @Override
    public void execute(){
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        Byte fValue  = memory.getAddress(f);
        fValue = (byte) ((~ fValue & 0xFF)-memory.getRegisterW());

        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);



        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));
    }




    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
