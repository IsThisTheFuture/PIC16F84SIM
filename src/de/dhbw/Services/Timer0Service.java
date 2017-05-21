package de.dhbw.Services;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Memory;

/**
 * Behandelt den TMR0
 *
 */
public class Timer0Service {
    private Memory memory = Memory.getInstance();

    private int optionReg;
    private int optionRegPs0;
    private int optionRegPs1;
    private int optionRegPs2;
    private int optionRegPsa;
    private int optionRegT0se;
    private int optionRegT0cs;
    private int optionRegIntEdg;
    private int optionRegRbpu;

    private int prescalerValue;
    private int vorteilerVerhaeltnis;

    private static int cycleCounter = 0;
    private static int counterModeNumberOfFallingFlanks = 0;
    public static int counterModeNumberOfRisingFlanks = 0;
    private boolean isInSyncMode;


    /**
     * Diese Methode wird am Ende jedes Befehls aufgerufen
     *
     * Abhängig vom Vorteilerverhältnis und ob dieser überhaupt aktiviert ist, wird der Timer erhöht
     * Ist kein Vorteiler aktiv, so wird nach jedem Befehl der Timer erhöht.
     * Bei Verhältnis 2:1 z. B. wird nach jedem 2. Befehl der Timer erhöht.
     */
    public void incrementTimer(){
        cycleCounter++;

        // Es wird nur der TimerMode behandelt
        if(isInTimerMode()){
            if(prescalerIsActive()){
                if(cycleCounter == getVorteilerVerhaeltnis()){
                    memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                    cycleCounter = 0;
                }
            } else {
                // Prescaler ist nicht aktiv. Nach jedem Takt den TMR erhöhen
                memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                cycleCounter = 0;
            }
        }
    }

    /**
     * Diese Methode verwaltet den CounterModus des TMR0
     *
     * Dabei ist die Taktquelle nun der Pin RA4 (Flankenerkennung: entweder Low-High oder High-Low)
     *
     * Abhängig vom Vorteilerverhältnis und ob dieser überhaupt aktiviert ist, wird der Counter erhöht
     * Ist kein Vorteiler aktiv, so wird nach jedem Befehl der Counter erhöht.
     * Bei Verhältnis 2:1 z. B. wird nach jedem 2. Befehl der Counter erhöht.
     *
     * Fallende Flanke = 0, Steigende Flanke = 1
     */
    public void incrementCounter(boolean flank){
        // Wenn nicht im TimerMode, dann muss TMR0 im CounterMode sein
        //TODO: Programm 7 zählt sowohl fallende als auch steigende Flanken!!
        if(!isInTimerMode()){

            if(flank == false) counterModeNumberOfFallingFlanks++;
            else counterModeNumberOfRisingFlanks++;

            if(prescalerIsActive()){
                if(isListeningForRisingFlanks()  && counterModeNumberOfRisingFlanks > 0){
                    if(counterModeNumberOfRisingFlanks == getVorteilerVerhaeltnis()){
                        memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                        counterModeNumberOfRisingFlanks = 0;
                    }
                } else if(isListeningForFallingFlanks()  && counterModeNumberOfFallingFlanks > 0) {
                    if(counterModeNumberOfFallingFlanks == getVorteilerVerhaeltnis()) {
                        memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                        counterModeNumberOfFallingFlanks = 0;
                    }
                }
            } else if (!prescalerIsActive()) {
                if(isListeningForRisingFlanks() && counterModeNumberOfRisingFlanks > 0){
                    memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                    counterModeNumberOfRisingFlanks = 0;
                    System.out.println("Rising Flank");
                } else if(isListeningForFallingFlanks() && counterModeNumberOfFallingFlanks > 0) {
                    memory.setAbsoluteAddress(Const.TMR0, (memory.getAbsoluteAddress(Const.TMR0) + 1) & 255);
                    counterModeNumberOfFallingFlanks = 0;
                    System.out.println("Falling Flank");

                }
            }
        }
    }



    /**
     * Liefert auf Basis des PrescalerValue das entsprechende Vorteilervrehältnis zurück
     */
    @SuppressWarnings("Duplicates")
    private int getVorteilerVerhaeltnis(){
        optionRegPs0 = getBit(Const.OPTION_REG, 0);
        optionRegPs1 = getBit(Const.OPTION_REG, 1);
        optionRegPs2 = getBit(Const.OPTION_REG, 2);
        int prescalerValue = (optionRegPs2*4 + optionRegPs1*2 + optionRegPs0);

        if (prescalerValue == 0b000)
            vorteilerVerhaeltnis = 2;
        else if (prescalerValue == 0b001) vorteilerVerhaeltnis = 4;
        else if (prescalerValue == 0b010) vorteilerVerhaeltnis = 8;
        else if (prescalerValue == 0b011) vorteilerVerhaeltnis = 16;
        else if (prescalerValue == 0b100) vorteilerVerhaeltnis = 32;
        else if (prescalerValue == 0b101) vorteilerVerhaeltnis = 64;
        else if (prescalerValue == 0b110) vorteilerVerhaeltnis = 128;
        else if (prescalerValue == 0b111) vorteilerVerhaeltnis = 256;

        return vorteilerVerhaeltnis;
    }


    /**
     * Gibt den Wert des Bits mit der Position 'position' an der Adresse 'address' zurück
     */
    private int getBit(int address, int position){
        return ((memory.getAbsoluteAddress(address) >> position) & 1);
    }


    /**
     * Liefert true zurück, wenn der Timer den PIC-internen Takt benutzt
     * Ist das T0CS Bit = 1, dann wird der CounterMode benutzt
     */
    private boolean isInTimerMode(){
        // Wenn 0, dann ist die ClockSource der PIC-interne Takt
        if (getBit(Const.OPTION_REG, 5) == 0)
            return true;
        else
            return false;
    }

    /**
    * Liefert true zurück, wenn der Prescaler dem Timer zugeordnet ist
    * Das PSA (Prescaler Assignment) Bit muss auf 0 sein,
    * damit der Prescaler dem TMR0 zugeteilt wird
    */
    private boolean prescalerIsActive(){
        if (getBit(Const.OPTION_REG, 3) == 0)
            return true;
        else
            return false;
    }



    private boolean isInSyncMode(){
        if(isInSyncMode)
            return true;
        else
            return false;
    }

    private boolean isListeningForRisingFlanks(){
        if(getBit(Const.OPTION_REG, 4) == 0)
            return true;
        else
            return false;
    }

    private boolean isListeningForFallingFlanks(){
        if(getBit(Const.OPTION_REG, 4) == 1)
            return true;
        else
            return false;
    }



}
