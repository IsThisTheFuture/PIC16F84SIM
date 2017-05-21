package de.dhbw;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Model.InstructionView;
import de.dhbw.Services.*;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.MemoryView;


import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.File;
import java.util.List;
import java.awt.Desktop;

public class Controller {
    @FXML
    private TableView<InstructionView> tableFileContent;
    @FXML
    private TableColumn<InstructionView, Boolean> tableColumnBreakpoint;
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
    @FXML
    private TextField textFieldOption;
    @FXML
    private Text textOptionReg7RBPU;
    @FXML
    private Text textOptionReg6INTEDG;
    @FXML
    private Text textOptionReg5T0CS;
    @FXML
    private Text textOptionReg4T0SE;
    @FXML
    private Text textOptionReg3PSA;
    @FXML
    private Text textOptionReg2PS2;
    @FXML
    private Text textOptionReg1PS1;
    @FXML
    private Text textOptionReg0PS0;
    @FXML
    private Text textClockSpeed;
    @FXML
    private Text textRuntime;






    private Memory memory = Memory.getInstance();
    private List<Instruction> instructionList;
    private List<InstructionView> instructionViewList;
    private List<MemoryView> memoryViewList;
    private InstructionDecoderService instructionDecoderService;
    private FileInputService fileInputService;
    private MemoryViewService memoryViewService;
    private InterruptService interruptService;
    private CheckForInterruptService checkForInterruptService;
    private Timer0Service timer0Service;
    private HostServices hostServices;
    private Integer opcodeList[];
    private int currentRow = 0;
    private int speed = 500;
    private boolean isRunning = true;

    public static int runtime = 0;
    public static double runtimeCalculated = 0;
    public static double clockSpeed = 4000; // PIC16F84 läuft mit 4MHz
    public static int inhibitTimer0 = 0;


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
        tableColumnBreakpoint.setCellValueFactory(new PropertyValueFactory<>("breakpoint"));
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("opcode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }

    public void updateUI(){
        updateMemoryView();
        updateTextfieldRegisters();
    }

    public void updateMemoryView(){
        tableMemory.getItems().clear();

        if (memoryViewList != null) memoryViewList.clear();
        memoryViewList = getMemoryViewService().getMemoryContent();
        tableMemory.getItems().addAll(memoryViewList);
        Platform.runLater(() -> tableMemory.refresh());
    }

    public void updateTextfieldRegisters(){
        textFieldRegisterW.setText(String.format("%02x",memory.getRegisterW()).toUpperCase());
        textFieldPC.setText(String.format("%04x", memory.getPc()));
        textFieldStatus.setText(String.format("%02x", memory.getAbsoluteAddress(Const.STATUS)).toUpperCase());

        textStatusReg0C.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 0)));
        textStatusReg1DC.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 1)));
        textStatusReg2Z.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 2)));
        textStatusReg3PD.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 3)));
        textStatusReg4TO.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 4)));
        textStatusReg5RP0.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 5)));
        textStatusReg6RP1.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 6)));
        textStatusReg7IRP.setText(String.format("%1x",getBit(memory.getAbsoluteAddress(Const.STATUS), 7)));


        textFieldOption.setText(String.format("%02x", memory.getAbsoluteAddress(Const.OPTION_REG)).toUpperCase());
        textOptionReg0PS0.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 0)));
        textOptionReg1PS1.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 1)));
        textOptionReg2PS2.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 2)));
        textOptionReg3PSA.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 3)));
        textOptionReg4T0SE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 4)));
        textOptionReg5T0CS.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 5)));
        textOptionReg6INTEDG.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 6)));
        textOptionReg7RBPU.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.OPTION_REG), 7)));

        textClockSpeed.setText(clockSpeed/1000 + " MHz");
        textRuntime.setText(runtime + " µs");
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


                            setOnMouseClicked(event -> {
                                if (event.getClickCount() == 2 && (!isEmpty())) {
                                    if(!item.isBreakpoint()) {
                                        System.out.println("Setting Breakpoint");
                                        item.setBreakPoint(true);
                                        tableFileContent.refresh();
                                    }
                                    else {
                                        System.out.println("Unsetting Breakpoint");
                                        item.setBreakPoint(false);
                                        tableFileContent.refresh();
                                    }
                                }
                            });
                        }
                    });

                    /*
        tableFileContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tableFileContent.getSelectionModel().getSelectedItem().setBreakPoint(true);
                    tableFileContent.getSelectionModel().getSelectedItem().setBreakPoint("B");
                    System.out.println(tableFileContent.getSelectionModel().getSelectedItem());
                    tableFileContent.refresh();
                }
            }
        });
        */

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
                isRunning = true;
                instructionList = instructionDecoderService.getInstructionList();
                for (int i = 0; i <= instructionList.size(); i++) {
                    while(isRunning){
                        currentRow = memory.getPc();

                        if (i != currentRow) i = currentRow;
                        Platform.runLater(() -> tableFileContent.scrollTo(currentRow - 2));


                        //if (instructionList.get(i).isBreakPoint) break;
                        if(instructionViewList.get(i).isBreakpoint()) isRunning = false;
                        instructionList.get(i).execute();

                        Platform.runLater(() -> tableFileContent.refresh());
                        Platform.runLater(() -> updateUI());
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
        runtime = 0;
        Platform.runLater(() -> tableFileContent.refresh());
        memory.initializeMemory();
        updateTextfieldRegisters();
        updateMemoryView();
    }

    public void clear(ActionEvent actionEvent) {
        currentRow = 0;
        isRunning = false;
        runtime = 0;
        memory.initializeMemory();
        tableFileContent.getItems().clear();
    }

    public void pause(ActionEvent actionEvent) {
        if(isRunning == true)
            isRunning=false;
        else
            isRunning = true;
    }

    public void next(ActionEvent actionEvent) {
        Thread thread = new Thread(() -> {
            try {
                instructionList = instructionDecoderService.getInstructionList();
                        currentRow = memory.getPc();

                        Platform.runLater(() -> tableFileContent.scrollTo(currentRow - 2));
                        instructionList.get(currentRow).execute();


                        Platform.runLater(() -> tableFileContent.refresh());
                        Platform.runLater(() -> updateUI());

            } catch (Exception e) {
                System.err.println("Fehler in Methode next()");
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    // TODO
    public void setBreakPoint() {
        System.out.println("TEST");
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }


    public int getBit(int b, int position)
    {
        return ((b >> position) & 1);
    }

    public void toggleBit(int bitPosition, int address){
        int byteValue = memory.getAbsoluteAddress(address);

        // Wenn das Bit an der Stelle bitPosition 0 ist, dann... sonst...
        if(getBit(memory.getAbsoluteAddress(address), bitPosition) == 0){
            // Bit auf 1 setzen
            byteValue = (byteValue | (1 << (bitPosition)));
        } else {
            // Bit auf 0 setzen
            byteValue = (byteValue & ~(1 << (bitPosition)));
        }
        memory.setAbsoluteAddress(address, byteValue);
    }

    public void toggleA0() {
        toggleBit(0, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 0) == 0)
        {
            textFieldRegisterA0.setText("0");
        } else {
            textFieldRegisterA0.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA1() {
        toggleBit(1, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 1) == 0)
        {
            textFieldRegisterA1.setText("0");
        } else {
            textFieldRegisterA1.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA2() {
        toggleBit(2, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 2) == 0)
        {
            textFieldRegisterA2.setText("0");
        } else {
            textFieldRegisterA2.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA3() {
        toggleBit(3, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 3) == 0)
        {
            textFieldRegisterA3.setText("0");
        } else {
            textFieldRegisterA3.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA4() {
        toggleBit(4, Const.PORTA);

        //TODO: Was ist mit TRISA? -> Prüfen ob RB4 In-/ oder Output ist


        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 4) == 0) {
            //getTimer0Service().counterModeNumberOfFallingFlanks++;
            getTimer0Service().incrementCounter(false);
            textFieldRegisterA4.setText("0");
        } else {
            //getTimer0Service().counterModeNumberOfRisingFlanks++;
            getTimer0Service().incrementCounter(true);
            textFieldRegisterA4.setText("1");
            //System.out.println("this is a Rising Flank");

        }

        updateMemoryView();
    }

    public void toggleA5() {
        toggleBit(5, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 5) == 0)
        {
            textFieldRegisterA5.setText("0");
        } else {
            textFieldRegisterA5.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA6() {
        toggleBit(6, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 6) == 0)
        {
            textFieldRegisterA6.setText("0");
        } else {
            textFieldRegisterA6.setText("1");
        }

        updateMemoryView();
    }

    public void toggleA7() {
        toggleBit(7, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 7) == 0)
        {
            textFieldRegisterA7.setText("0");
        } else {
            textFieldRegisterA7.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB0() {
        toggleBit(0, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 0) == 0)
        {
            textFieldRegisterB0.setText("0");
        } else {
            textFieldRegisterB0.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB1() {
        toggleBit(1, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 1) == 0)
        {
            textFieldRegisterB1.setText("0");
        } else {
            textFieldRegisterB1.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB2() {
        toggleBit(2, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 2) == 0)
        {
            textFieldRegisterB2.setText("0");
        } else {
            textFieldRegisterB2.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB3() {
        toggleBit(3, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 3) == 0)
        {
            textFieldRegisterB3.setText("0");
        } else {
            textFieldRegisterB3.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB4() {
        toggleBit(4, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 4) == 0)
        {
            textFieldRegisterB4.setText("0");
        } else {
            textFieldRegisterB4.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB5() {
        toggleBit(5, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 5) == 0)
        {
            textFieldRegisterB5.setText("0");
        } else {
            textFieldRegisterB5.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB6() {
        toggleBit(6, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 6) == 0)
        {
            textFieldRegisterB6.setText("0");
        } else {
            textFieldRegisterB6.setText("1");
        }

        updateMemoryView();
    }

    public void toggleB7() {
        toggleBit(7, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 7) == 0)
        {
            textFieldRegisterB7.setText("0");
        } else {
            textFieldRegisterB7.setText("1");
        }

        updateMemoryView();
    }

    public void openDocumentation(ActionEvent actionEvent) {
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        try {
            File file = new File("/tmp/test.txt");
            Desktop desktop = Desktop.getDesktop();

            if(file.exists()) System.out.println("File exists");
            //if(file.exists()) desktop.open(file);


            Desktop.getDesktop().open(new File("///tmp/test.txt"));
        } catch (Exception e) {

        }
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

    private InterruptService getInterruptService() {
        if (interruptService == null) {
            interruptService = new InterruptService();
        }
        return interruptService;
    }

    private CheckForInterruptService getCheckForInterruptService() {
        if (checkForInterruptService == null) {
            checkForInterruptService = new CheckForInterruptService();
        }
        return checkForInterruptService;
    }

    private Timer0Service getTimer0Service() {
        if(timer0Service == null) {
            timer0Service = new Timer0Service();
        }
        return timer0Service;
    }
}