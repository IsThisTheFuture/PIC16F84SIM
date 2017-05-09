package de.dhbw.Microcontroller;

import de.dhbw.Constants.*;
public class Memory {

    private static final Memory memory = new Memory();

    private Byte registerW;

    private Byte[] registers;

    private int tos;


    protected Memory(){
        this.registers = new Byte[255]; // 0xFF
        initializeMemory();
    }

    public static Memory getInstance() {
        return memory;
    }

    public void initializeMemory() {
        registerW = (byte) 0b00000000;

        for (int i = 0; i < registers.length; i++){
            registers[i] = (byte) 0b00000000;
        }

        /*
         *   Legende:
         *   x = unknown, u = unchanged. - = unimplemented, read as '0', q = value depends on condition
         */

                                                         // Start- bzw. Reset-Wert
        registers[Const.TMR0]       = (byte) 0b00000000; // xxxx xxxx
        registers[Const.PCL]        = (byte) 0b00000000; // 0000 0000
        registers[Const.STATUS]     = (byte) 0b00011000; // 0001 1xxx
        registers[Const.FSR]        = (byte) 0b00000000; // xxxx xxxx
        registers[Const.PORTA]      = (byte) 0b00000000; // ---x xxxx
        registers[Const.PORTB]      = (byte) 0b00000000; // xxxx xxxx
        registers[Const.PORTB]      = (byte) 0b00000000; // xxxx xxxx
        registers[Const.EEDATA]     = (byte) 0b00000000; // xxxx xxxx
        registers[Const.EEADR]      = (byte) 0b00000000; // xxxx xxxx
        registers[Const.PCLATH]     = (byte) 0b00000000; // ---0 0000
        registers[Const.INTCON]     = (byte) 0b00000000; // 0000 000x
        registers[Const.OPTION_REG] = (byte) 0b11111111; // 1111 1111
        registers[Const.TRISA]      = (byte) 0b11111111; // ---1 1111
        registers[Const.TRISB]      = (byte) 0b11111111; // 1111 1111
        registers[Const.EECON1]     = (byte) 0b00000000; // ---0 x000
        registers[Const.EECON2]     = (byte) 0b00000000; // ---- ----


        registers[0]                = (byte) 0b11111111;

        //System.out.println(registers[0] & 0xFF);

        /*for (int i = 0; i < registers.length; i++)
        {
            System.out.println("Adresse " + i + ": " + (registers[i] & 0xFF));
        }
        */
    }


    public void setAddress(int address, byte value){
        this.registers[address] = value;
    }

    public byte getAddress(int address){
        return registers[address];
    }

    public Byte[] getRegisters(){
        return registers;
    }

    public void setRegisters(Byte[] registers){
        this.registers = registers;
    }

    public Byte getRegisterW() {
        return registerW;
    }

    public void setRegisterW(Byte registerW) {
        this.registerW = registerW;
    }
}
