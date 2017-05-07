package de.dhbw;

import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Microcontroller.Befehle.InstructionView;
import de.dhbw.Microcontroller.CPU;
import de.dhbw.Microcontroller.InstructionDecoder;
import de.dhbw.Services.FileInputService;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class Controller {

    @FXML
    private TableView<InstructionView> tableFileContent;
    @FXML
    private TableColumn<InstructionView, Integer> tableColumnZeilennummer;
    @FXML
    private TableColumn<InstructionView, String> tableColumnBefehlscode;
    @FXML
    private TableColumn<InstructionView, String> tableColumnBefehl;
    @FXML
    private TableColumn<InstructionView, String> tableColumnKommentar;


    @FXML
    private TextField textFieldRegisterW = new TextField();
    @FXML
    private TextField textFieldPC = new TextField();
    @FXML
    private TextField textFieldStatus = new TextField();



    private List<InstructionView> instructions;
    private InstructionDecoder decoder;
    private FileInputService fileInputService;
    private Integer opcodeList[];
    private int currentRow = 0;


    public void openFile(ActionEvent actionEvent) {
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("opcode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("comment"));


        if (instructions != null) instructions.clear();
        instructions = getFileInputService().importLstFile();

        tableFileContent.getItems().addAll(instructions);
        tableFileContent.setRowFactory(tv -> new TableRow<InstructionView>() {
            @Override
            public void updateItem(InstructionView item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getZeilennummer() == currentRow) {
                   setStyle("-fx-background-color: #5cadff;");
                }
                else {
                    setStyle("");
                }
            }
        });


        // Dekodiere eingelesene Instruktionen
        opcodeList = new Integer[instructions.size()];

        for (int i = 0; i < instructions.size(); i++)
        {
            opcodeList[i] = Integer.parseInt(instructions.get(i).getOpcode(),16);
        }

        decoder = new InstructionDecoder();
        decoder.decode(opcodeList);
    }


    public void openDocumentation(ActionEvent actionEvent) {
    }


    public void run(ActionEvent actionEvent) {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }


        Thread cpuThread = new Thread(() -> {
            try {
                // programCode hat die eigentlichen Befehle, instructions ist der GUI Inhalt
                List<Instruction> programCode = decoder.getProgramCode();
                System.out.println(programCode.size());
                for (int i = 0; i < programCode.size(); i++) {
                    programCode.get(i).execute();
                    programCode.get(i).displayDebugInfo();

                    // TODO: programcode sollte PC beinhalten
                    currentRow++;

                    textFieldRegisterW.setText(CPU.getInstance().register.w.toString());

                    textFieldPC.setText(String.format("%04x", CPU.getInstance().register.pc));
                    textFieldStatus.setText(CPU.getInstance().register.STATUS.toString());
                    Platform.runLater(() -> tableFileContent.refresh());
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.err.println("Fehler in Methode run()");
                e.printStackTrace();
            }
        });
        cpuThread.setDaemon(true);
        cpuThread.start();
    }

    public void next(ActionEvent actionEvent) {
    }

    public void reset(ActionEvent actionEvent) {
        currentRow = 0;
        // TODO: Initialisere alles neu
    }

    public void clear(ActionEvent actionEvent) {
            currentRow = 0;
            tableFileContent.getItems().clear();
            decoder.clearProgramCode();
    }

    public void close(ActionEvent actionEvent) {
    Platform.exit();
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




    private FileInputService getFileInputService() {
        if (fileInputService == null) {
            fileInputService = new FileInputService();
        }
        return fileInputService;
    }
}
