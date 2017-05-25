package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
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
        memory.setSleepMode(true);
        memory.setWatchDogTimer(0x00);

        int status = memory.getAddress(Const.STATUS);
        status = (status & ~(1 << 3)); // das PD Bit wird auf 0 gesetzt
        status = (status | (1 << 4)); // das TO Bit wird auf 1 gesetzt
        memory.setAddress(Const.STATUS, status);

        incrementRuntime();
        incrementProgramCounter();

    }
}
