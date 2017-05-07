package de.dhbw.Microcontroller;

/*  The PIC16FXX has a 13-bit program counter capable
 *   of addressing an 8K x 14 program memory space.
 *   For the PIC16F84A, the first 1K x 14 (0000h-03FFh) are
 *   physically implemented (Figure 2-1). Accessing a loca-
 *   tion above the physically implemented address will
 *   cause a wraparound. For example, for locations 20h,
 *   420h, 820h, C20h, 1020h, 1420h, 1820h, and 1C20h,
 *   the instruction will be the same.
 *   The RESET vector is at 0000h and the interrupt vector
 *   is at 0004h.
 *
 */
public class Memory extends CPU {
    public Memory(){

    }
}
