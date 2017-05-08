package de.dhbw.Microcontroller.Befehle.PIC;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;


/*
 * AND literal (k) with W
 * TODO: 'w and k -> w; CheckZero'
 */

public class ANDLW extends Instruction {

    public ANDLW(int instruction, int opcode, int argument){
        super(instruction, opcode,argument );
    }

    @Override
    public void execute(){
        byte k = (byte) argument;

        memory.setRegisterW((byte) (memory.getRegisterW() & k));
        memory.setAddress(Const.PCL, (byte) (memory.getAddress(Const.PCL) + 1));





        //TODO Prüfen ob das Ergebnis stimmt
        //cpu.register.w = (byte) (cpu.register.w & k);





        //if (cpu.register.w == 0)
        //else
            //TODO: Zero Bit 0 setzen

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
