package de.dhbw.Services;

import de.dhbw.Microcontroller.Befehle.Instruction;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileInputService {
    public List<Instruction> importLstFile(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                return new ArrayList<>();
            }
        } catch (Exception e){

        }
        return new ArrayList<>();
    }


}
