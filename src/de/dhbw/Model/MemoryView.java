package de.dhbw.Model;

/*
 *  Model für die Anzeige des Speichers in der GUI
 */
public class MemoryView {
    private String memoryRow;
    private String column0;
    private String column1;
    private String column2;
    private String column3;
    private String column4;
    private String column5;
    private String column6;
    private String column7;


    public String getColumn0() {
        return column0;
    }

    public void setColumn0(Integer column0) {
        this.column0 = String.format("%02x", column0).toUpperCase();
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(Integer column1) {
        this.column1 = String.format("%02x", column1).toUpperCase();
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(Integer column2) {
        this.column2 = String.format("%02x", column2).toUpperCase();
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(Integer column3) {
        this.column3 = String.format("%02x", column3).toUpperCase();
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(Integer column4) {
        this.column4 = String.format("%02x", column4).toUpperCase();
    }

    public String getColumn5() {
        return column5;
    }

    public void setColumn5(Integer column5) {
        this.column5 = String.format("%02x", column5).toUpperCase();
    }

    public String getColumn6() {
        return column6;
    }

    public void setColumn6(Integer column6) {
        this.column6 = String.format("%02x", column6).toUpperCase();
    }

    public String getColumn7() {
        return column7;
    }

    public void setColumn7(Integer column7) {
        this.column7 = String.format("%02x", column7).toUpperCase();
    }

    public String getMemoryRow() {
        return memoryRow;
    }


    public void setMemoryRow (Integer memoryRow) {
        //this.memoryRow = Integer.toHexString(memoryRow);
        this.memoryRow = String.format("%02x", memoryRow).toUpperCase();
    }




}
