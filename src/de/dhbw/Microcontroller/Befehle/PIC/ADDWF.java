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
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        memory.setRegisterW((byte) (memory.getRegisterW() + f));

        //TODO: Prüfen!! Ergebnis nicht in W speichern, sonder in d?


        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
