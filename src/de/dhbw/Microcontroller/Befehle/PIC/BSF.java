package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Created by TInf on 08.05.2017.
 */

public class BSF extends Instruction {

    public BSF(int instruction, int opcode, int argument1, int argument2){
        super(instruction, opcode, argument1, argument2);
    }

    @Override
    public void execute(){
        byte b = (byte) argument1;
        int f = argument2;

        b = (byte) (b |(1 << (f-1))); //das bit f wird in b  auf 1 gesetzt
        //memory.setRegisterW((byte) (memory.getRegisterW() ));
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

        // cpu.register.pc++;

    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": BSF" + "  b: " + argument1 + "," + " f: " + argument2);
    }


}

