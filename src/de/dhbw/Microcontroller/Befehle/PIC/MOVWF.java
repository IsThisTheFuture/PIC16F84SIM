package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Move W to f
 * 'w -> f'
 */

public class MOVWF extends Instruction {
    public MOVWF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }


    @Override
    public void execute(){
        int f = argument;

        memory.setAddress(f, memory.getRegisterW());

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVWF" + "  f: " + argument);
    }

}
