package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * TODO: 'w and f -> d; CheckZero'
 */
public class ANDWF extends Instruction {

    public ANDWF(int instruction, int opcode, int argument1, int argument2) {
        super(instruction, opcode, argument1, argument2);
    }


    @Override
    public void execute() {
        int f = argument1;

        memory.setRegisterW(memory.getRegisterW() + f);

        incrementProgramCounter();
    }

        @Override
        public void displayDebugInfo ()
        {
            System.out.println(String.format("%04X", instruction) + ": ANDWF" + "  k: " + argument);
        }
    }
