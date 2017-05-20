package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Rotate Left f through Carry
 * 'Bits von f nach l. rot.; 1 -> C (wenn Bit 7 = 1 war)'
 */

public class RLF extends Instruction {
    public RLF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute() {
        copyFormerValues();

        int d = argument1;
        int f = argument2;


        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);


        int fValue = memory.getAddress(f);
        int currentCarryBit = getBit(Const.STATUS, 0);
        int rotatingBit = fValue & 0b10000000;

        // Carry-Bit nur setzen, wenn das rotierte Bit 1 ist
        if(rotatingBit != 0)
            setCarryFlag();
        else
            clearCarryFlag();

        // Inhalt von Adresse f wird eine Stelle nach links geshiftet. Darf Max 255 sein
        int result = (fValue * 2) & 255;

        //Jetzt das CarryBit hinzuaddieren (falls es gesetzt ist)
        result = result + currentCarryBit;

        // d=0 : fValue -> W     d=1 : fValue -> f
        if (d==0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        incrementProgramCounter();
        incrementRuntime();
        incrementTimer0();

        // Wenn der Timer beschrieben wird ist er für die nächsten 2 Zyklen gesperrt
        if (f == 0x01 && ((memory.getAbsoluteAddress(Const.STATUS) >> 5) & 1) == 0)
            Controller.inhibitTimer0 = 2;


        copyCurrentValues();
        compareValues();

    }

}
