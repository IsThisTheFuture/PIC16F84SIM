package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Inclusive OR literal (k) with W
 * 'w or k -> w; CheckZero'
 */
public class IORLW extends Instruction {
    public IORLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int k = argument;

        memory.setRegisterW(memory.getRegisterW() | k);

        // CheckZero
        int w = memory.getRegisterW();
        if(w == 0)
            setZeroFlag();
        else
            clearZeroFlag();


        incrementProgramCounter();
        incrementRuntime();
        incrementTimer0();

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": IORLW" + "  k: " + argument);
    }
}
