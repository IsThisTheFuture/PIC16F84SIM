package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Call subroutine
 * TODO:  'PC + 1 -> TOS; k -> PC (Bits 0-10); PCLATH (Bits 3-4) -> PC (Bits 11-12)''0 -> f (Bit b)'
 */
public class CALL extends Instruction {
    public CALL(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        //TODO: Top of Stack = cpu.register.pc ++;
        //...

    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": CALL" + "  k: " + argument1);
        System.out.println("Subroutine wird aufgerufen: ");

    }


}
