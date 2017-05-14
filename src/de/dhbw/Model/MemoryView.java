package de.dhbw.Model;

/*
 *  Model für die Anzeige des Speichers in der GUI
 */
public class MemoryView {
    private String memoryRow;
    private int column0;
    private int column1;
    private int column2;
    private int column3;
    private int column4;
    private int column5;
    private int column6;
    private int column7;


    public int getColumn0() {
        return column0;
    }

    public void setColumn0(int column0) {
        this.column0 = column0;
    }

    public int getColumn1() {
        return column1;
    }

    public void setColumn1(int column1) {
        this.column1 = column1;
    }

    public int getColumn2() {
        return column2;
    }

    public void setColumn2(int column2) {
        this.column2 = column2;
    }

    public int getColumn3() {
        return column3;
    }

    public void setColumn3(int column3) {
        this.column3 = column3;
    }

    public int getColumn4() {
        return column4;
    }

    public void setColumn4(int column4) {
        this.column4 = column4;
    }

    public int getColumn5() {
        return column5;
    }

    public void setColumn5(int column5) {
        this.column5 = column5;
    }

    public int getColumn6() {
        return column6;
    }

    public void setColumn6(int column6) {
        this.column6 = column6;
    }

    public int getColumn7() {
        return column7;
    }

    public void setColumn7(int column7) {
        this.column7 = column7;
    }

    public String getMemoryRow() {
        return memoryRow;
    }


    public void setMemoryRow (Integer memoryRow) {
        //this.memoryRow = Integer.toHexString(memoryRow);
        this.memoryRow = String.format("%02x", memoryRow).toUpperCase();
    }


}
