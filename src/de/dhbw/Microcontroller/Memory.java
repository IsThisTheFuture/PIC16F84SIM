package de.dhbw.Microcontroller;

import de.dhbw.Constants.*;
public class Memory {

    private static final Memory memory = new Memory();

    private Byte registerW;

    private Byte[] registers;
    private Byte[] stack;


    protected Memory(){
        this.registers = new Byte[255]; // 0xFF
        this.stack = new Byte[8];
        initializeMemory();
    }

    public static Memory getInstance() {
        return memory;
    }




    public void initializeMemory() {
        registerW = (byte) 0b00000000;

        registers[Const.TMR0]     = (byte) 0b00000000;
        registers[Const.PCL]      = (byte) 0b00000000;
        registers[Const.STATUS]   = (byte) 0b00011000;
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

    public Byte[] getStack() {
        return stack;
    }

    public void setStack(Byte[] stack) {
        this.stack = stack;
    }

    public Byte getRegisterW() {
        return registerW;
    }

    public void setRegisterW(Byte registerW) {
        this.registerW = registerW;
    }
}
