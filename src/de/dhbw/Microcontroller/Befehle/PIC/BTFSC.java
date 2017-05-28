package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Bit Test, Skip if Clear
 * 'if b = 1: PC += 1; else PC += 2'
 */
public class BTFSC extends Instruction {

    public BTFSC(int instruction, int opcode, int argument1, int argument2){
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

        int bitValue = getBit(f, b);

        if (bitValue==1) {
            incrementProgramCounter();
        }
        else {
            incrementProgramCounter();
            NOP NOP = new NOP(0x000, 0x0000);
            NOP.execute();
        }

        incrementRuntime();
        incrementTimer0();

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo(){

        System.out.println(String.format("%04X", instruction) + ": BTFSC " + "  b: " + argument1 + "," + " f: " + argument2);
    }
}