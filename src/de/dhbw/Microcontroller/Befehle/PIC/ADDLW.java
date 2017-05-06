package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add literal and WDT
 * TODO: 'w + k -> w; CheckZero; CheckCarry, CheckDC'
 */

public class ADDLW extends Instruction {

    public ADDLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        cpu.register.w = (byte) (cpu.register.w + k);
        cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDLW" + "  k: " + argument);
        System.out.println("W-Reg: " + cpu.register.w);
    }

}
