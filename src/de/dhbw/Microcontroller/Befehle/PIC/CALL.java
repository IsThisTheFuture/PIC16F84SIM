package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Call subroutine
 * TODO:  'PC + 1 -> TOS; k -> PC (Bits 0-10); PCLATH (Bits 3-4) -> PC (Bits 11-12)''0 -> f (Bit b)'
 */
public class CALL extends Instruction {
    public CALL(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        int k = argument;

        stack.push(memory.getAddress(Const.PCL+1));
        // TODO: PCL, PC, PCLATH benutzen?
        memory.setAddress(Const.PCL, k);
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": CALL" + "  k: " + argument1);
        System.out.println("Subroutine wird aufgerufen: ");
    }


}
