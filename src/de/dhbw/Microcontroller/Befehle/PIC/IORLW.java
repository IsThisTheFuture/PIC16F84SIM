package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Inclusive OR literal (k) with W
 * TODO: 'w or k -> w; CheckZero'
 */

public class IORLW extends Instruction {
    public IORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        //TODO Prüfen ob das Ergebnis stimmt
        cpu.register.w = (byte) (cpu.register.w | k);
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": IORLW" + "  k: " + argument);
        System.out.println("W-Reg: " + cpu.register.w);

    }
}
