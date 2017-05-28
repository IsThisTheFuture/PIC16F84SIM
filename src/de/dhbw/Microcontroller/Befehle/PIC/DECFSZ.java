package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Decrement f, Skip if 0
 * 'f - 1 -> d (wenn result != 0)'
 */
public class DECFSZ extends Instruction {
    public DECFSZ(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void execute(){
        copyFormerValues();

        int d = argument1;
        int f = argument2;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        int fValue  = memory.getAddress(f);
        fValue--;

        if (fValue==0) {
            if (d == 0)
                memory.setRegisterW(fValue);
            else
                memory.setAddress(f, fValue);

            NOP nop = new NOP(0x0000, 0x0000);
            nop.execute();
        } else {
            if (d == 0)
                memory.setRegisterW(fValue);
             else
                memory.setAddress(f, fValue);
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
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}