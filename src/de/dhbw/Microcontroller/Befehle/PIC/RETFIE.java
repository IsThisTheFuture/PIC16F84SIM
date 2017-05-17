package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Return from interrupt
 * TODO: 'TOS -> PC; 1 -> GIE'
 */

public class RETFIE extends Instruction {
    public RETFIE(int instruction, int opcode){
        super(instruction, opcode);
    }

    @Override
    public void execute(){

    }
}
