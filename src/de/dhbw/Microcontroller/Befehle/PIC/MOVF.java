package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Move f
 * 'f -> d; CheckZero'
 */
public class MOVF extends Instruction {
    public MOVF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int d = argument1;
        int f = argument2;

        // Indirekte Addressierung
        if(f == Const.IND)
            f = memory.getAddress(Const.FSR);


        int fValue  = memory.getAddress(f);

        if (d == 0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f, fValue);

        // ZeroBit prüfen
        if(fValue == 0)
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
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": MOVF" + "  d: " + argument1 + "," + " f: " + argument2);
    }



}
