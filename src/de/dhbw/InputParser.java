package de.dhbw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputParser {

    /*
     * Liest ein Programm im LST Format ein und gibt ein Array zurück
     * Es wird vorausgesetzt, dass die LST Datei korrekt formatiert ist
     */

    public List<String> readLST(File lstDatei) {
        List<String> programCode = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(lstDatei));
            String line;
            while ((line = br.readLine()) != null) {
                programCode.add(line);
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
        return programCode;
    }


    // Sucht nach den eigentlichen Instruktionen und löscht alles andere
    public Integer[] retrieveInstructions(List<String> lstContent)
    {
        // Setze alle Felder null, die keine Befehle enthalten
        for (int i = 0; i < lstContent.size(); i++) {
            // Wenn erstes Zeichen ein Leerzeichen ist, handelt es sich NICHT um einen Befehl
            if(lstContent.get(i).charAt(0) == ' ')
            {
                lstContent.set(i,null);
            }
        }

        // Ermittle Arraygröße (Anzahl Zeilen mit Befehlen)
        int arraySize = 0;
        for (String s : lstContent)
        {
            if (s != null) {
                arraySize++;
            }
        }

        int arrayIndex = 0;
        String strInstructions[] = new String[arraySize];
        for (int i = 0; i < lstContent.size(); i++) {
            if(lstContent.get(i) == null)
            {
                // Tue nichts
            } else {
                // Die ersten 4 Zeichen werden abgeschnitten (Program Counter)
                lstContent.set(i, lstContent.get(i).substring(5));

                // Dann wird beim ersten Leerzeichen abgeschnitten. So bleibt nur die Instruktion
                strInstructions[arrayIndex] = lstContent.get(i).split(" ")[0];
                lstContent.set(i, lstContent.get(i).split(" ")[0]);
                arrayIndex++;
            }
        }


        // Strings zu Base-16 Integer konvertieren
        Integer instructions[] = new Integer[strInstructions.length];
        for (int i = 0; i < strInstructions.length; i++)
        {
            instructions[i] = Integer.parseInt(strInstructions[i], 16);
        }

        // Array mit Instruktionen (als INT) zurückgeben
        return instructions;
    }
}