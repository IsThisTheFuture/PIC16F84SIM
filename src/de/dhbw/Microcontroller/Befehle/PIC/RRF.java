package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
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
        int currentCarryBit = getBit(Const.STATUS, 0);
        int rotatingBit = fValue & 0b00000001;


        // Carry-Bit nur setzen, wenn das rotierte Bit 1 ist
        if(rotatingBit != 0)
            setCarryFlag();
        else
            clearCarryFlag();

        // Inhalt von Adresse f wird eine Stelle nach rechts geshiftet. Darf max. 255 sein
        int result = (fValue / 2) & 255;
        //Jetzt das CarryBit hinzuaddieren (falls es gesetzt ist). Es muss noch geshiftet werden an Position 7
        result = result + (currentCarryBit * 128);

        // d=0 : fValue -> W     d=1 : fValue -> f
        if (d==0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        incrementProgramCounter();

    }

    private int getBit(int address, int position)
    {
        return ((memory.getAddress(address) >> position) & 1);
    }

}
