package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Exclusive OR literal (k) with W
 * TODO: 'w xor k -> w; CheckZero'
 */

public class XORLW extends Instruction {
    public XORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        memory.setRegisterW((byte) (memory.getRegisterW() ^ k));
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

        //TODO Prüfen ob das Ergebnis stimmt
        //cpu.register.w = (byte) (cpu.register.w ^ k);
        //cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": XORLW" + "  k: " + argument);
    }
}