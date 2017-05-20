package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Bit set file ( set Bit b to 1)
 * '1 -> f (Bit b)'
 */

public class BSF extends Instruction {

    public BSF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int b = argument1;
        int f = argument2;


        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);


        int fValue = memory.getAddress(f);
        fValue = (fValue | (1 << b)); //das bit b wird in f  auf 1 gesetzt
        memory.setAddress(f,fValue);

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

        System.out.println(String.format("%04X", instruction) + ": BSF" + "  b: " + argument1 + "," + " f: " + argument2);
    }


}

