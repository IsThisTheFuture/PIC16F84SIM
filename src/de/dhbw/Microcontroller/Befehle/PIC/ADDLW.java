package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add literal and W
 * 'w + k -> w; CheckZero; CheckCarry, CheckDC'
 */

public class ADDLW extends Instruction {

    public ADDLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        copyFormerValues();
        int k = argument;
        int w = memory.getRegisterW();

        //CarryFlag prüfen

        if ((w+k)>255)
            setCarryFlag();
        else
            clearCarryFlag();

        // DigitCarry prüfen
        int wRechts = w & 0b00001111;
        int kRechts = k & 0b00001111;
        if((wRechts + kRechts) >= 16){
            setDigitCarryFlag();
        } else {
            clearDigitCarryFlag();
        }

        int result = (w+k) & 255;
        memory.setRegisterW(result);


        if(memory.getRegisterW() == 0)
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
        System.out.println(String.format("%04X", instruction) + ": ADDLW" + "  k: " + argument);
    }
}
