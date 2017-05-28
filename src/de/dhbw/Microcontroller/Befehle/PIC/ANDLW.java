package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;


/**
 * AND literal (k) with W
 * 'w and k -> w; CheckZero'
 */
public class ANDLW extends Instruction {

    public ANDLW(int instruction, int opcode, int argument){
        super(instruction, opcode,argument );
    }

    @Override
    public void execute(){
        copyFormerValues();
        int k = argument;
        int w = memory.getRegisterW();

        int result = k & w;
        memory.setRegisterW(result);


        if(result == 0)
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
        System.out.println(String.format("%04X", instruction) + ": ANDLW" + "  k: " + argument);
    }
}
