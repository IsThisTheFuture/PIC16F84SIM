package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Increment f
 * TODO: Skip if 0	->	'f + 1 -> d (wenn result != 0)'
 */

public class INCFSZ extends Instruction {
    public INCFSZ(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        int fValue  = memory.getAddress(f);

        //TODO: Ist das  & 0xFF wegen des alten datentyp byte hier stehen geblieben?
        fValue = fValue + 1;

        if (fValue==0) {
            //TODO: dann wird der nächste Befehl im Programm übersprungen, und mit dem übernächsten weitergebacht.
        }
        else {
            if (d == 0)
                memory.setRegisterW(fValue);
            else
                memory.setAddress(f, fValue);
        }

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": INCFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
