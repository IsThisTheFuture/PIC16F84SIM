package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Complement f
 * 'invertiere f -> d; CheckZero'
 */



public class COMF extends Instruction {
    public COMF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        int d = argument1;
        int f = argument2;


        int fValue  = memory.getAddress(f);
        int result = ~fValue & 255;

        // ZeroFlag prüfen
        if (result == 0)
            setZeroFlag();
        else
            clearZeroFlag();

        // Ergebnis in Destination schreiben
        if (d == 0)
            memory.setRegisterW(result);
        else
            memory.setAddress(f,result);


        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": COMF" + "  d: " + argument1 + "," + "  f: " + argument2);
    }



}
