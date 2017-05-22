package de.dhbw.Services;

import de.dhbw.Microcontroller.Stack;
import de.dhbw.Model.StackView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobi on 22.05.17.
 */
public class StackViewService {
    private List<StackView> stackViewList = new ArrayList<>();

    private Stack stack = Stack.getInstance();
    Integer[] stackContent = stack.getStackContent();

    public List<StackView> getStackViewList(){
        for (int i = 0; i < stackContent.length; i++){
            if(stackContent[i] != null){
            StackView stackView = new StackView();
            stackView.setStackContent(stackContent[i]);
            stackViewList.add(stackView);
            }
        }

        return stackViewList;
    }
}
