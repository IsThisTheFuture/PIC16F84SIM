package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Bit Test f, Skip if set
 * 'if b = 0: PC += 1; else PC += 2'
 */
public class BTFSS extends Instruction {

    public BTFSS(int instruction, int opcode, int argument1, int argument2){
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
        if (bitValue==0) {
            incrementProgramCounter();
        }
        else {
            incrementProgramCounter();
            NOP NOP = new NOP(0x0000, 0x0000);
            NOP.execute();
        }

        incrementRuntime();
        incrementTimer0();

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo(){

        System.out.println(String.format("%04X", instruction) + ": BTFSS " + "  b: " + argument1 + "," + " f: " + argument2);
    }




}
