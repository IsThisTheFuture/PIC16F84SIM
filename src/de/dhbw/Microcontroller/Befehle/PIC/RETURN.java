package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Return from subroutine
 * 'TOS -> PC'
 */

public class RETURN extends Instruction {
    public RETURN(int instruction, int opcode){
        super(instruction, opcode);
    }


    @Override
    public void execute(){
        memory.setPc(stack.pop());
        memory.setAddress(Const.PCL, memory.getPc()&255);
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": RETURN");
    }

}
