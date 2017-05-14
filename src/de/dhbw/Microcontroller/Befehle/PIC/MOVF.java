package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Move f
 * TODO: ->	'f -> d; CheckZero'
 */

public class MOVF extends Instruction {
    public MOVF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        int fValue  = memory.getAddress(f);
        fValue = (~ fValue & 0xFF);

        if (d == 0)
            memory.setRegisterW(fValue);
        else {}

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": MOVF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
