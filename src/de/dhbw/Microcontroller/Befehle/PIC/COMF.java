package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Complement f
 * TODO: '1 -> Z'
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
        fValue = (~ fValue & 0xFF);

        if (d==0)
            memory.setRegisterW(fValue);
        else
            memory.setAddress(f,fValue);



        //TODO: wenn d=0: Das Ergebnis-> W. f bleibt unverändert. d=1 Das Ergebnis -> f.

        incrementProgramCounter();
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": COMF" + "  d: " + argument1 + "," + "  f: " + argument2);
    }



}
