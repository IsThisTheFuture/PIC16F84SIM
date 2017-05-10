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
        byte d = (byte) argument1;
        byte f = (byte) argument2;

        Byte fValue  = memory.getAddress(f);
        fValue = (byte) ((~ fValue & 0xFF)+1);

        if (fValue==0) {
            //TODO: dann wird der nächste Befehl im Programm übersprungen, und mit dem übernächsten weitergebacht.
        }
        else {
            if (d == 0)
                memory.setRegisterW(fValue);
            else
                memory.setAddress(f, fValue);
        }
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": INCFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
