package de.dhbw.Services;

import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.MemoryView;

import java.util.ArrayList;
import java.util.List;

public class MemoryViewService {
    List<MemoryView> memoryViewList = new ArrayList<>();
    Memory memory = Memory.getInstance();

    public List<MemoryView> getMemoryContent(){
        for(int i = 0; i < memory.getRegisters().length; i += 8)
        {
            MemoryView memoryView = new MemoryView();


            memoryView.setMemoryRow(i/8);
            memoryView.setColumn0(memory.getAddress(i));
            memoryView.setColumn1(memory.getAddress(i+1));
            memoryView.setColumn2(memory.getAddress(i+2));
            memoryView.setColumn3(memory.getAddress(i+3));
            memoryView.setColumn4(memory.getAddress(i+4));
            memoryView.setColumn5(memory.getAddress(i+5));
            memoryView.setColumn6(memory.getAddress(i+6));
            memoryView.setColumn7(memory.getAddress(i+7));
            memoryViewList.add(memoryView);
        }
        return memoryViewList;
    }
}
