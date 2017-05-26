package de.dhbw.Services;

import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.MemoryView;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MemoryViewService {
    List<MemoryView> memoryViewList;// = new ArrayList<>();
    Memory memory = Memory.getInstance();

    public List<MemoryView> getMemoryContent(){
        memoryViewList = new ArrayList<>();
        for(int i = 0; i < memory.getRegisters().length; i += 8)
        {
            MemoryView memoryView = new MemoryView();

            memoryView.setMemoryRow(i);
            memoryView.setColumn0(memory.getAbsoluteAddress(i));
            memoryView.setColumn1(memory.getAbsoluteAddress(i+1));
            memoryView.setColumn2(memory.getAbsoluteAddress(i+2));
            memoryView.setColumn3(memory.getAbsoluteAddress(i+3));
            memoryView.setColumn4(memory.getAbsoluteAddress(i+4));
            memoryView.setColumn5(memory.getAbsoluteAddress(i+5));
            memoryView.setColumn6(memory.getAbsoluteAddress(i+6));
            memoryView.setColumn7(memory.getAbsoluteAddress(i+7));

            memoryViewList.add(memoryView);
        }
        return memoryViewList;
    }
}
