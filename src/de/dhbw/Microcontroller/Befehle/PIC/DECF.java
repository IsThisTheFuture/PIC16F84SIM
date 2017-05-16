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
        int result = (fValue -1) & 255;


        if (d==0) {
            memory.setRegisterW(result);
        }
        else {
            memory.setAddress(f, result);
        }

        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}