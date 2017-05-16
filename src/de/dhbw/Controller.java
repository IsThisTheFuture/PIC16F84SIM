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
import javafx.scene.text.Text;

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
    private TableColumn<MemoryView, Integer> tableColumnMemory00;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory01;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory02;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory03;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory04;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory05;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory06;
    @FXML
    private TableColumn<MemoryView, Integer> tableColumnMemory07;
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
    @FXML
    private TextField textFieldRegisterB0;
    @FXML
    private TextField textFieldRegisterB1;
    @FXML
    private TextField textFieldRegisterB2;
    @FXML
    private TextField textFieldRegisterB3;
    @FXML
    private TextField textFieldRegisterB4;
    @FXML
    private TextField textFieldRegisterB5;
    @FXML
    private TextField textFieldRegisterB6;
    @FXML
    private TextField textFieldRegisterB7;
    @FXML
    private Text textStatusReg7IRP;
    @FXML
    private Text textStatusReg6RP1;
    @FXML
    private Text textStatusReg5RP0;
    @FXML
    private Text textStatusReg4TO;
    @FXML
    private Text textStatusReg3PD;
    @FXML
    private Text textStatusReg2Z;
    @FXML
    private Text textStatusReg1DC;
    @FXML
    private Text textStatusReg0C;
    @FXML
    private TextField textFieldSpeed;




    private Memory memory = Memory.getInstance();
    private List<Instruction> instructionList;
    private List<InstructionView> instructionViewList;
    private List<MemoryView> memoryViewList;
    private InstructionDecoderService instructionDecoderService;
    private FileInputService fileInputService;
    private MemoryViewService memoryViewService;
    private Integer opcodeList[];
    private int currentRow = 0;
    private int speed = 500;
    private boolean isRunning = true;


    public void initialize(){
        initializeMemoryView();
        initializeFileContentView();
        updateTextfieldRegisters();
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
       // textFieldRegisterW.setText(memory.getRegisterW().toString());
        //textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
        //textFieldStatus.setText(memory.getRegisters()[Const.STATUS].toString());
        //Platform.runLater(() -> tableFileContent.refresh());
        textFieldRegisterW.setText(String.format("%02x",memory.getRegisterW()).toUpperCase());
        textFieldPC.setText(String.format("%04x", memory.getRegisters()[Const.PCL]));
        textFieldStatus.setText(String.format("%02x", memory.getAddress(Const.STATUS)).toUpperCase());

        textStatusReg0C.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 0)));
        textStatusReg1DC.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 1)));
        textStatusReg2Z.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 2)));
        textStatusReg3PD.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 3)));
        textStatusReg4TO.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 4)));
        textStatusReg5RP0.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 5)));
        textStatusReg6RP1.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 6)));
        textStatusReg7IRP.setText(String.format("%1x",getBit(memory.getAddress(Const.STATUS), 7)));
    }

    public void updateTextfieldRegisterAB(){
        //TODO
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
                    while(isRunning){
                        currentRow = memory.getPc();

                        if (i != currentRow) i = currentRow;

                        instructionList.get(i).execute();
                        //instructionList.get(i).displayDebugInfo();

                        Platform.runLater(() -> tableFileContent.refresh());
                        updateTextfieldRegisters();
                        updateTextfieldRegisterAB();
                        updateMemoryView();
                        Thread.sleep(speed);
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

    public void reset(ActionEvent actionEvent) {
        isRunning = false;
        currentRow = 0;
        Platform.runLater(() -> tableFileContent.refresh());
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

    public void toggleA2(Event clickEvent) {
        toggleBit(2, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 2) == 0)
        {
            textFieldRegisterA2.setText("0");
        } else {
            textFieldRegisterA2.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA3(Event clickEvent) {
        toggleBit(3, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 3) == 0)
        {
            textFieldRegisterA3.setText("0");
        } else {
            textFieldRegisterA3.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA4(Event clickEvent) {
        toggleBit(4, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 4) == 0)
        {
            textFieldRegisterA4.setText("0");
        } else {
            textFieldRegisterA4.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA5(Event clickEvent) {
        toggleBit(5, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 5) == 0)
        {
            textFieldRegisterA5.setText("0");
        } else {
            textFieldRegisterA5.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA6(Event clickEvent) {
        toggleBit(6, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 6) == 0)
        {
            textFieldRegisterA6.setText("0");
        } else {
            textFieldRegisterA6.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleA7(Event clickEvent) {
        toggleBit(7, Const.PORTA);

        if (getBit(memory.getAddress(Const.PORTA), 7) == 0)
        {
            textFieldRegisterA7.setText("0");
        } else {
            textFieldRegisterA7.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB0(Event clickEvent) {
        toggleBit(0, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 0) == 0)
        {
            textFieldRegisterB0.setText("0");
        } else {
            textFieldRegisterB0.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB1(Event clickEvent) {
        toggleBit(1, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 1) == 0)
        {
            textFieldRegisterB1.setText("0");
        } else {
            textFieldRegisterB1.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB2(Event clickEvent) {
        toggleBit(2, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 2) == 0)
        {
            textFieldRegisterB2.setText("0");
        } else {
            textFieldRegisterB2.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB3(Event clickEvent) {
        toggleBit(3, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 3) == 0)
        {
            textFieldRegisterB3.setText("0");
        } else {
            textFieldRegisterB3.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB4(Event clickEvent) {
        toggleBit(4, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 4) == 0)
        {
            textFieldRegisterB4.setText("0");
        } else {
            textFieldRegisterB4.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB5(Event clickEvent) {
        toggleBit(5, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 5) == 0)
        {
            textFieldRegisterB5.setText("0");
        } else {
            textFieldRegisterB5.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB6(Event clickEvent) {
        toggleBit(6, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 6) == 0)
        {
            textFieldRegisterB6.setText("0");
        } else {
            textFieldRegisterB6.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }

    public void toggleB7(Event clickEvent) {
        toggleBit(7, Const.PORTB);

        if (getBit(memory.getAddress(Const.PORTB), 7) == 0)
        {
            textFieldRegisterB7.setText("0");
        } else {
            textFieldRegisterB7.setText("1");
        }

        updateTextfieldRegisterAB();
        updateMemoryView();
    }


    public void setSpeed(ActionEvent actionEvent){
        this.speed = Integer.parseInt(textFieldSpeed.getText());
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