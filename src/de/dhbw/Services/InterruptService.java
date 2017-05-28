package de.dhbw.Services;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.Stack;

/**
 * Löst Interrupts aus
 *
 * Das Global Interrupt Enable Bit GIE in INTCON<7> aktiviert (falls gesetzt) alle unmaskierten Interrupts
 * oder deaktiviert (falls nicht gesetzt) alle Interrupts
 *
 * Individuelle Interrupts können mit den entsprechenden Bits in INTCON de-/aktiviert werden
 * GIE wird bei RESET auf 0 gesetzt
 */
public class InterruptService {
    private Memory memory = Memory.getInstance();
    private Stack stack = Stack.getInstance();


    /**
     * Ein Overflow des TMR0 Registers triggered das Setzen des Flags INTCON<2>
     *
     * Der Interrupt kann enabled/disabled werden durch setzen/löschen des Bits T0IE in INTCON<5>
     */
    public void triggerTimer0Interrupt() {
        if (globalInterruptisEnabled() && getBit(Const.INTCON, 5) == 1) {
            setBit(Const.INTCON, 2);

            disableGlobalInterruptEnableBit();
            stack.push(memory.getPc() + 1);
            memory.setPc(0x0004);
        }
    }
    /**
     * Der Flankensensitive Pin RB0/INT kann auf 2 Arten getriggert werden. Entweder:
     * Low-High: INTEDG = 1 in OPTION_REG, oder
     * High-Low: INTEDG = 0 in OPTION_REG
     *
     * Wird die eingestellte Flanke bemerkt, wird das INTF<1> Bit in INTCON auf 1 gesetzt
     *
     * Der Interrupt kann disabled werden durch setzen von INTE = 0 in INTCON<4>
     * Der INT Interrupt kann den Prozessor aus dem SLEEP wecken
     */
    public void triggerIntInterrupt(){
        if(globalInterruptisEnabled()) {

            setBit(Const.INTCON, 1);
            disableGlobalInterruptEnableBit();
            stack.push(memory.getPc() + 1);
            memory.setPc(0x0004);
            memory.setSleepMode(false);
        } else if(!globalInterruptisEnabled())
        {
            memory.setSleepMode(false);
        }

    }


    /**
     * Ein Flankenwechsel auf den Ports B<7:4> setzt das Bit RBIF in INTCON<3>
     *
     */
    public void triggerPortBInterrupt(){
        if(globalInterruptisEnabled()){

            setBit(Const.INTCON, 0);
            disableGlobalInterruptEnableBit();
            stack.push(memory.getPc() + 1);
            memory.setPc(0x0004);

            if(memory.isSleepMode())
                memory.setSleepMode(false);
        }
    }

    /**
     * Prüft, ob das GIE (Global Interrupt Enable) Bit gesetzt ist
     */
    public boolean globalInterruptisEnabled(){
        if(getBit(Const.INTCON, 7) == 1)
            return true;
        else
            return false;

    }


    /**
     * Wird ein Interrupt ausgelöst, wird das GIE auf 0 gesetzt, um weitere Interrupts zu verhindern
     * Das GIE Bit wird von RETFIE wieder gesetzt
     */
    private void disableGlobalInterruptEnableBit(){
        int byteValue = memory.getAbsoluteAddress(Const.INTCON);
        byteValue = (byteValue & ~(1 << (7)));
        memory.setAbsoluteAddress(Const.INTCON, byteValue);
    }

    /**
     * Gibt den Wert des Bits mit der Position 'position' an der Adresse 'address' zurück
     */
    private int getBit(int address, int position)
    {
        return ((memory.getAbsoluteAddress(address) >> position) & 1);
    }

    /**
     * Setzt die das Bit 'bitPosition' and Speicheradresse 'address' auf den Wert 1
     */
    private void setBit(int address, int bitPosition){
        int byteValue = memory.getAbsoluteAddress(address);
        byteValue = (byteValue | (1 << (bitPosition)));
        memory.setAbsoluteAddress(address, byteValue);
    }



}
