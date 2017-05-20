package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/**
 * Call subroutine
 * 'PC + 1 -> TOS; k -> PC (Bits 0-10); PCLATH (Bits 3-4) -> PC (Bits 11-12)'
 */
public class CALL extends Instruction {
    public CALL(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        copyFormerValues();

        int k = argument;
        int pclath = memory.getAddress(Const.PCLATH);


        // Top of Stack = PC + 1
        stack.push(memory.getPc() +1);

        // Die 11-Bit Addresse k wird in das 13-Bit Register geladen (Bit 0-10)
        // Die oberen 2 Bits werden von PCLATH (Bit 3 und 4) geladen
        pclath = pclath & 0b00011000;
        // Damit sie die richtige Wertigkeit haben multiplizieren wir sie mit 256
        /*
            0b0000000011000 *1
            0b0000000110000 *2
            0b0000001100000 *4
            0b0000011000000 *8
            0b0000110000000 *16
            0b0001100000000 *32
            0b0011000000000 *64
            0b0110000000000 *128
            0b1100000000000 *256
         */

        // Jetzt addieren wir beide und schreiben sie in PC
        memory.setPc(k+pclath);

        incrementRuntime();
        incrementRuntime();
        //TODO NOP?

        incrementTimer0();
        incrementTimer0();

        copyCurrentValues();
        compareValues();
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": CALL" + "  k: " + argument1);
        System.out.println("Subroutine wird aufgerufen: ");
    }


}
