package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Clear Watchdog Timer
 * TODO: ->	'0x00 -> WDT; 0 -> WDT prescaler; 1 -> TO; 1 -> PD'
 */
public class CLRWDT extends Instruction{
    public CLRWDT(int instruction, int opcode){
        super(instruction, opcode);
    }

    @Override
    public void execute(){
        copyFormerValues();


        copyCurrentValues();
        compareValues();

    }
}
