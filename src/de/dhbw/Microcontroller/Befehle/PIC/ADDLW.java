package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add literal and WDT
 * TODO: 'w + k -> w; CheckZero; CheckCarry, CheckDC'
 */

public class ADDLW extends Instruction {

    public ADDLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;
        memory.setRegisterW((byte) (memory.getRegisterW() + k));

        Byte w = memory.getRegisterW();
        if(w == 0)
            setZeroFlag();
        else
            clearZeroFlag();

        //TODO Wie CheckCarry und CheckDC prüfen????

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDLW" + "  k: " + argument);
    }
}
