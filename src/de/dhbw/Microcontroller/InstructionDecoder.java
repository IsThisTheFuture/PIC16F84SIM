package de.dhbw.Microcontroller;

import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Microcontroller.Befehle.PIC.*;

import java.util.ArrayList;
import java.util.List;

public class InstructionDecoder {
    private int argument1 = 0;
    private int argument2 = 0;

    private List<Instruction> instructionList = new ArrayList<>();

    public List<Instruction> getInstructionList(){
        return instructionList;
    }

    /*
     * Eine Liste von Instruktionen wird beim dekodieren angelegt.
     * Alle Felder des Arrays instructionList (in dem die Befehle bisher als 16-Bit Opcode gespeichert sind)
     * werden einzeln dekodiert. Erkannte Opcodes werden als "Instruction"-Objekt zur Liste hinzugefügt.
     */

    public List<Instruction> decode(Integer[] opcodeList){
        for (int opcode : opcodeList)
        {
            if (decodeInstruction(opcode) != null)
            {
                instructionList.add(decodeInstruction(opcode));
            }
        }
        return instructionList;
    }



    private Instruction decodeInstruction(int instruction){
            //ADDLW (0011 111x kkkk kkkk)
            if ((instruction & 0x3E00) == 0x3E00) {
                // k:
                argument1 = instruction & 0x00FF;

                //System.out.println(String.format("%04X", instruction) + ": ADDLW" + "  k: " + argument1);
                return new ADDLW(instruction,0x3E00, argument1);
            }


            //SUBLW (0011 1100 kkkk kkkk)
            else if ((instruction & 0x3C00) == 0x3C00) {
                // k:
                argument1 = instruction & 0x00FF;

                return new SUBLW(instruction, 0x3C00, argument1);
            }


            //XORLW (0011 1010 kkkk kkkk)
            else if ((instruction & 0x3A00) == 0x3A00) {
                // k:
                argument1 = instruction & 0x00FF;

                return new XORLW(instruction, 0x3A00, argument1);
            }


            //ANDLW (0011 1001 kkkk kkkk)
            else if ((instruction & 0x3900) == 0x3900) {
                // k:
                argument1 = instruction & 0x00FF;

                //System.out.println(String.format("%04X", instruction) + ": ANDLW" + "  k: " + argument1);
                return new ANDLW(instruction, 0x3900, argument1);
            }


            //IORLW (0011 1000 kkkk kkkk)
            else if ((instruction & 0x3800) == 0x3800) {
                // k:
                argument1 = instruction & 0x00FF;

                return new IORLW(instruction, 0x3800, argument1);
            }


            //RETLW (0011 01xx kkkk kkkk)
            else if ((instruction & 0x3400) == 0x3400) {
                // k:
                argument1 = instruction & 0x00FF;
                return new RETLW(instruction, 0x3400, argument1);
            }


            //MOVLW (0011 00xx kkkk kkkk)
            else if ((instruction & 0x3000) == 0x3000) {
                // k:
                argument1 = instruction & 0x00FF;

                return new MOVLW(instruction, 0x3000, argument1);
            }


            //GOTO (0010 1kkk kkkk kkkk)
            else if ((instruction & 0x2800) == 0x2800) {
                // k:
                argument1 = instruction & 0x07FF;

                //System.out.println(String.format("%04X", instruction) + ": GOTO" + "  k: " + argument1);
                return new GOTO(instruction, 0x2800, argument1);
            }


            //CALL (0010 0kkk kkkk kkkk)
            else if ((instruction & 0x2000) == 0x2000) {
                // k:
                argument1 = instruction & 0x07FF;
                return new CALL(instruction, 0x2000, argument1);
            }


            //BTFSS (0001 11bb bfff ffff)
            else if ((instruction & 0x1C00) == 0x1C00) {
                // b:
                argument1 = 0x0380 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": BTFSS" + "  b: " + argument1 + "," + " f: " + argument2);
            }


            //BTSFC (0001 10bb bfff ffff)
            else if ((instruction & 0x1800) == 0x1800) {
                // b:
                argument1 = 0x0380 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": BTFSC" + "  b: " + argument1 + "," + " f: " + argument2);
            }


            //BSF (0001 01bb bfff ffff)
            else if ((instruction & 0x1400) == 0x1400) {
                // b:
                argument1 = 0x0380 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": BSF" + "  b: " + argument1 + "," + " f: " + argument2);
            }


            //BCF (0001 00bb bfff ffff)
            else if ((instruction & 0x1000) == 0x1000) {
                // b:
                argument1 = 0x0380 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                return new BCF(instruction, 0x1000, argument1, argument2);
            }


            //INCFSZ (0001 10bb bfff ffff)
            else if ((instruction & 0x0F00) == 0x0F00) {
                // b:
                argument1 = 0x0380 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": INCFSZ" + "  b: " + argument1 + "," + " f: " + argument2);
            }


            //SWAPF (0000 1110 dfff ffff)
            else if ((instruction & 0x0E00) == 0x0E00) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": SWAPF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //RLF (0000 1101 dfff ffff)
            else if ((instruction & 0x0D00) == 0x0D00) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": RLF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //RRF (0000 1100 dfff ffff)
            else if ((instruction & 0x0C00) == 0x0C00) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": RRF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //DECFSZ (0000 1011 dfff ffff)
            else if ((instruction & 0x0B00) == 0x0B00) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": DECFSZ" + "  d: " + argument1 + "," + " f: " + argument2);
            }


            //INCF (0000 1010 dfff ffff)
            else if ((instruction & 0x0A00) == 0x0A00) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": INCF" + "  d: " + argument1 + "," + " f: " + argument2);
            }


            //COMF (0000 1001 dfff ffff)
            else if ((instruction & 0x0900) == 0x0900) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": COMF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //MOVF (0000 1000 dfff ffff)
            else if ((instruction & 0x0800) == 0x0800) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": MOVF" + "  d: " + argument1 + "," + " f: " + argument2);
            }


            //ADDWF (0000 0111 dfff ffff)
            else if ((instruction & 0x0700) == 0x0700) {

                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": ADDWF" + "  d: " + argument1 + "," + "  f: " + argument2);
                return new ADDWF(instruction, 0x0700, argument1, argument2);
            }


            //XORWF ( 0000 0110 dfff ffff)
            else if ((instruction & 0x0600) == 0x0600) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": XORWF" + "  d:" + argument1 + "," + " f:" + argument2);
            }


            //ANDWF (0000 0101 dfff ffff)
            else if ((instruction & 0x0500) == 0x0500) {
                // d:
                argument1 = 0x0080 & instruction;

                //f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": ANDWF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //IORWF (0000 0100 dfff ffff)
            else if ((instruction & 0x0400) == 0x0400) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": IORWF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //DECF (0000 0011 dfff ffff)
            else if ((instruction & 0x0300) == 0x0300) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": DECF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //SUBWF (0000 0010 dfff ffff)
            else if ((instruction & 0x0200) == 0x0200) {
                // d:
                argument1 = 0x0080 & instruction;

                // f:
                argument2 = 0x007F & instruction;

                System.out.println(String.format("%04X", instruction) + ": SUBWF" + "  d: " + argument1 + "," + "  f: " + argument2);
            }


            //CLRF (0000 0001 1fff ffff)
            else if ((instruction & 0x0180) == 0x0180) {
                // f:
                argument1 = 0x007F & instruction;
                return new CLRF(instruction, 0x0180, argument1);
            }


            //CLRW (0000 0001 0xxx xxxx)
            else if ((instruction & 0x0100) == 0x0100) {
                System.out.println(String.format("%04X", instruction) + ": CLRW");
            }


            //MOVWF (0000 0000 1fff ffff)
            else if ((instruction & 0x0080) == 0x0080) {
                // f:
                argument1 = 0x007F & instruction;

                return new MOVWF(instruction, 0x0080, argument1);
            }


            //CLRWDT (0000 0000 0110 0100)
            else if ((instruction & 0x0064) == 0x0064) {
                System.out.println(String.format("%04X", instruction) + ": CLRWDT");
            }


            //SLEEP (0000 0000 0110 0011)
            else if ((instruction & 0x0063) == 0x0063) {
                System.out.println(String.format("%04X", instruction) + ": SLEEP");
            }


            //RETFIE (0000 0000 0000 1001)
            else if ((instruction & 0x0009) == 0x0009) {
                System.out.println(String.format("%04X", instruction) + ": RETIE");
            }


            //RETURN (0000 0000 0000 1000)
            else if ((instruction & 0x0008) == 0x0008) {
                System.out.println(String.format("%04X", instruction) + ": RETURN");
            }


            //NOP (0000 0000 0xx0 0000)
            else if ((instruction & 0x0000) == 0x0000) {
                return new NOP(instruction, 0x0000);
            }

            else {
                System.err.println("Fehler! Instruction " + String.format("%04X", instruction) + "wurde nicht erkannt");
                return null;
            }

            return null;
    }
}
