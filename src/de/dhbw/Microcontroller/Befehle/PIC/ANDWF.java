package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Created by TInf on 08.05.2017.
 */
public class ANDWF extends Instruction {

    public ANDWF(int instruction, int opcode, int argument1, int argument2) {
        super(instruction, opcode, argument1, argument2);
    }


    @Override
    public void execute() {
        byte f = (byte) argument1;

        memory.setRegisterW((byte) (memory.getRegisterW() + f));
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

    }

        @Override
        public void displayDebugInfo ()
        {
            System.out.println(String.format("%04X", instruction) + ": ANDWF" + "  k: " + argument);
        }
    }
