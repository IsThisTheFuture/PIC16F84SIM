package de.dhbw.Microcontroller;

// Jedes General-Purpose-Register ist 8 Bit breit
// GPR Adressen in Bank 1 sind auf GPR-Adressen in Bank 0 gemappt
//Siehe Seite 9 Tabelle 2-1

public class Register {//extends CPU{
   /*
    *   Legend:
    *   x = unknown, u = unchanged. - = unimplemented, read as '0', q = value depends on condition
    */


    // Arbeitsregister
    public Byte w       = (byte) 0b00000000;

    // ProgramCounter
    public Byte pc      = (byte) 0b00000000;

    // TODO: Wo den Stack anlegen?


//Bank 0:
    // Der Datentyp byte ist Vorzeichenbehaftet. Um das zu Umgehen kann man "byte b = (byte) 0b11111111;" schreiben

    //          Register                                  Start- bzw. Reset-Wert
    public Byte INDF    = (byte) 0b00000000;           // ---- ----
    public Byte TMR0    = (byte) 0b00000000;           // xxxx xxxx
    public Byte PCL     = (byte) 0b00000000;           // 0000 0000
    public Byte STATUS  = (byte) 0b00011000;           // 0001 1xxx
    public Byte FSR     = (byte) 0b00000000;           // xxxx xxxx
    public Byte PORTA   = (byte) 0b00000000;           // ---x xxxx
    public Byte PORTB   = (byte) 0b00000000;           // xxxx xxxx
    public Byte EEDATA  = (byte) 0b00000000;           // xxxx xxxx
    public Byte EEADR   = (byte) 0b00000000;           // xxxx xxxx
    public Byte PCLATH  = (byte) 0b00000000;           // ---0 0000
    public Byte INTCON  = (byte) 0b00000000;           // 0000 000x


    // Bank 1:
    //public Byte INDF      =                          // ---- ----
    public byte OPTION_REG  = (byte) 0b11111111;       // 1111 1111
    //public Byte PCL       =
    // public Byte STATUS   =
    // public Byte FSR      =
    public Byte TRISA       = (byte) 0b11111111;       // ---1 1111
    public Byte TRISB       = (byte) 0b11111111;       // 1111 1111
    public Byte EECON1      = (byte) 0b00000000;       // ---0 x000
    public Byte EECON2      = (byte) 0b00000000;       // ---- ----
    // public Byte PCLATH
    // public Byte INTCON
}
