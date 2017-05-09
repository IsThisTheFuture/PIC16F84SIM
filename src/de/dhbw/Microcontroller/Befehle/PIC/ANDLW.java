package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;


/*
 * AND literal (k) with W
 * 'w and k -> w; CheckZero'
 */

public class ANDLW extends Instruction {

    public ANDLW(int instruction, int opcode, int argument){
        super(instruction, opcode,argument );
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        memory.setRegisterW((byte) (memory.getRegisterW() & k));

        //TODO: Prüfen!!
        // CheckZero
        Byte w = memory.getRegisterW();
        Byte status = memory.getAddress(Const.STATUS);

        if(w == 0)
            status = (byte) (status | (1 << 2));
        else
            status = (byte) (status & ~(1 << 2));

        memory.setAddress(Const.STATUS, status);


        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));

        /*
                Set:
                myByte |= 1 << bit;

                Clear:
                myByte &= ~(1 << bit);
         */
        //cpu.register.pc++;
    }

    @Override
    public void displayDebugInfo()
    {
        System.out.println(String.format("%04X", instruction) + ": ANDLW" + "  k: " + argument);
    }
}
