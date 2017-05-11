package de.dhbw.Services;

import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.MemoryView;

import java.util.ArrayList;
import java.util.List;

public class MemoryViewService {
    List<MemoryView> memoryViewList = new ArrayList<>();
    Memory memory = Memory.getInstance();

    public List<MemoryView> getMemoryContent(){
        for(int i = 0; i < memory.getRegisters().length; i += 8)
        {
            MemoryView memoryView = new MemoryView();
            memoryView.setColumn0(memory.getAddress(i));
            memoryView.setColumn1(memory.getAddress(i+1));
            memoryView.setColumn1(memory.getAddress(i+2));
            memoryView.setColumn1(memory.getAddress(i+3));
            memoryView.setColumn1(memory.getAddress(i+4));
            memoryView.setColumn1(memory.getAddress(i+5));
            memoryView.setColumn1(memory.getAddress(i+6));
            memoryView.setColumn1(memory.getAddress(i+7));
            memoryView.setColumn1(memory.getAddress(i+8));
            memoryViewList.add(memoryView);
        }

        return memoryViewList;
    }
}
