package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Subtract W from literal (k)
 * TODO: 'k - w -> w; Wenn w > k: 1 -> C; Wenn w = k: 1 -> Z; CheckDC'
 */

public class SUBLW extends Instruction {
        public SUBLW(int instruction, int opcode, int argument){
            super(instruction, opcode, argument);
        }

        @Override
        public void execute(){
            byte k = (byte) argument;

            memory.setRegisterW((byte) (k - memory.getRegisterW()));
            memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));


            //TODO Prüfen ob das Ergebnis stimmt
            //cpu.register.w = (byte) (k - cpu.register.w);
            //cpu.register.pc++;
        }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBLW" + "  k: " + argument);
    }
}
