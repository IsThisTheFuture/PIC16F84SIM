package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Exclusive OR W with f
 * 'w xor f -> d; CheckZero'
 */
public class XORWF extends Instruction {
    public XORWF(int instruction, int opcode, int argument1, int argument2){
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
        int w       = memory.getRegisterW();

        int result = (fValue ^ w);


        if (d == 0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        incrementProgramCounter();
        incrementRuntime();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": XORWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
