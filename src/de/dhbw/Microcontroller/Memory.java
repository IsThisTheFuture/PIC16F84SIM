package de.dhbw.Microcontroller;

import de.dhbw.Constants.*;
import de.dhbw.Services.InterruptService;

/**
 * Hier wird der Speicher angelegt und mit Startwerten belegt
 * Das Arbeitsregister, der ProgramCounter und der WatchdogTimer werden auch hier angelegt,
 * sie liegen jedoch nicht im Arbeitsspeicher des PICs
 */
public class Memory {

    private static final Memory memory = new Memory();

    private Integer registerW;
    private Integer pc;
    private Integer[] registers;

    private Integer watchDogTimer;
    private boolean sleepMode;
    private boolean watchDogTimerEnabled;


    protected Memory(){
        this.registers = new Integer[256];
        initializeMemory();
    }

    public static Memory getInstance() {
        return memory;
    }

    /**
     * Belegt den Speicher mit Startwerten
     */
    public void initializeMemory() {
        // W ist 8 Bit, pc ist 13 Bit
        registerW =  0b00000000;
        pc        =  0b0000000000000;

        watchDogTimer = 0;
        watchDogTimerEnabled = false;
        sleepMode = false;

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


    /**
     * Diese Methode wird innerhalb des Programms benutzt, da dort nur absolute Adressen verwendet werden
     * (also unabhängig davon, ob Bank1 oder Bank2 aktiv ist)
     */
    public Integer getAbsoluteAddress(int address){
        return registers[address];
    }

    /**
     * Diese Methode wird innerhalb des Programms benutzt, da dort nur absolute Adressen verwendet werden
     * (also unabhängig davon, ob Bank1 oder Bank2 aktiv ist)
     */
    public void setAbsoluteAddress(int address, int value){
        this.registers[address] = value;
    }


    /**
     * Wird von den Befehlen verwendet (z. B. MOVF, etc.)
     * Es erfolgt eine Prüfung, auf welcher Bank wir uns befinden
     */
    public void setAddress(int address, int value){
        // Das Statusregister soll auf beiden Bänken gleichen Inhalt haben
        if(address == Const.STATUS || address == Const.STATUS+0x80){
            this.registers[address] = value;
            this.registers[address + 0x80] = value;
        }

        if( ((registers[Const.STATUS] >> 5) & 1) == 0){
            // Bank 0 ist ausgewählt
            this.registers[address] = value;
        } else {
            // Bank 1 ist ausgewählt
            this.registers[address + 0x80] = value;
        }
    }

    /**
     * Wird von den Befehlen verwendet
     * Es erfolgt eine Prüfung, auf welcher Bank wir uns befinden
     * @param address
     * @return den Inhalt der Adresse
     */
    public Integer getAddress(int address){
        if( ((registers[Const.STATUS] >> 5) & 1) == 0){
            // Bank 0 ist ausgewählt
            return registers[address];
        } else {
            // Bank 1 ist ausgewählt
            return registers[address + 0x80];
        }
    }

    /**
     * Gibt den vollständigen Inhalt des Speichers zurück
     */
    public Integer[] getRegisters(){
        return registers;
    }

    public void setRegisters(Integer[] registers){
        this.registers = registers;
    }

    /**
     * Gibt den Inhalt des Arbeitsregisters zurücl
     * @return Register W
     */
    public Integer getRegisterW() {
        return registerW;
    }

    /**
     * Setzt den Inhalt des Arbeitsregisters
     * @param registerW
     */
    public void setRegisterW(int registerW) {
        this.registerW = registerW;
    }

    /**
     * Liefert den 13-Bit ProgramCounter zurück
     * @return pc
     */
    public Integer getPc() {
        return pc;
    }

    /**
     * Setzt den 13-Bit ProgramCounter
     * @param pc
     */
    public void setPc(Integer pc) {
        this.pc = pc;
    }

    /**
     * Liefert den Wert des Watchdogs
     * @return watchDogTimer
     */
    public Integer getWatchDogTimer() {
        return watchDogTimer;
    }

    /**
     * Setzt den Wert des Watchdogs
     * @param watchDogTimer
     */
    public void setWatchDogTimer(Integer watchDogTimer) {
        this.watchDogTimer = watchDogTimer;
    }

    /**
     * Gibt zurück, ob sich der WDT im SleepMode befindet
     * @return
     */
    public boolean isSleepMode() {
        return sleepMode;
    }

    /**
     * Aktiviert / deaktiviert den SleepMode
     * @param sleepMode
     */
    public void setSleepMode(boolean sleepMode) {
        this.sleepMode = sleepMode;
    }

    /**
     * Gibt zurück, ob der WDT aktiv ist
     * @return
     */
    public boolean isWatchDogTimerEnabled() {
        return watchDogTimerEnabled;
    }

    /**
     * Aktiviert / deaktiviert den WatchDogTimer
     * @param watchDogTimerEnabled
     */
    public void setWatchDogTimerEnabled(boolean watchDogTimerEnabled) {
        this.watchDogTimerEnabled = watchDogTimerEnabled;
    }
}
