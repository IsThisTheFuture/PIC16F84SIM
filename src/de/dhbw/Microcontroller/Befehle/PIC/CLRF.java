package de.dhbw.Microcontroller.Befehle.PIC;

/*
 * Clear f
 * TODO: '0x00 -> f; 1 -> Z'
 */


import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

public class CLRF extends Instruction{
    public CLRF(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte f = (byte) argument;
        argument = 0;
        //memory.setRegisterW((byte) (memory.getRegisterW() ));
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

        //TODO: Speicher anlegen
        //cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo(){
        System.out.println(String.format("%04X", instruction) + ": CLRF" + "  f: " + argument);
    }



}
