package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Return from interrupt
 * 'TOS -> PC; 1 -> GIE'
 */

public class RETFIE extends Instruction {
    public RETFIE(int instruction, int opcode){
        super(instruction, opcode);
    }

    @Override
    public void execute(){
        int returnAddress = stack.pop();
        memory.setPc(returnAddress);
        memory.setAbsoluteAddress(Const.PCL, returnAddress&255);


        int byteValue = memory.getAbsoluteAddress(Const.INTCON);
        byteValue = (byteValue | (1 << (7)));
        memory.setAbsoluteAddress(Const.INTCON, byteValue);

        incrementRuntime();
        incrementTimer0();
    }
}
