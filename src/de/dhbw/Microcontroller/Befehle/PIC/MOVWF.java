package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
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
        copyFormerValues();

        int f = argument;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        memory.setAddress(f, memory.getRegisterW());

        incrementProgramCounter();
        incrementRuntime();
        // Wenn der Timer beschrieben wird ist er für die nächsten 2 Zyklen gesperrt
        if (f == 1 && ((memory.getAbsoluteAddress(Const.STATUS) >> 5) & 1) == 0){
            Controller.inhibitTimer0 = 2;
            System.out.println("Inhibiting Timer....");
        }


        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": MOVWF" + "  f: " + argument);
    }

}
