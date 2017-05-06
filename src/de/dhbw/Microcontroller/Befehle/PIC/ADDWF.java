package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add W and f
 * TODO: 'w + f -> d; CheckZero; CheckCarry; CheckDC'
 */

public class ADDWF extends Instruction {
    public ADDWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1,argument2);
    }

    @Override
    public void execute(){
        byte k = (byte) argument2;
        cpu.register.pc++;

    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDWF" + "  f: " + argument2);
    }
}
