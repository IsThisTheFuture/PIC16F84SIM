package de.dhbw.Microcontroller;

import de.dhbw.Constants.*;
public class Memory {

    private static final Memory memory = new Memory();

    private Integer registerW;
    private Integer pc;
    private Integer[] registers;

    protected Memory(){
        this.registers = new Integer[256];
        initializeMemory();
    }

    public static Memory getInstance() {
        return memory;
    }

    public void initializeMemory() {
        // W ist 8 Bit, pc ist 13 Bit
        registerW =  0b00000000;
        pc        =  0b0000000000000;

        for (int i = 0; i < registers.length; i++){
            registers[i] = 0b00000000;
        }

        /*
         *   Legende:
         *   x = unknown, u = unchanged. - = unimplemented, read as '0', q = value depends on condition
         */

                                                         // Start- bzw. Reset-Wert
        registers[Const.IND]        = 0b00000000;
        registers[Const.TMR0]       = 0b00000000; // xxxx xxxx
        registers[Const.PCL]        = 0b00000000; // 0000 0000
        registers[Const.STATUS]     = 0b00011000; // 0001 1xxx
        registers[Const.FSR]        = 0b00000000; // xxxx xxxx
        registers[Const.PORTA]      = 0b00000000; // ---x xxxx
        registers[Const.PORTB]      = 0b00000000; // xxxx xxxx
        registers[Const.EEDATA]     = 0b00000000; // xxxx xxxx
        registers[Const.EEADR]      = 0b00000000; // xxxx xxxx
        registers[Const.PCLATH]     = 0b00000000; // ---0 0000
        registers[Const.INTCON]     = 0b00000000; // 0000 000x
        registers[Const.OPTION_REG] = 0b11111111; // 1111 1111
        registers[Const.TRISA]      = 0b11111111; // ---1 1111
        registers[Const.TRISB]      = 0b11111111; // 1111 1111
        registers[Const.EECON1]     = 0b00000000; // ---0 x000
        registers[Const.EECON2]     = 0b00000000; // ---- ----

        // Bank 1 mit Startwerten belegen
        registers[Const.PCL + 0x80]    = registers[Const.PCL];
        registers[Const.STATUS + 0x80] = registers[Const.STATUS];
        registers[Const.FSR + 0x80]    = registers[Const.FSR];
        registers[Const.PCLATH + 0x80] = registers[Const.PCLATH];
        registers[Const.INTCON + 0x80] = registers[Const.INTCON];
    }


    // Diese Methode ist für die GUI gedacht, da dort nur absolute Werte aus dem Speicher geholt werden
    // (also unabhängig davon, ob Bank1 oder Bank2 aktiv ist)
    public Integer getAbsoluteAddress(int address){
            return registers[address];
        }

    public void setAddress(int address, int value){
        // Das Statusregister soll auf beiden Bänken gleichen Inhalt haben
        if(address == Const.STATUS || address == Const.STATUS+0x80){
            this.registers[address] = value;
            this.registers[address + 0x80] = value;
        }



        // Für alle anderen Adressen wird geprüft welche Bank beschrieben werden soll
        if( ((registers[Const.STATUS] >> 5) & 1) == 0){
            // Bank 0 ist ausgewählt
            this.registers[address] = value;
        } else {
            // Bank 1 ist ausgewählt
            this.registers[address + 0x80] = value;
        }
    }

    public Integer getAddress(int address){
        if( ((registers[Const.STATUS] >> 5) & 1) == 0){
            // Bank 0 ist ausgewählt
            return registers[address];
        } else {
            // Bank 1 ist ausgewählt
            return registers[address + 0x80];
        }
    }

    public Integer[] getRegisters(){
        return registers;
    }

    public void setRegisters(Integer[] registers){
        this.registers = registers;
    }

    public Integer getRegisterW() {
        return registerW;
    }

    public void setRegisterW(int registerW) {
        this.registerW = registerW;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }
}
