package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Subtract W from f
 * 'f - w -> d; Wenn w > f: 1 -> C/DC; Wenn w = f: 1 -> Z'
 */

public class SUBWF extends Instruction {
    public SUBWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1,argument2);
    }

    @Override
    public void execute(){
        copyFormerValues();
        int d = argument1;
        int f = argument2;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        int fValue  = memory.getAddress(f);
        int w       = ~memory.getRegisterW() + 1; // 2er Komplement

        int result = (fValue + w) & 255;


        if (w > fValue){
            setCarryFlag();
            setDigitCarryFlag();
        } else {
            clearCarryFlag();
            clearDigitCarryFlag();
        }


        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        if (d == 0) {
            memory.setRegisterW(result);
        }
        else {
            memory.setAddress(f, result);
        }



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
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
