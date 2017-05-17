package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Bit Test f, Skip if set
 * TODO: ->	'if b = 0: PC += 1; else PC += 2'
 */
public class BTFSS extends Instruction {

    public BTFSS(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int f = argument1;
        int b = argument2;


        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);

        int fValue = memory.getAddress(f);

        int bitValue = (fValue &(1 << b));
        if (bitValue==0) {
            incrementProgramCounter();
        }
        else {
            incrementProgramCounter();
            incrementProgramCounter();
        }
    }

    @Override
    public void displayDebugInfo(){

        System.out.println(String.format("%04X", instruction) + ": BTFSS " + "  b: " + argument1 + "," + " f: " + argument2);
    }


}
