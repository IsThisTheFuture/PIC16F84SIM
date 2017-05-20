package de.dhbw.Services;


import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Memory;

/**
 * Es wird geprüft, ob die Bedingung für einen Interrupt erfüllt sind
 * Dazu werden vor der Befehlsausführung relevante Register (RegA, RegB, TMR0, ...) gespeichert
 * und nach der Befehlsausführung verglichen.
 */
public class CheckForInterruptService {

    // Instanz des Speichers
    // TODO: Prüfen, welche Auswirkungen das static hier hat....
    private Memory memory = Memory.getInstance();

    private static Memory memCopy;

    // PortA
    private int portA4Old = 0;
    private int portA4New = 0;

    //PortB
    private static int portB4Old = 0;
    private static int portB5Old = 0;
    private static int portB6Old = 0;
    private static int portB7Old = 0;
    private static int portB4New = 0;
    private static int portB5New = 0;
    private static int portB6New = 0;
    private static int portB7New = 0;


    //RB0
    private static int rb0intOld = 0;
    private static int rb0intNew = 0;

    /**
     * Kopieren der alten Werte, um sie später hiergegen vergleichen zu können
     */
    public void copyFormerState(){
        int registerA = memory.getAbsoluteAddress(Const.PORTA);
        int registerB = memory.getAbsoluteAddress(Const.PORTB);

        portA4Old = getBit(registerA, 4);
    }

    /**
     * Kopieren der aktuellen Werte, um sie gegen die Werte aus copyFormerState zu vergleichen
     */
    public void copyCurrentState(){
        Integer registerA = memory.getAbsoluteAddress(Const.PORTA);
        Integer registerB = memory.getAbsoluteAddress(Const.PORTB);

        portA4New = getBit(registerA, 4);
    }


    /**
     * Die beiden Zustände werden verglichen.
     * Bei unterschieden wird ein entsprechender Interrupt getriggert
     */
    public void compareStates(){
        if (this.portA4New > portA4Old) //
            System.out.println("Flanke an PIN RA4 erkannt!");
    }

    /**
     * Es wird der InterruptService benötigt, um Interrupts auszulösen
     * @return InterruptService
     */
    public static InterruptService getInterruptService() {
        return new InterruptService();
    }

    private static int getBit(int byteValue, int position)
    {
        return ((byteValue >> position) & 1);
    }
}
