package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Move W to f
 * 'w -> f'
 */

public class MOVWF extends Instruction {
    public MOVWF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }


    @Override
    public void execute(){
        int f = argument;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);
        else {
            // Welche Bank ist ausgewählt?
            if (bankOneisSelected())
                f = f + 0x80;
        }



        memory.setAddress(f, memory.getRegisterW());

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVWF" + "  f: " + argument);
    }

}
