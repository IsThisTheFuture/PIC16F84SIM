package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;

/*
 * Add literal and WDT
 * TODO: 'w + k -> w; CheckZero; CheckCarry, CheckDC'
 */

public class ADDLW extends Instruction {

    public ADDLW(int instruction, int opcode, int argument){
        super(instruction, opcode, argument);
    }

    @Override
    public void execute(){
        byte k = (byte) argument;
        memory.setRegisterW((byte) (memory.getRegisterW() + k));

        //TODO: Prüfen!!
        // CheckZero
        Byte w = memory.getRegisterW();
        Byte status = memory.getAddress(Const.STATUS);


        if(w == 0)
            status = (byte) (status | (1 << 2));
        else
            status = (byte) (status & ~(1 << 2));

        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));




        //cpu.register.w = (byte) (cpu.register.w + k);
        //cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ADDLW" + "  k: " + argument);
    }

}
