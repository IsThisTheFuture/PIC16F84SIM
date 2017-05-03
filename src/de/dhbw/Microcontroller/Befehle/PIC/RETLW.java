package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Return with literal (k) in W
 * TODO: 'k -> w; TOS -> PC'
 */
public class RETLW extends Instruction {
    public RETLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }


    @Override
    public void execute(){
        byte k = (byte) argument;

        cpu.register.w = k;
        //TODO: TOS -> PC

    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": RETLW" + "  k: " + argument);
    }


}
