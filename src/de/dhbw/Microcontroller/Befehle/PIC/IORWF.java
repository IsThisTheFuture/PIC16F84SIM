package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Inclusive OR W with f
 * TODO: ->	'w or f -> d; CheckZero'
 */

public class IORWF extends Instruction {
    public IORWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        int fValue  = memory.getAddress(f);
        fValue = (memory.getRegisterW() | fValue);

        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);


        incrementProgramCounter();

    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": IORWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
