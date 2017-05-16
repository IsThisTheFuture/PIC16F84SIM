package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Subtract W from f
 * 'f - w -> d; Wenn w > f: 1 -> C/DC; Wenn w = f: 1 -> Z'
 */

public class SUBWF extends Instruction {
    public SUBWF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1,argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;

        int fValue  = memory.getAddress(f);
        int w       = memory.getRegisterW();

        int result = (fValue - w) & 255;

        if (w > f){
            setCarryFlag();
            setDigitCarryFlag();
        } else {
            clearCarryFlag();
            clearDigitCarryFlag();
        }


        if(result == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        if (d == 0) {
            memory.setRegisterW(result);
        }
        else {
            memory.setAddress(f, result);
        }



        incrementProgramCounter();
    }






    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBWF" + "  d: " + argument1 + "," + " f: " + argument2);
    }
}
