package de.dhbw.Microcontroller.Befehle;

/**
 * Created by tobi on 5/5/17.
 */
public class InstructionView {
    private int zeilennummer;
    private String opcode;
    private String befehl;
    private String comment;


    public InstructionView(){}

    public int getZeilennummer() {
        return zeilennummer;
    }

    public void setZeilennummer(int zeilennummer) {
        this.zeilennummer = zeilennummer;
    }


    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBefehl() {
        return befehl;
    }

    public void setBefehl(String befehl) {
        this.befehl = befehl;
    }
}
