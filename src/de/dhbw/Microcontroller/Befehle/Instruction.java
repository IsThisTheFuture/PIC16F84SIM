package de.dhbw.Microcontroller.Befehle;

import de.dhbw.Constants.Const;
import de.dhbw.Controller;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.Stack;

public class Instruction {
    public int instruction; // HEX-Code für Opcode + Argumente
    public int opcode;
    public int argument;
    public int argument1;
    public int argument2;

    public boolean isBreakPoint;
    public static int tmrCounter;

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
        memory.setPc(memory.getPc() + 1);

        // In PCL stehen nur die unteren 8 Bit von PC
        memory.setAddress(Const.PCL, memory.getPc()&255);
    }

    public void displayDebugInfo(){
        System.err.println("Die Debug-Info für diesen Befehl muss noch implementiert werden!");
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
     * Hier wird die Laufzeit und das TMR0 Register gesetzt.
     * Ist der Timer im Sync-Modus (dann, wenn das Register z. B. mit movwf 1 beschrieben wurde),
     * ist der Timer für die nachfolgenden 2 Takte gesperrt.
     *
     * Ist der Timer nicht im Sync, wird tmrCounter erhöht. Erreicht tmrCounter einen durch das Vorteilerverhältnis
     * teilbaren Wert, erhöht sich das TMR0 Register
     *
     * Wird nach jedem Befehl ausgeführt
     */
    public void incrementRuntime(){
        int optionReg = memory.getAbsoluteAddress(Const.OPTION_REG);
        int t0cs = ((optionReg >> 5) & 1);  // Clock Source Select. 0 = Timer Mode,  1 = Counter Mode

        if(Controller.inhibitTimer0 <= 0 && t0cs == 0) // TmrCounter wird nur erhöht wenn TimerMode enabled
            this.tmrCounter++;


        System.out.println("inhibitTimer: " + Controller.inhibitTimer0 + "tmrCounter: " + tmrCounter);

        //if(t0cs == 0 && Controller.inhibitTimer0 <= 0){            // Clock Source ist die interne Frequenz
        if(t0cs == 0){            // Clock Source ist die interne Frequenz
            if(((optionReg >> 4) & 1) == 0 && Controller.inhibitTimer0 <= 0){
                /*
                 * Das PSA (Prescaler Assignment) Bit muss auf 0 sein,
                 * damit der Prescaler dem TMR0 zugeteilt wird
                 */
                int ps0 = ((optionReg >> 0) & 1);
                int ps1 = ((optionReg >> 1) & 1);
                int ps2 = ((optionReg >> 2) & 1);
                int prescalerValue = (ps2*4 + ps1*2 + ps0) ;
                int VorteilerVerh = 2;
                if (prescalerValue == 0b000) VorteilerVerh = 2;
                else if (prescalerValue == 0b001) VorteilerVerh = 4;
                else if (prescalerValue == 0b010) VorteilerVerh = 8;
                else if (prescalerValue == 0b011) VorteilerVerh = 16;
                else if (prescalerValue == 0b100) VorteilerVerh = 32;
                else if (prescalerValue == 0b101) VorteilerVerh = 64;
                else if (prescalerValue == 0b110) VorteilerVerh = 128;
                else if (prescalerValue == 0b111) VorteilerVerh = 256;

                /*
                 * Abhängig vom Vorteilerverhältnis wird bei jedem xten Befehl das TMR0 Register erhöht
                 * Für PS2 = 0, PS1 = 0, PS0 = 1 wäre das z. B. jeder 4. Befehl
                 */
                if(tmrCounter%VorteilerVerh==0){
                    memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                    tmrCounter = 0;
                }
            } else {
                /*
                 * Ohne Prescaler wird nach jedem Taktzyklus der TMR0 erhöht
                 */
                memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
            }

        }

        // Falls der Timer gesperrt ist
        Controller.inhibitTimer0--;
        Controller.runtime++;

        /*
         One Instruction Cycle consists of 4 Oscillator Periods
         Thus, for an oscillator frequency of 4 MHz the instruction execution time is 1 microsecond
         */
        Controller.runtimeCalculated = Controller.runtimeCalculated + (4000 / Controller.clockSpeed);
    }
}
