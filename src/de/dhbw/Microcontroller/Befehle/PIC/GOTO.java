package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Go to address
 * TODO: 'k -> PC (Bits 0-10); PCLATH (Bits 3-4) -> PC (11-12)'
 */
public class GOTO extends Instruction {
    public GOTO(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        // Program Counter setzen TODO Auf Korrektheit prüfen
        cpu.register.pc = k;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": GOTO" + "  k: " + argument);
        System.out.println("ProgramCounter neu gesetzt: " + cpu.register.pc);

    }
}
