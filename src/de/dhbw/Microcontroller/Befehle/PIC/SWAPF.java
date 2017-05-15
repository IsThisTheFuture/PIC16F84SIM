package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Swap nibbles in f
 * TODO: 'Tausche untere/obere 4 Bit von f -> d'
 */

public class SWAPF extends Instruction {
    public SWAPF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }


    @Override
    public void execute() {
        int d = argument1;
        int f = argument2; // Register an der Adresse f

        int leftNibble  = memory.getAddress(argument2) & 0b11110000;
        int rightNibble = memory.getAddress(argument2) & 0b00001111;

        // Durch teilen / multiplizieren schieben wir die bits und führen die Nibble danach wieder zusammen
        int result = (leftNibble / 16) + (rightNibble * 16);

        // Wenn d=0, dann result -> w     sonst result -> f
        if(d == 0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f, result);

        incrementProgramCounter();

    }



}
