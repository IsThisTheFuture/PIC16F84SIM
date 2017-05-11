package de.dhbw.Services;

import de.dhbw.Model.InstructionView;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileInputService {

    List<InstructionView> programCode = new ArrayList<>();

    public List<InstructionView> importLstFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                return new ArrayList<>();
            }

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(selectedFile));
                String line;
                while ((line = br.readLine()) != null) {
                    InstructionView instruction = new InstructionView();


                    // Prüfen, ob Befehl ausführbar ist. Nur dann wird er zur Liste hinzugefügt
                    char c = line.charAt(0);
                    if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
                    {
                        String subline = line.substring(0, 25);
                        //subline = subline.replaceAll("\\s+", "");

                        instruction.setZeilennummer(Integer.parseInt(subline.substring(0, 4), 16));
                        instruction.setOpcode(subline.substring(5,9));

                        String subline2 = line.substring(25);
                        String[] strings = subline2.split(";");
                        for (int i = 0; i < strings.length; i++) {
                            if (i == 0) {
                                instruction.setBefehl(strings[0]);
                                continue;
                            }
                            instruction.setComment(strings[i]);
                        }

                        programCode.add(instruction);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
        return programCode;
    }
}