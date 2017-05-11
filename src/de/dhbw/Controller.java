package de.dhbw;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Microcontroller.Befehle.InstructionView;
import de.dhbw.Microcontroller.InstructionDecoder;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Microcontroller.MemoryView;
import de.dhbw.Services.FileInputService;


import de.dhbw.Services.MemoryViewService;
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
    private TableView<MemoryView> tableMemory;
    @FXML
    private TableColumn<MemoryView, String> tableColumnMemory00;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory01;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory02;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory03;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory04;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory05;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory06;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory07;



    @FXML
    private TextField textFieldRegisterW = new TextField();
    @FXML
    private TextField textFieldPC = new TextField();
    @FXML
    private TextField textFieldStatus = new TextField();



    private Memory memory = Memory.getInstance();
    private List<InstructionView> instructions;
    private List<MemoryView> memoryViewList;
    private InstructionDecoder decoder;
    private FileInputService fileInputService;
    private MemoryViewService memoryViewService;
    private Integer opcodeList[];
    private int currentRow = 0;
    private boolean isRunning = false;

    public Controller(){
    }

    public void updateMemoryView(){
        memoryViewList = memoryViewService.getMemoryContent();

        tableColumnMemory00.setCellValueFactory(new PropertyValueFactory<>("column0"));
        tableColumnMemory01.setCellValueFactory(new PropertyValueFactory<>("column1"));
        tableColumnMemory02.setCellValueFactory(new PropertyValueFactory<>("column2"));
        tableColumnMemory03.setCellValueFactory(new PropertyValueFactory<>("column3"));
        tableColumnMemory04.setCellValueFactory(new PropertyValueFactory<>("column4"));
        tableColumnMemory05.setCellValueFactory(new PropertyValueFactory<>("column5"));
        tableColumnMemory06.setCellValueFactory(new PropertyValueFactory<>("column6"));
        tableColumnMemory07.setCellValueFactory(new PropertyValueFactory<>("column7"));

        tableMemory.getColumns().addAll();

    }

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

        isRunning = true;
        Thread cpuThread = new Thread(() -> {
            try {
                List<Instruction> instructionList = decoder.getInstructionList();
                //System.out.println(instructionList.size());


                    for (int i = 0; i <= instructionList.size(); i++) {
                        while(isRunning) {
                        //currentRow = i;

                        // TODO: ProgramCounter richtig behandeln
                        currentRow = memory.getAddress(Const.PCL);
                        if (i != currentRow) i = currentRow;

                        instructionList.get(i).execute();
                        //instructionList.get(i).displayDebugInfo();

                        System.out.println(memory.getRegisterW());
                        textFieldRegisterW.setText(memory.getRegisterW().toString());
                        textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
                        textFieldStatus.setText(memory.getRegisters()[Const.STATUS].toString());
                        Platform.runLater(() -> tableFileContent.refresh());
                        Thread.sleep(500);

                    }
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
        //TODO: Dieser Inhalt steht hier nur zum Test. Bitte entfernen!!!!!
        System.out.println("Updating MemoryView...");
        updateMemoryView();
    }

    public void reset(ActionEvent actionEvent) {
        isRunning = false;
        currentRow = 0;
        memory.initializeMemory();
        textFieldRegisterW.setText(memory.getRegisterW().toString());
        textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
        textFieldStatus.setText(memory.getRegisters()[Const.STATUS].toString());



        // TODO: Initialisere alles neu
    }

    public void clear(ActionEvent actionEvent) {
            isRunning = false;
            currentRow = 0;
            tableFileContent.getItems().clear();
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




    private MemoryViewService getMemoryViewService(){
        if(memoryViewService == null) {
            memoryViewService = new MemoryViewService();
        }
        return memoryViewService;
    }
    private FileInputService getFileInputService() {
        if (fileInputService == null) {
            fileInputService = new FileInputService();
        }
        return fileInputService;
    }
}
