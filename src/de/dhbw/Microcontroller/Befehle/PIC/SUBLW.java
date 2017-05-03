package de.dhbw.Microcontroller.Befehle.PIC;

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

            //TODO Prüfen ob das Ergebnis stimmt
            cpu.register.w = (byte) (k - cpu.register.w);
        }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBLW" + "  k: " + argument);
        System.out.println("W-Reg: " + cpu.register.w);

    }
}
