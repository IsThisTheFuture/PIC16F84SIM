package de.dhbw;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Model.InstructionView;
import de.dhbw.Services.InstructionDecoderService;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.MemoryView;
import de.dhbw.Services.FileInputService;


import de.dhbw.Services.MemoryViewService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    private TableColumn<MemoryView, Integer> tableColumnMemoryRow;
    @FXML
    private TableColumn<MemoryView, Byte> tableColumnMemory00;
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
    private TextField textFieldRegisterW;
    @FXML
    private TextField textFieldPC;
    @FXML
    private TextField textFieldStatus;

    @FXML
    private TextField textFieldRegisterA7;
    @FXML
    private TextField textFieldRegisterA6;
    @FXML
    private TextField textFieldRegisterA5;
    @FXML
    private TextField textFieldRegisterA4;
    @FXML
    private TextField textFieldRegisterA3;
    @FXML
    private TextField textFieldRegisterA2;
    @FXML
    private TextField textFieldRegisterA1;
    @FXML
    private TextField textFieldRegisterA0;




    private Memory memory = Memory.getInstance();
    private List<Instruction> instructionList;
    private List<InstructionView> instructionViewList;
    private List<MemoryView> memoryViewList;
    private InstructionDecoderService instructionDecoderService;
    private FileInputService fileInputService;
    private MemoryViewService memoryViewService;
    private Integer opcodeList[];
    private int currentRow = 0;


    public void initialize(){
        initializeMemoryView();
        initializeFileContentView();
        initializeTextfieldRegisters();
    }


    // TODO: Lesen/Einsetzen des Wertes im Register A
    public void readPortA ()
    {
    }
    // TODO: Lesen/Einsetzen des Wertes im Register b
    public void readPortB ()
    {
    }


    public void initializeTextfieldRegisters(){
        textFieldRegisterW.setText(memory.getRegisterW().toString());
        textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
        textFieldStatus.setText(memory.getRegisters()[Const.STATUS].toString());
    }


    public void initializeMemoryView(){
        tableColumnMemoryRow.setCellValueFactory(new PropertyValueFactory<>("memoryRow"));
        tableColumnMemory00.setCellValueFactory(new PropertyValueFactory<>("column0"));
        tableColumnMemory01.setCellValueFactory(new PropertyValueFactory<>("column1"));
        tableColumnMemory02.setCellValueFactory(new PropertyValueFactory<>("column2"));
        tableColumnMemory03.setCellValueFactory(new PropertyValueFactory<>("column3"));
        tableColumnMemory04.setCellValueFactory(new PropertyValueFactory<>("column4"));
        tableColumnMemory05.setCellValueFactory(new PropertyValueFactory<>("column5"));
        tableColumnMemory06.setCellValueFactory(new PropertyValueFactory<>("column6"));
        tableColumnMemory07.setCellValueFactory(new PropertyValueFactory<>("column7"));

        if (memoryViewList != null) memoryViewList.clear();
        memoryViewList = getMemoryViewService().getMemoryContent();
        tableMemory.getItems().addAll(memoryViewList);

    }

    public void initializeFileContentView(){
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("opcode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }

    public void updateMemoryView(){
        tableMemory.getItems().clear();

        if (memoryViewList != null) memoryViewList.clear();
        memoryViewList = getMemoryViewService().getMemoryContent();
        tableMemory.getItems().addAll(memoryViewList);
        tableMemory.refresh();
    }

    public void updateTextfieldRegisters(){
        textFieldRegisterW.setText(memory.getRegisterW().toString());
        textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
        textFieldStatus.setText(memory.getRegisters()[Const.STATUS].toString());
        //Platform.runLater(() -> tableFileContent.refresh());
    }

    public void updateTextfieldRegisterAB(){
    }


    public void openFile(ActionEvent actionEvent) {
        if (instructionViewList != null) instructionViewList.clear();
        instructionViewList = getFileInputService().importLstFile();

        tableFileContent.getItems().addAll(instructionViewList);
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

        opcodeList = new Integer[instructionViewList.size()];
        for (int i = 0; i < instructionViewList.size(); i++)
        {
            opcodeList[i] = Integer.parseInt(instructionViewList.get(i).getOpcode(),16);
        }
        instructionDecoderService = getInstructionDecoderService();
        instructionDecoderService.decode(opcodeList);
    }


    public void run(ActionEvent actionEvent) {
        if (tableFileContent.getItems().isEmpty()) {  return;  }

        Thread cpuThread = new Thread(() -> {
            try {
                instructionList = instructionDecoderService.getInstructionList();
                    for (int i = 0; i <= instructionList.size(); i++) {
                        // TODO: ProgramCounter richtig behandeln
                        currentRow = memory.getAddress(Const.PCL);
                        if (i != currentRow) i = currentRow;

                        instructionList.get(i).execute();
                        //instructionList.get(i).displayDebugInfo();

                        //System.out.println("W: " + memory.getRegisterW() + "   STATUS: " + memory.getAddress(Const.STATUS));

                        Platform.runLater(() -> tableFileContent.refresh());
                        updateTextfieldRegisters();
                        updateTextfieldRegisterAB();
                        updateMemoryView();
                        Thread.sleep(500);
                }
            } catch (Exception e) {
                System.err.println("Fehler in Methode run()");
                e.printStackTrace();
            }
        });
        cpuThread.setDaemon(true);
        cpuThread.start();
    }

    public void reset(ActionEvent actionEvent) {
        currentRow = 0;
        memory.initializeMemory();
        updateTextfieldRegisters();
        updateMemoryView();
    }

    public void clear(ActionEvent actionEvent) {
            currentRow = 0;
            tableFileContent.getItems().clear();
    }

    public void next(ActionEvent actionEvent) {}

    public void openDocumentation(ActionEvent actionEvent) {
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }


    public int getBit(int b, int position)
    {
        return ((b >> position) & 1);
    }

    public void toggleBit(int bitPosition, int address){
        int b = memory.getAddress(address);

        // Wenn das Bit an der Stelle bitPosition 0 ist, dann... sonst...
        if(getBit(memory.getAddress(address), bitPosition) == 0){
            // Bit auf 1 setzen
            b = (b | (1 << (bitPosition)));
        } else {
            // Bit auf 0 setzen
            b = (b & ~(1 << (bitPosition)));
        }
        memory.setAddress(address, b);
    }

    public void toggleA0(Event clickEvent) {
        toggleBit(0, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 0) == 0)
        {
            textFieldRegisterA0.setText("0");
        } else {
            textFieldRegisterA0.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA1(Event clickEvent) {
        toggleBit(1, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 1) == 0)
        {
            textFieldRegisterA1.setText("0");
        } else {
            textFieldRegisterA1.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
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



    private InstructionDecoderService getInstructionDecoderService(){
        if(instructionDecoderService == null) {
            instructionDecoderService = new InstructionDecoderService();
        }
        return instructionDecoderService;
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