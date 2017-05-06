package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Move W to f
 * TODO: 'w -> f'
 */

public class MOVWF extends Instruction {
    public MOVWF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }


    @Override
    public void execute(){
        byte f = (byte) argument;

        //TODO: Speicher.f = cpu.register.w
        cpu.register.pc++;

    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVWF" + "  f: " + argument);

    }

}
