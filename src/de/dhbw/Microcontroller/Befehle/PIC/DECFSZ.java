package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Decrement f, Skip if 0
 * 'f - 1 -> d (wenn result != 0)'
 */

@SuppressWarnings("ALL")
public class DECFSZ extends Instruction {
    public DECFSZ(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        int fValue  = memory.getAddress(f);
        fValue--;

        if (fValue==0) {
            NOP nop = new NOP(0x0000, 0x0000);
            // TODO: Prüfen wie der ProgramCounter verändert wird
            nop.execute();
        } else {
            if (d == 0)
                memory.setRegisterW(fValue);
             else
                memory.setAddress(f, fValue);
        }

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}