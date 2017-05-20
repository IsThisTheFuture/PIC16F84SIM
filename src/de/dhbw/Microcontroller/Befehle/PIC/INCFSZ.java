package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Increment f, Skip if 0
 * 'f + 1 -> d (wenn result != 0)'
 */

@SuppressWarnings("Duplicates")
public class INCFSZ extends Instruction {
    public INCFSZ(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);


        int fValue  = memory.getAddress(f);

        //Im Register darf max 255 stehen. Wenn 255 inkrementiert wird, steht wieder 0 drin
        fValue = (fValue + 1) & 255;

        if (fValue==0) {
            NOP nop = new NOP(0x0000, 0x0000);
            nop.execute();
        }
        else {
            if (d == 0)
                memory.setRegisterW(fValue);
            else
                memory.setAddress(f, fValue);
        }

        incrementProgramCounter();
        incrementRuntime();


        // Wenn der Timer beschrieben wird ist er für die nächsten 2 Zyklen gesperrt
        if (f == 0x01 && ((memory.getAbsoluteAddress(Const.STATUS) >> 5) & 1) == 0)
            Controller.inhibitTimer0 = 2;
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": INCFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
