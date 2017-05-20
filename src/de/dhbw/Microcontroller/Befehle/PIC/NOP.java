package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * No Operation
 */

public class NOP extends Instruction {
        public NOP(int instruction, int opcode){
            super(instruction, opcode);
        }

        @Override
        public void execute(){
            copyFormerValues();

            incrementProgramCounter();
            incrementRuntime();


            copyCurrentValues();
            compareValues();
        }

        @Override
        public void displayDebugInfo()
        {
            System.out.println(String.format("%04X", instruction) + ": NOP");
        }
}
