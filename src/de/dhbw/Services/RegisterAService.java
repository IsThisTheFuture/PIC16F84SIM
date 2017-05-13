package de.dhbw.Services;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.RegisterA;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Lesen jegen einzelnen bit in PORTA Register. Vllt ist ARRAY nicht richtig dafür -> prüfen!
 */
public class RegisterAService {
    List<RegisterA> RegisterAList = new ArrayList<>();
        Memory memory = Memory.getInstance();

        public List<RegisterA> getRegisterAContent(){
            for(int i = Const.PORTA; i < memory.getRegisters().length;)
            {
                RegisterA registerA = new RegisterA();
                registerA.settextFieldRegisterA0(memory.getAddress(Const.PORTA));
                registerA.settextFieldRegisterA1(memory.getAddress((Const.PORTA)+1));
                registerA.settextFieldRegisterA2(memory.getAddress((Const.PORTA)+2));
                registerA.settextFieldRegisterA3(memory.getAddress((Const.PORTA)+3));
                registerA.settextFieldRegisterA4(memory.getAddress((Const.PORTA)+4));
                registerA.settextFieldRegisterA5(memory.getAddress((Const.PORTA)+5));
                registerA.settextFieldRegisterA6(memory.getAddress((Const.PORTA)+6));
                registerA.settextFieldRegisterA7(memory.getAddress((Const.PORTA)+7));
                RegisterAList.add(registerA);
            }
            return RegisterAList;
        }
    }

