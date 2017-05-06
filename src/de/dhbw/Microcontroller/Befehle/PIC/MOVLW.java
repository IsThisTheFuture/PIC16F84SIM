package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Move literal (k) to W
 * TODO:  'k -> w'
 */

public class MOVLW extends Instruction {

    public MOVLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;
        cpu.register.w = k;
        cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVLW" + "  k: " + argument);
        System.out.println("W-Reg: " + cpu.register.w);
    }
}
