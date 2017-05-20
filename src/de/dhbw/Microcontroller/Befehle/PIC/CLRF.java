package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Clear f
 * '0x00 -> f; 1 -> Z'
 */


public class CLRF extends Instruction{
    public CLRF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int f = argument;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        memory.setAddress(f, 0);

        setZeroFlag();

        incrementProgramCounter();
        incrementRuntime();
        incrementTimer0();


        // Wenn der Timer beschrieben wird ist er für die nächsten 2 Zyklen gesperrt
        if (f == 0x01 && ((memory.getAbsoluteAddress(Const.STATUS) >> 5) & 1) == 0)
            Controller.inhibitTimer0 = 2;

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": CLRF" + "  f: " + argument);
    }



}
