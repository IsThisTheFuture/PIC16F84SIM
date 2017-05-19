package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Move literal (k) to W
 * 'k -> w'
 */

public class MOVLW extends Instruction {

    public MOVLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        int k = argument;

        memory.setRegisterW(k);

        incrementProgramCounter();
        incrementRuntime();


    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVLW" + "  k: " + argument);
    }
}
