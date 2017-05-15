package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Subtract W from literal (k)
 * TODO: 'k - w -> w; Wenn w > k: 1 -> C; Wenn w = k: 1 -> Z; CheckDC'
 */

/*
The W register is subtracted (2’s
complement method) from the
eight-bit literal 'k'. The result is
placed in the W register.
TODO: Sollen wir das auch mit dem 2er Komplement machen?
 */

public class SUBLW extends Instruction {
        public SUBLW(int instruction, int opcode, int argument){
            super(instruction, opcode, argument);
        }

        @Override
        public void execute(){
            int k = argument;
            int w = memory.getRegisterW();

            //2er Komplement von w:
            w = ~w + 1;


            if(k == memory.getRegisterW())
                setZeroFlag();
            else
                clearZeroFlag();

            memory.setRegisterW(k + w);

            // CarryBit setzen
            if(memory.getRegisterW() > k)
                setCarryFlag();
            else
                clearCarryFlag();

            /*if (memory.getRegisterW() == 0)
                setZeroFlag();
            else
                clearZeroFlag();
             */



            incrementProgramCounter();
        }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBLW" + "  k: " + argument);
    }
}
