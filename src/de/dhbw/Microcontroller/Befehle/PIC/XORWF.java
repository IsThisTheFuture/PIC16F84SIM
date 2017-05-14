package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Exclusive OR W with f
 * TODO: ->	'w xor f -> d; CheckZero'
 */
public class XORWF extends Instruction {
    public XORWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        int fValue  = memory.getAddress(f);
        fValue = (fValue ^ memory.getRegisterW());


        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": XORWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
