package de.dhbw.Microcontroller.Befehle;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.CPU;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.Stack;

public class Instruction {
    public int instruction; // HEX-Code für Opcode + Argumente
    public int opcode;
    public int argument;
    public int argument1;
    public int argument2;

    protected Memory memory = Memory.getInstance();
    protected Stack stack = Stack.getInstance();

    public Instruction(int instruction, int opcode){
        this.instruction = instruction;
        this.opcode = opcode;
    }

    public Instruction(int instruction, int opcode, int argument){
        this.instruction = instruction;
        this.opcode = opcode;
        this.argument = argument;
    }

    public Instruction(int instruction, int opcode, int argument1, int argument2){
        this.instruction = instruction;
        this.opcode = opcode;
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    public void execute(){
        System.err.println("Dieser Befehl muss noch implementiert werden!");
    }

    public void incrementProgramCounter(){
        // TODO: Richtig behandeln. Unterschied PC, PCL, PLATH?
        memory.setAddress(Const.PCL, (memory.getAddress(Const.PCL) + 1));
    }

    public void displayDebugInfo(){
        System.err.println("Die Debug-Info für diesen Befehl muss noch implementiert werden!");
    }

    public boolean zeroFlagIsSet(){
        int status = memory.getAddress(Const.STATUS);

        if (((status >> 3) & 1) == 1)   return true;
        else    return false;
    }

    public void setZeroFlag()
    {
        int status = memory.getAddress(Const.STATUS);
        status = (status | (1<<2));
        memory.setAddress(Const.STATUS, status);
    }

    public void clearZeroFlag()
    {
        int status = memory.getAddress(Const.STATUS);
        status = (status & ~(1 << 2));
        memory.setAddress(Const.STATUS, status);
    }

    public void setCarryFlag(){
        int status =  memory.getAddress(Const.STATUS);
        status = (status | (1<<0));
        memory.setAddress(Const.STATUS, status);
    }

    public void clearCarryFlag(){
        int status = memory.getAddress(Const.STATUS);
        status = (status & ~(1 << 0));
        memory.setAddress(Const.STATUS, status);
    }

}
