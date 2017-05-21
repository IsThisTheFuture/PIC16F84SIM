package de.dhbw.Model;

/*
 * Model für die Anzeige des Assembler-Programmes in der GUI
 */
public class InstructionView {
    private boolean isExecutable;
    private boolean isBreakpoint;
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

    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    public boolean isBreakpoint() {
        return isBreakpoint;
    }

    public void setBreakPoint(boolean breakpoint) {
        isBreakpoint = breakpoint;
    }

}
