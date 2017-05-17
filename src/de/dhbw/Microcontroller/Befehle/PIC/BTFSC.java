package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Bit Test, Skip if Clear
 * TODO: ->	'if b = 1: PC += 1; else PC += 2'
 */

public class BTFSC extends Instruction {

    public BTFSC(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int f = argument1;
        int b = argument2;

        b = (f &(1 << b)); //das bit b in f wird verundet um zu prüfen was für ein wert dort steht
        if (b==1) {
            incrementProgramCounter();
        }
        else {
            incrementProgramCounter();
            incrementProgramCounter();
        }
    }

    @Override
    public void displayDebugInfo(){

        System.out.println(String.format("%04X", instruction) + ": BTFSC " + "  b: " + argument1 + "," + " f: " + argument2);
    }


}