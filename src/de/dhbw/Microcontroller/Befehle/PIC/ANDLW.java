package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;


/*
 * AND literal (k) with W
 * TODO: 'w and k -> w; CheckZero'
 */

public class ANDLW extends Instruction {

    public ANDLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;


        //TODO Prüfen ob das Ergebnis stimmt
        cpu.register.w = (byte) (cpu.register.w & k);
        cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ANDLW" + "  k: " + argument);
        System.out.println("W-Reg: " + cpu.register.w);

    }
}
