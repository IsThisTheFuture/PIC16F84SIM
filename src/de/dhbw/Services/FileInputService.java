package de.dhbw.Services;

import de.dhbw.Microcontroller.Befehle.InstructionView;
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

            //return new ArrayList<>();


            //List<InstructionView> programCode = new ArrayList<>();

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

                        instruction.setZeilennummer(Integer.parseInt(subline.substring(0, 4)));
                        instruction.setOpcode(subline.substring(5,9));

                        //instruction.setZeilennummer(Integer.parseInt(subline.substring(0, 4), 16));


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

            for(int i =0; i<programCode.size(); i++)
            {
                System.out.print(programCode.get(i).getZeilennummer() + "   ");
                System.out.print(programCode.get(i).getOpcode()+ "   ");
                System.out.print(programCode.get(i).getBefehl()+ "   ");
                System.out.print(programCode.get(i).getComment()+ "\n");
            }


        } catch (Exception e) {

        }

        //return new ArrayList<>();
        return programCode;
    }
}



    /* public List<Befehl> importFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                return new ArrayList<>();
            }
            InputStream inputStream = new FileInputStream(selectedFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<Befehl> befehle = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                Befehl befehl = new Befehl();
                char ch = line.charAt(0);

                if (ch != ' ' && ch != '\t' && ch != '\r' && ch != '\n') {
                    befehl.setAusfuehrbar(true);
                }

                if (befehl.isAusfuehrbar()) {
                    String subline = line.substring(0, 25);
                    subline = subline.replaceAll("\\s+", "");
                    befehl.setZeigernummer(Integer.parseInt(subline.substring(0, 4), 16));
                    befehl.setBefehlscode(subline.substring(4, 8));
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(8, 13)));
                } else {
                    String subline = line.substring(0, 25);
                    subline = subline.replaceAll("\\s+", "");
                    befehl.setBefehlscode("-");
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(0, 5)));
                    befehl.setZeigernummer(0);
                }

                String subline2 = line.substring(25);
                String[] strings = subline2.split(";");
                for (int i = 0; i < strings.length; i++) {
                    if (i == 0) {
                        befehl.setBefehl(strings[0]);
                        continue;
                    }
                    befehl.setKommentar(strings[i]);
                }
                befehle.add(befehl);
            }
            in.close();
            return befehle;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    */

