package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * No Operation
 * TODO: Ein Zyklus aussetzen?
 */

public class NOP extends Instruction {
        public NOP(int instruction, int opcode){
            super(instruction, opcode);
        }

        @Override
        public void execute(){
                cpu.register.pc++;
        }

        @Override
        public void displayDebugInfo()
        {
            System.out.println(String.format("%04X", instruction) + ": NOP");
        }
}
