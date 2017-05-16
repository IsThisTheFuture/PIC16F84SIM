package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add W and f
 * TODO: 'w + f -> d; CheckZero; CheckCarry; CheckDC'
 */

public class ADDWF extends Instruction {
    public ADDWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1,argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        int w = memory.getRegisterW();
        int fValue = memory.getAddress(f);


        // CarryBit prüfen.
        if((fValue + w) > 255)
            setCarryFlag();
        else
            clearCarryFlag();

        // DigitCarry prüfen
        int wRechts = w      & 0b00001111;
        int fRechts = fValue & 0b00001111;
        if((wRechts + fRechts) >= 16){
            setDigitCarryFlag();
        } else {
            clearDigitCarryFlag();
        }


        // Addition durchführen, Ergebnis ist max. 255
        int result = (memory.getRegisterW() + memory.getAddress(f)) & 255;

        // Ergebnis in Destination schreiben
        if(d == 0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        // Zero Flag prüfen
        if(result == 0)
            setZeroFlag();
        else setZeroFlag();


        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
