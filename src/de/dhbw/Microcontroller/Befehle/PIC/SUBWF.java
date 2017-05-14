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
        int d = argument1;
        int f = argument2;

        int fValue  = memory.getAddress(f);
        fValue = fValue - memory.getRegisterW();

        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);


        incrementProgramCounter();
    }




    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
