package de.dhbw.Microcontroller.Befehle;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.Stack;
import de.dhbw.Services.CheckForInterruptService;
import de.dhbw.Services.InterruptService;
import de.dhbw.Services.Timer0Service;

/**
 * Alle Befehle erben von dieser Klasse und können somit deren Methoden nutzen (sofern diese protected / public sind)
 */
public class Instruction {
    public int instruction; // HEX-Code für Opcode + Argumente
    public int opcode;
    public int argument;
    public int argument1;
    public int argument2;

    protected Memory memory = Memory.getInstance();
    protected Stack stack = Stack.getInstance();

    protected CheckForInterruptService checkForInterruptService = new CheckForInterruptService();
    protected Timer0Service timer0Service = new Timer0Service();

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

    /**
     * Wenn der Befehl diese Methode nicht überschreibt soll eine Fehlermeldung ausgegeben werden
     */
    public void execute(){
        System.err.println("Dieser Befehl muss noch implementiert werden!");
    }

    public void incrementProgramCounter(){
        memory.setPc(memory.getPc() + 1);

        // In PCL stehen nur die unteren 8 Bit von PC
        memory.setAddress(Const.PCL, memory.getPc()&255);
    }

    /**
     * Gibt zusätzliche Information zum Debugging aus
     */
    public void displayDebugInfo(){
        System.err.println("Die Debug-Info für diesen Befehl muss noch implementiert werden!");
    }

    /**
     * Setzt das ZeroFlag im Statusregister
     */
    public void setZeroFlag()
    {
        int status = memory.getAddress(Const.STATUS);
        status = (status | (1<<2));
        memory.setAddress(Const.STATUS, status);
    }

    /**
     * Löscht das ZeroFlag im Statusregister
     */
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

    public void setDigitCarryFlag(){
        int status =  memory.getAddress(Const.STATUS);
        status = (status | (1<<1));
        memory.setAddress(Const.STATUS, status);
    }

    public void clearDigitCarryFlag(){
        int status = memory.getAddress(Const.STATUS);
        status = (status & ~(1 << 1));
        memory.setAddress(Const.STATUS, status);
    }

    // TODO: getAbsoluteAdress() nutzen?
    protected int getBit(int address, int position)
    {
        return ((memory.getAddress(address) >> position) & 1);
    }


    /**
     * Für CheckInterruptService
     */
    public void copyFormerValues(){
        checkForInterruptService.copyFormerState();
    }

    public void copyCurrentValues(){
        checkForInterruptService.copyCurrentState();
    }

    public void compareValues(){
        checkForInterruptService.compareStates();
    }


    public void incrementTimer0(){
        timer0Service.incrementTimer();
    }

    /**
     * Hier wird die Laufzeit gesetzt.
     * One Instruction Cycle consists of 4 Oscillator Periods
     * Thus, for an oscillator frequency of 4 MHz the instruction execution time is 1 microsecond
     */
    public void incrementRuntime(){
        Controller.runtime = Controller.runtime + Controller.runtimeCalculated;
    }
}
