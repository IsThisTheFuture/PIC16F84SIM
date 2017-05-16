package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Rotate Right f through Carry
 * TODO: 'Bits von f nach r. rot.; 1 -> C (wenn Bit 0 = 1 war)'
 */
@SuppressWarnings("ALL")
public class RRF extends Instruction {
    public RRF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute() {
        int d = argument1;
        int f = argument2;

        int fValue = memory.getAddress(f);
        int rotatingBit = fValue & 0b00000001;

        // Carry-Bit nur setzen, wenn das rotierte Bit 1 ist
        if(rotatingBit != 0)
            setCarryFlag();
        else
            clearCarryFlag();

        // Inhalt von Adresse f wird eine Stelle nach rechts geshiftet. Darf max. 255 sein
        fValue = (fValue / 2) & 255;

        // d=0 : fValue -> W     d=1 : fValue -> f
        if (d==0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);

        incrementProgramCounter();

    }

}
