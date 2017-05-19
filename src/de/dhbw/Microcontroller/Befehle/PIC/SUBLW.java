package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Subtract W from literal (k)
 * 'k - w -> w; Wenn w > k: 1 -> C; Wenn w = k: 1 -> Z; CheckDC'
 */

public class SUBLW extends Instruction {
        public SUBLW(int instruction, int opcode, int argument){
            super(instruction, opcode, argument);
        }

        @Override
        public void execute(){
            int k = argument;
            int w = memory.getRegisterW();


            // CarryBit prüfen. Hier steht w < k, da 2er Komplement.
            if(w < k)
                setCarryFlag();
            else
                clearCarryFlag();



            // Subtraktion mit 2er Komplement von w:
            w = ~w + 1;
            int result = (k + w) & 255;
            memory.setRegisterW(result);

            // DigitCarry prüfen
            int wRechts = w & 0b00001111;
            int kRechts = k & 0b00001111;
            if((wRechts + kRechts) >= 16){
                setDigitCarryFlag();
            } else {
                clearDigitCarryFlag();
            }


            // ZeroBit prüfen
            if (memory.getRegisterW() == 0)
                setZeroFlag();
            else
                clearZeroFlag();

            incrementProgramCounter();
            incrementRuntime();
        }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": SUBLW" + "  k: " + argument);
    }
}
