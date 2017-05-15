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


        //registers[0]                = (byte) 0b11111111;

        //System.out.println(registers[0] & 0xFF);

        /*for (int i = 0; i < registers.length; i++)
        {
            System.out.println("Adresse " + i + ": " + (registers[i] & 0xFF));
        }
        */
    }


    public void setAddress(int address, int value){
        this.registers[address] = value;
    }

    public Integer getAddress(int address){
        return registers[address];
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
