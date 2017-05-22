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
    private Memory memory = Memory.getInstance();

    private static Integer[] memCopy;

    // PortA
    private static int portA4Old = 0;
    private static int portA4New = 0;

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

    //TMR0
    private static int tmr0Old = 0;
    private static int tmr0New = 0;

    /**
     * Kopieren der alten Werte, um sie später hiergegen vergleichen zu können
     */
    @SuppressWarnings("Duplicates")
    public void copyFormerState(){
        //int registerA = memory.getAbsoluteAddress(Const.PORTA);
        //int registerB = memory.getAbsoluteAddress(Const.PORTB);

        memCopy = memory.getRegisters();
        Integer registerA = memCopy[Const.PORTA];
        Integer registerB = memCopy[Const.PORTB];


        tmr0Old = memCopy[Const.TMR0];
        portA4Old = getBit(registerA, 4);
        //System.out.println("PortA4 Alter Wert: " + portA4Old);
    }

    /**
     * Kopieren der aktuellen Werte, um sie gegen die Werte aus copyFormerState zu vergleichen
     */
    @SuppressWarnings("Duplicates")
    public void copyCurrentState(){
        memCopy = memory.getRegisters();

        Integer registerA = memCopy[Const.PORTA];
        Integer registerB = memCopy[Const.PORTB];

        tmr0New = memCopy[Const.TMR0];
        portA4New = getBit(registerA, 4);
        //System.out.println("Port A4 neuer Wert: " + portA4New + "\n");
    }


    /**
     * Die beiden Zustände werden verglichen.
     * Bei unterschieden wird ein entsprechender Interrupt getriggert
     */
    public void compareStates(){
        if (portA4New == 1 && portA4Old == 0)
            System.out.println("Flanke an PIN RA4 erkannt! Bit von 1 auf 0");
        else if (portA4New == 0 && portA4Old == 1)
            System.out.println("Flanke an PIN RA4 erkannt! Bit von 0 auf 1");

        if(tmr0Old == 255 && tmr0New == 0){
            System.out.println("Timer0 overflow!");
            getInterruptService().triggerTimer0Interrupt();
        }
    }

    /**
     * Wenn von Null auf 1 getriggert wird
     * @return true
     * else false
     *
     * Für TMR0 Counter Mode in Klasse Instruction
     */
    public boolean isRA4HighTriggered(){
        portA4New = getBit(memCopy[Const.PORTA], 4);
        if (portA4Old < portA4New)
            return true;
        else
            return false;
    }


    /**
     * INT/RB0 Interrupt wird getriggert, entweder bei steigender oder fallender Flanke
     * steigend, wenn INTEDG in OPTION_REG<6> = 1, fallend wenn INTEDG = 0
     *
     * Wird eine valide Flanke erkannt, dann wird INTCON<1> gesetzt
     *
     * Der Interrupt kann durch setzen des INTE = 0 in INTCON<4> deaktiviert werden
     * Ein RB0 Interrupt kann den Prozessor aus dem SLEEP Modus wecken
     *
     * Wird aufgerufen, wenn via GUI RB0 getoggled wird
     */
    public void checkForRB0Interrupt(){
        if(getBit(memory.getAbsoluteAddress(Const.INTCON), 4) == 1){

            // Wird auf steigende Flanken gehört?
            if(getBit(memory.getAbsoluteAddress(Const.INTCON), 6) == 1){
                // Wenn jetzt in REG_B<0> eine 1 steht gab es eine steigende Flanke
                if (getBit(memory.getAbsoluteAddress(Const.PORTB), 0) == 1);
                getInterruptService().triggerIntInterrupt();
            } else {
                // Es wird auf fallende Flanken geprüft
                if(getBit(memory.getAbsoluteAddress(Const.PORTB), 0) == 0)
                    getInterruptService().triggerIntInterrupt();
            }
        }
            getInterruptService().triggerIntInterrupt();
    }

    /**
     * Wenn RB4-7 getoggled wird, löst ein Interrupt aus (unabhängig der Flanke)
     *
     */
    public void checkForPortBInterrupt(){
        if(getBit(memory.getAbsoluteAddress(Const.PORTB), 3) == 1)
            getInterruptService().triggerPortBInterrupt();
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
