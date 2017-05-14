package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Clear f
 * TODO: '0x00 -> f; 1 -> Z'
 */


public class CLRF extends Instruction{
    public CLRF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        int f = argument;
        memory.setAddress(f, 0);

        setZeroFlag();

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": CLRF" + "  f: " + argument);
    }



}
