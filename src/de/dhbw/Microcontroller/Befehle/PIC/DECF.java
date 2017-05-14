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
        fValue = fValue -1;


        //TODO Prüfen
        if (d==0) {
            memory.setRegisterW(fValue);
        }
        else {
            memory.setAddress(f, fValue);
        }

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}