package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Decrement f
 * TODO: 'f - 1 -> d; CheckZero'
 */

public class DECF extends Instruction {
    public DECF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;


        int fValue  = memory.getAddress(f);
        fValue = (~ fValue & 0xFF);


        if (d==0) {
            fValue = ((~ fValue & 0xFF)-1);
            memory.setRegisterW(fValue);
        }
        else {
            fValue = ((~fValue & 0xFF) - 1);
            memory.setAddress(f, fValue);
        }

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}