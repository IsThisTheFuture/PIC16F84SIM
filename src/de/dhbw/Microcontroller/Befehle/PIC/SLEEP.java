package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Go into standby mode
 * TODO: '0x00 -> WDT; 0 -> WDT prescaler; 1 -> TO; 0 -> PD'
 */

public class SLEEP extends Instruction {
    public SLEEP(int instruction, int opcode){
        super(instruction, opcode);
    }

    @Override
    public void execute(){

        incrementRuntime();
    }
}
