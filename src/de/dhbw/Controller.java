package de.dhbw;


import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Microcontroller.CPU;
import de.dhbw.Services.FileInputService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class Controller {

    @FXML
    private TableView<Instruction> tableFileContent;
    //private TableView<Befehl> tableFileContent;
    @FXML
    private TableColumn<Instruction, Integer> tableColumnZeilennummer;
    @FXML
    private TableColumn<Instruction, String> tableColumnBefehlscode;
    @FXML
    private TableColumn<Instruction, String> tableColumnBefehl;
    @FXML
    private TableColumn<Instruction, String> tableColumnKommentar;


    private List<Instruction> instructions;
    private InputParser parser;
    private FileInputService fileInputService;

    public void openFile(ActionEvent actionEvent) {
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("Zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("Befehlscode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("Befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("Kommentar"));

        // Einträge löschen, falls nicht leer
        if (instructions != null) instructions.clear();
        instructions = getFileInputService().importLstFile();

    }

    /*public void openFile(ActionEvent actionEvent) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
           // return file;
            InputParser parser = new InputParser();
            List<String> lstContent = parser.readLST(file);






        } else {
            System.err.println("Datei konnte nicht geöffnet werden!");
        }
        //return null;
    }
    */

    public void openDocumentation(ActionEvent actionEvent) {
    }

    public void run(ActionEvent actionEvent) {
        // CPU Thread benachrichtigen
        try {
            synchronized (CPU.getInstance()) {
                CPU.getInstance().notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void next(ActionEvent actionEvent) {
    }

    public void reset(ActionEvent actionEvent) {
    }

    public void clear(ActionEvent actionEvent) {
    }

    public void toggleA0(ActionEvent actionEvent) {
    }

    public void toggleA1(ActionEvent actionEvent) {
    }

    public void toggleA2(ActionEvent actionEvent) {
    }

    public void toggleA3(ActionEvent actionEvent) {
    }

    public void toggleA4(ActionEvent actionEvent) {
    }

    public void toggleA5(ActionEvent actionEvent) {
    }

    public void toggleA6(ActionEvent actionEvent) {
    }

    public void toggleA7(ActionEvent actionEvent) {
    }

    public void toggleB0(ActionEvent actionEvent) {
    }

    public void toggleB1(ActionEvent actionEvent) {
    }

    public void toggleB2(ActionEvent actionEvent) {
    }

    public void toggleB3(ActionEvent actionEvent) {
    }

    public void toggleB4(ActionEvent actionEvent) {
    }

    public void toggleB5(ActionEvent actionEvent) {
    }

    public void toggleB6(ActionEvent actionEvent) {
    }

    public void toggleB7(ActionEvent actionEvent) {
    }



    // Getter Methoden für Services:

    private FileInputService getFileInputService() {
        if (fileInputService == null) {
            fileInputService = new FileInputService();
        }
        return fileInputService;
    }
}
