package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Increment f
 * 'f + 1 -> d; CheckZero'
 */

public class INCF extends Instruction {
    public INCF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        int fValue  = memory.getAddress(f);
        int result = (fValue + 1) & 255;

        // Ergebnis an Destination schreiben
        if (d == 0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        // ZeroBit prüfen
        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        incrementProgramCounter();
        incrementRuntime();

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": INCF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
