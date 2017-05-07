package de.dhbw.Microcontroller.Befehle;

import de.dhbw.Microcontroller.CPU;
import de.dhbw.Microcontroller.Memory;

public class Instruction {
    public int instruction; // HEX-Code für Opcode + Argumente
    public int opcode;
    public int argument;
    public int argument1;
    public int argument2;

    protected CPU cpu = CPU.getInstance();
    protected Memory memory = Memory.getInstance();
    //protected Memory memory = new Memory();

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

    public void displayDebugInfo(){
        System.err.println("Die Debug-Info für diesen Befehl muss noch implementiert werden!");
    }

}
