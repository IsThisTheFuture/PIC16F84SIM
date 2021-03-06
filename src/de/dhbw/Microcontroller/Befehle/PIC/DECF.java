package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Decrement f
 * 'f - 1 -> d; CheckZero'
 */
public class DECF extends Instruction {
    public DECF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
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
        int result = (fValue - 1) & 255;


        if (d==0) {
            memory.setRegisterW(result);
        }
        else {
            memory.setAddress(f, result);
        }

        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();

        incrementProgramCounter();
        incrementRuntime();
        incrementTimer0();
        incrementTimer0();


        // Wenn der Timer beschrieben wird ist er für die nächsten 2 Zyklen gesperrt
        if (f == 0x01 && ((memory.getAbsoluteAddress(Const.STATUS) >> 5) & 1) == 0)
            Controller.inhibitTimer0 = 2;

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": DECF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}