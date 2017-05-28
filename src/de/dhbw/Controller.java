package de.dhbw;

import de.dhbw.Constants.Const;
import de.dhbw.Microcontroller.Befehle.Instruction;
import de.dhbw.Model.InstructionView;
import de.dhbw.Model.StackView;
import de.dhbw.Services.*;
import de.dhbw.Microcontroller.Memory;
import de.dhbw.Model.MemoryView;


import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sun.javafx.PlatformUtil.isLinux;

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
    private TableView<StackView> tableStack;
    @FXML
    private TableColumn<StackView, String> tableColumnStack;
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
    private TextField textFieldTRISA0;
    @FXML
    private TextField textFieldTRISA1;
    @FXML
    private TextField textFieldTRISA2;
    @FXML
    private TextField textFieldTRISA3;
    @FXML
    private TextField textFieldTRISA4;
    @FXML
    private TextField textFieldTRISA5;
    @FXML
    private TextField textFieldTRISA6;
    @FXML
    private TextField textFieldTRISA7;
    @FXML
    private TextField textFieldTRISB0;
    @FXML
    private TextField textFieldTRISB1;
    @FXML
    private TextField textFieldTRISB2;
    @FXML
    private TextField textFieldTRISB3;
    @FXML
    private TextField textFieldTRISB4;
    @FXML
    private TextField textFieldTRISB5;
    @FXML
    private TextField textFieldTRISB6;
    @FXML
    private TextField textFieldTRISB7;
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
    @FXML
    private Button btnWDT;
    @FXML
    private ComboBox<String> comboboxTaktgenerator;// = new ComboBox<String>();
    @FXML
    private TextField taktGenFrequenz;
    @FXML
    private ToggleButton btnTaktGen;
    @FXML
    private TextField textFieldClockSpeed;
    @FXML
    private TextField textFieldIntconReg7GIE;
    @FXML
    private TextField textFieldIntconReg6EEIE;
    @FXML
    private TextField textFieldIntconReg5T0IE;
    @FXML
    private TextField textFieldIntconReg4INTE;
    @FXML
    private TextField textFieldIntconReg3RBIE;
    @FXML
    private TextField textFieldIntconReg2T0IF;
    @FXML
    private TextField textFieldIntconReg1INTF;
    @FXML
    private TextField textFieldIntconReg0RBIF;



    private Memory memory = Memory.getInstance();
    private List<Instruction> instructionList;
    private List<InstructionView> instructionViewList;
    private List<MemoryView> memoryViewList;
    private List<StackView> stackViewList;
    private InstructionDecoderService instructionDecoderService;
    private FileInputService fileInputService;
    private MemoryViewService memoryViewService;
    private InterruptService interruptService;
    private CheckForInterruptService checkForInterruptService;
    private Timer0Service timer0Service;
    private StackViewService stackViewService;
    private HostServices hostServices;
    private Integer opcodeList[];

    private int extTaktGenFreq;
    private double sleepTime;
    private int currentRow = 0;
    private int speed = 500;
    private boolean isRunning = true;
    private float oscillatorPeriod;
    private long cyclePeriod;
    private boolean taktgeneratorEnabled;

    public static int runtime = 0;
    public static double runtimeCalculated = 0;
    public static double clockSpeed; // PIC16F84 läuft mit 4MHz
    public static int inhibitTimer0 = 0;


    public void initialize(){
        initializeMemoryView();
        initializeFileContentView();
        updateTextfieldRegisters();
        initialzizeTaktgen();
        initializeStackView();
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

        //if (memoryViewList != null) memoryViewList.clear();
        memoryViewList = getMemoryViewService().getMemoryContent();
        tableMemory.getItems().setAll(memoryViewList);

    }

    public void initializeFileContentView(){
        tableColumnBreakpoint.setCellValueFactory(new PropertyValueFactory<>("breakpoint"));
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("opcode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("comment"));

    }

    public void initializeStackView(){
        tableColumnStack.setCellValueFactory(new PropertyValueFactory<>("stackContent"));
    }

    public void initialzizeTaktgen(){
        ObservableList<String> ports = FXCollections.observableArrayList(
                "RA0", "RA1", "RA2", "RA3", "RA4", "RA5", "RA6", "RA7", "RB0", "RB1", "RB2", "RB3", "RB4", "RB5", "RB6", "RB7");
        comboboxTaktgenerator.getItems().addAll(ports);
    }

    public void updateUI(){
        Platform.runLater(() -> updateMemoryView());
        Platform.runLater(() -> updateTextfieldRegisters());
        Platform.runLater(() -> updateStackView());
        //updateMemoryView();
        //updateTextfieldRegisters();
        //updateStackView();
    }

    public void updateStackView(){
        tableStack.getItems().clear();

        if(stackViewList != null) stackViewList.clear();
        stackViewList = getStackViewService().getStackViewList();
        tableStack.getItems().addAll(stackViewList);
        Platform.runLater(() -> tableStack.refresh());
    }

    public void updateMemoryView(){
        //tableMemory.getItems().clear();

        //if (memoryViewList != null) memoryViewList.clear();
        memoryViewList = getMemoryViewService().getMemoryContent();

        tableMemory.getItems().setAll(memoryViewList);
        //Platform.runLater(() -> tableMemory.refresh());
        //tableMemory.refresh();
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

        textFieldIntconReg0RBIF.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 0)));
        textFieldIntconReg1INTF.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 1)));
        textFieldIntconReg2T0IF.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 2)));
        textFieldIntconReg3RBIE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 3)));
        textFieldIntconReg4INTE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 4)));
        textFieldIntconReg5T0IE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 5)));
        textFieldIntconReg6EEIE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 6)));
        textFieldIntconReg7GIE.setText(String.format("%1x", getBit(memory.getAbsoluteAddress(Const.INTCON), 7)));

        //textClockSpeed.setText(clockSpeed/1000 + " MHz");
        textRuntime.setText(runtime + " µs");


        if(memory.isWatchDogTimerEnabled()){
            btnWDT.setText("Disable WDT");
        } else {
            btnWDT.setText("Enable WDT");
        }

    }

    public void openFile(ActionEvent actionEvent) {
        memory.initializeMemory();
        if (tableFileContent != null) tableFileContent.getItems().clear();
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

                        if(instructionViewList.get(i).isBreakpoint()) isRunning = false;

                        //if(!memory.isSleepMode())
                        //instructionList.get(i).execute();

                        executeCycle(instructionList.get(i));

                        Platform.runLater(() -> tableFileContent.refresh());
                        Platform.runLater(() -> updateUI());

                        //tableFileContent.refresh();
                        //updateUI();

                        oscillatorPeriod = (speed * 1000) / 1000000;
                        //speed = speed / (4*(1000000));
                        Thread.sleep(speed);
                        //Thread.sleep((long) oscillatorPeriod);

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

    private boolean executeCycle(Instruction instruction){
        if(!memory.isSleepMode()){
            instruction.execute();
            return true;
        }

        /*
        if(memory.isWatchDogTimerEnabled()){
            if(memory.getWatchDogTimer() > 255){
                memory.setWatchDogTimer(0);
                memory.setSleepMode(false);
            }
            memory.setWatchDogTimer(memory.getWatchDogTimer() + 1);
        }

*/
        if(memory.isWatchDogTimerEnabled())
            startWatchDog();
       // System.out.println("WDT enabled: " + memory.isWatchDogTimerEnabled() + " WDT Value: " + memory.getWatchDogTimer());
        return false;


    }

    /**
     * Startet den Watchdog.
     * Dieser hat ohne Vorteiler ca 18ms bis er überläuft
     *
     */
    public void startWatchDog() {
        Thread thWDT = new Thread(() -> {
            try {
                while (memory.isWatchDogTimerEnabled()) {
                    if (memory.getWatchDogTimer() > 18000) {
                        memory.setSleepMode(false);
                        memory.setWatchDogTimer(0);
                        System.out.println("WatchDogTimer overflow. RESET!");
                        clearBit(Const.STATUS, 4);
                        break;
                    }

                    memory.setWatchDogTimer(memory.getWatchDogTimer() + 1);
                    System.out.println("WDT: " + memory.getWatchDogTimer() + ", Runtime: " + runtime);
                    runtime++;
                    Platform.runLater(() -> updateUI());
                    // Thread.sleep ist ungenau
                    Thread.sleep(Integer.parseInt(textFieldSpeed.getText())* 100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thWDT.setDaemon(true);
        thWDT.start();
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
        isRunning = false;
        currentRow = 0;
        runtime = 0;
        tableFileContent.getItems().clear();
        memory.initializeMemory();
        initialize();
    }

    public void pause(ActionEvent actionEvent) {
        if(isRunning == true)
            isRunning=false;
        else
            isRunning = true;

        memory.setWatchDogTimerEnabled(false);
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

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setClockSpeed(){
        clockSpeed = Double.parseDouble(textFieldClockSpeed.getText());

        String clockSpeedStr = textFieldClockSpeed.getText() + " MHz";
        textClockSpeed.setText(clockSpeedStr);
    }

    public int getBit(int b, int position)
    {
        return ((b >> position) & 1);
    }

    public void toggleBit(int bitPosition, int address){
        int byteValue = memory.getAbsoluteAddress(address);

        // Wenn das Bit an der Stelle bitPosition 0 ist, dann... sonst...
        if(getBit(byteValue, bitPosition) == 0){
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
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA1() {
        toggleBit(1, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 1) == 0)
        {
            textFieldRegisterA1.setText("0");
        } else {
            textFieldRegisterA1.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA2() {
        toggleBit(2, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 2) == 0)
        {
            textFieldRegisterA2.setText("0");
        } else {
            textFieldRegisterA2.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA3() {
        toggleBit(3, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 3) == 0)
        {
            textFieldRegisterA3.setText("0");
        } else {
            textFieldRegisterA3.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA4() {
        toggleBit(4, Const.PORTA);

        //TODO: Was ist mit TRISA? -> Prüfen ob RB4 In-/ oder Output ist


        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 4) == 0) {
            getTimer0Service().incrementCounter(false);
            textFieldRegisterA4.setText("0");
        } else {
            getTimer0Service().incrementCounter(true);
            textFieldRegisterA4.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA5() {
        toggleBit(5, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 5) == 0)
        {
            textFieldRegisterA5.setText("0");
        } else {
            textFieldRegisterA5.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA6() {
        toggleBit(6, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 6) == 0)
        {
            textFieldRegisterA6.setText("0");
        } else {
            textFieldRegisterA6.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleA7() {
        toggleBit(7, Const.PORTA);

        if (getBit(memory.getAbsoluteAddress(Const.PORTA), 7) == 0)
        {
            textFieldRegisterA7.setText("0");
        } else {
            textFieldRegisterA7.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    /**
     * Toggled das RB0 Bit
     * Der RB0 Pin löst unter Umständen einen Interrupt aus
     */
    public void toggleB0() {
        toggleBit(0, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 0) == 0)
        {
            textFieldRegisterB0.setText("0");
        } else {
            textFieldRegisterB0.setText("1");
        }
        getCheckForInterruptService().checkForRB0Interrupt();
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB1() {
        toggleBit(1, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 1) == 0)
        {
            textFieldRegisterB1.setText("0");
        } else {
            textFieldRegisterB1.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB2() {
        toggleBit(2, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 2) == 0)
        {
            textFieldRegisterB2.setText("0");
        } else {
            textFieldRegisterB2.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB3() {
        toggleBit(3, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 3) == 0)
        {
            textFieldRegisterB3.setText("0");
        } else {
            textFieldRegisterB3.setText("1");
        }

        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB4() {
        toggleBit(4, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 4) == 0)
        {
            textFieldRegisterB4.setText("0");
        } else {
            textFieldRegisterB4.setText("1");
        }

        getCheckForInterruptService().checkForPortBInterrupt();
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB5() {
        toggleBit(5, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 5) == 0)
        {
            textFieldRegisterB5.setText("0");
        } else {
            textFieldRegisterB5.setText("1");
        }

        getCheckForInterruptService().checkForPortBInterrupt();
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB6() {
        toggleBit(6, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 6) == 0)
        {
            textFieldRegisterB6.setText("0");
        } else {
            textFieldRegisterB6.setText("1");
        }

        getCheckForInterruptService().checkForPortBInterrupt();
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleB7() {
        toggleBit(7, Const.PORTB);

        if (getBit(memory.getAbsoluteAddress(Const.PORTB), 7) == 0)
        {
            textFieldRegisterB7.setText("0");
        } else {
            textFieldRegisterB7.setText("1");
        }

        getCheckForInterruptService().checkForPortBInterrupt();
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA0() {
        toggleBit(0, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 0) == 0)
        {
            textFieldTRISA0.setText("o");
        } else {
            textFieldTRISA0.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA1() {
        toggleBit(1, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 1) == 0)
        {
            textFieldTRISA1.setText("o");
        } else {
            textFieldTRISA1.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA2() {
        toggleBit(2, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 2) == 0)
        {
            textFieldTRISA2.setText("o");
        } else {
            textFieldTRISA2.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA3() {
        toggleBit(3, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 3) == 0)
        {
            textFieldTRISA3.setText("o");
        } else {
            textFieldTRISA3.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA4() {
        toggleBit(4, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 4) == 0)
        {
            textFieldTRISA4.setText("o");
        } else {
            textFieldTRISA4.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA5() {
        toggleBit(5, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 5) == 0)
        {
            textFieldTRISA5.setText("o");
        } else {
            textFieldTRISA5.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA6() {
        toggleBit(6, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 6) == 0)
        {
            textFieldTRISA6.setText("o");
        } else {
            textFieldTRISA6.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISA7() {
        toggleBit(7, Const.TRISA);

        if (getBit(memory.getAbsoluteAddress(Const.TRISA), 7) == 0)
        {
            textFieldTRISA7.setText("o");
        } else {
            textFieldTRISA7.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB0() {
        toggleBit(0, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 0) == 0)
        {
            textFieldTRISB0.setText("o");
        } else {
            textFieldTRISB0.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB1() {
        toggleBit(1, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 1) == 0)
        {
            textFieldTRISB1.setText("o");
        } else {
            textFieldTRISB1.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB2() {
        toggleBit(2, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 2) == 0)
        {
            textFieldTRISB2.setText("o");
        } else {
            textFieldTRISB2.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB3() {
        toggleBit(3, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 3) == 0)
        {
            textFieldTRISB3.setText("o");
        } else {
            textFieldTRISB3.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB4() {
        toggleBit(4, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 4) == 0)
        {
            textFieldTRISB4.setText("o");
        } else {
            textFieldTRISB4.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB5() {
        toggleBit(5, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 5) == 0)
        {
            textFieldTRISB5.setText("o");
        } else {
            textFieldTRISB5.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB6() {
        toggleBit(6, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 6) == 0)
        {
            textFieldTRISB6.setText("o");
        } else {
            textFieldTRISB6.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleTRISB7() {
        toggleBit(7, Const.TRISB);

        if (getBit(memory.getAbsoluteAddress(Const.TRISB), 7) == 0)
        {
            textFieldTRISB7.setText("o");
        } else {
            textFieldTRISB7.setText("i");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON0() {
        toggleBit(0, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 0) == 0)
        {
            textFieldIntconReg0RBIF.setText("0");
        } else {
            textFieldIntconReg0RBIF.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON1() {
        toggleBit(1, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 1) == 0)
        {
            textFieldIntconReg1INTF.setText("0");
        } else {
            textFieldIntconReg1INTF.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON2() {
        toggleBit(2, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 2) == 0)
        {
            textFieldIntconReg2T0IF.setText("0");
        } else {
            textFieldIntconReg2T0IF.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON3() {
        toggleBit(3, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 3) == 0)
        {
            textFieldIntconReg3RBIE.setText("0");
        } else {
            textFieldIntconReg3RBIE.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON4() {
        toggleBit(4, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 4) == 0)
        {
            textFieldIntconReg4INTE.setText("0");
        } else {
            textFieldIntconReg4INTE.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON5() {
        toggleBit(5, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 5) == 0)
        {
            textFieldIntconReg5T0IE.setText("0");
        } else {
            textFieldIntconReg5T0IE.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON6() {
        toggleBit(6, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 6) == 0)
        {
            textFieldIntconReg6EEIE.setText("0");
        } else {
            textFieldIntconReg6EEIE.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void toggleINTCON7() {
        toggleBit(7, Const.INTCON);

        if (getBit(memory.getAbsoluteAddress(Const.INTCON), 7) == 0)
        {
            textFieldIntconReg7GIE.setText("0");
        } else {
            textFieldIntconReg7GIE.setText("1");
        }
        Platform.runLater(()->updateMemoryView());
    }

    public void openDocumentation(ActionEvent actionEvent) {
            /* build up command and launch */
        String command = "";
        String file = "https://github.com/IsThisTheFuture/PIC16F84SIM/blob/master/TPicSim/Projekt_Simulator.pdf";
        if (isLinux()) {
            command = "xdg-open " + file;
        } else {
            command = "cmd /C start " + file;
        }

        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSpeed(ActionEvent actionEvent){
        this.speed = Integer.parseInt(textFieldSpeed.getText());
    }

    public void toggleWatchDogTimer(){
        memory.setWatchDogTimerEnabled(!memory.isWatchDogTimerEnabled());
        updateUI();
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

    private StackViewService getStackViewService() {
        if (stackViewService == null) {
            stackViewService = new  StackViewService();
        }
        return stackViewService;
    }

    public void toggleTaktGenerator() {
        taktgeneratorEnabled = !taktgeneratorEnabled;

        if (!taktgeneratorEnabled) {
            updateUI();
        }

        Thread thTakt = new Thread(() -> {
            try {
                while (taktgeneratorEnabled) {

                    if (comboboxTaktgenerator.getValue().equals("RA0")) toggleA0();
                    else if (comboboxTaktgenerator.getValue().equals("RA1")) Platform.runLater(() -> toggleA1());//toggleA1();
                    else if (comboboxTaktgenerator.getValue().equals("RA2")) toggleA2();
                    else if (comboboxTaktgenerator.getValue().equals("RA3")) toggleA3();
                    else if (comboboxTaktgenerator.getValue().equals("RA4")) toggleA4();
                    else if (comboboxTaktgenerator.getValue().equals("RA5")) toggleA5();
                    else if (comboboxTaktgenerator.getValue().equals("RA6")) toggleA6();
                    else if (comboboxTaktgenerator.getValue().equals("RA7")) toggleA7();
                    else if (comboboxTaktgenerator.getValue().equals("RB0")) toggleB0();
                    else if (comboboxTaktgenerator.getValue().equals("RB1")) toggleB1();
                    else if (comboboxTaktgenerator.getValue().equals("RB2")) toggleB2();
                    else if (comboboxTaktgenerator.getValue().equals("RB3")) toggleB3();
                    else if (comboboxTaktgenerator.getValue().equals("RB4")) toggleB4();
                    else if (comboboxTaktgenerator.getValue().equals("RB5")) toggleB5();
                    else if (comboboxTaktgenerator.getValue().equals("RB6")) toggleB6();
                    else if (comboboxTaktgenerator.getValue().equals("RB7")) toggleB7();

                    //Platform.runLater(() -> updateMemoryView());

                    if(!taktGenFrequenz.getText().equals(""))
                         sleepTime = (1 / (Double.parseDouble(taktGenFrequenz.getText())) * 1000);

                    //System.out.println(sleepTime + "ms");
                    Thread.sleep((long) sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thTakt.setDaemon(true);
        thTakt.start();
    }

    /**
     * Löst einen MCLR Reset aus und belegt einige Register mit Werten
     * Es wird unterschieden zwischen MCLR Reset im Normalbetrieb und im SLEEP Modus
     *
     * Status wenn Sleep:          0001 0uuu
     * Status wenn Normalbetrieb:  000u uuuu
     */
    @SuppressWarnings("Duplicates")
    public void toggleMCLRReset(ActionEvent actionEvent){
        if(memory.isSleepMode()){
        clearBit(Const.STATUS, 7);
        clearBit(Const.STATUS, 6);
        clearBit(Const.STATUS, 5);
        setBit(Const.STATUS, 4);
        clearBit(Const.STATUS, 3);

        memory.setPc(0);
        memory.setAbsoluteAddress(Const.PCL, 0);

        clearBit(Const.INTCON, 7);
        clearBit(Const.INTCON, 6);
        clearBit(Const.INTCON, 5);
        clearBit(Const.INTCON, 4);
        clearBit(Const.INTCON, 3);
        clearBit(Const.INTCON, 2);
        clearBit(Const.INTCON, 1);

        memory.setAbsoluteAddress(Const.OPTION_REG, 255);

        setBit(Const.TRISA, 4);
        setBit(Const.TRISA, 3);
        setBit(Const.TRISA, 2);
        setBit(Const.TRISA, 1);
        setBit(Const.TRISA, 0);

        setBit(Const.TRISB, 7);
        setBit(Const.TRISB, 6);
        setBit(Const.TRISB, 5);
        setBit(Const.TRISB, 4);
        setBit(Const.TRISB, 3);
        setBit(Const.TRISB, 2);
        setBit(Const.TRISB, 1);
        setBit(Const.TRISB, 0);

        //TODO EECON1: ---0 q000
        } else {
            clearBit(Const.STATUS, 7);
            clearBit(Const.STATUS, 6);
            clearBit(Const.STATUS, 5);

            memory.setPc(0);
            memory.setAbsoluteAddress(Const.PCL, 0);

            clearBit(Const.INTCON, 7);
            clearBit(Const.INTCON, 6);
            clearBit(Const.INTCON, 5);
            clearBit(Const.INTCON, 4);
            clearBit(Const.INTCON, 3);
            clearBit(Const.INTCON, 2);
            clearBit(Const.INTCON, 1);

            memory.setAbsoluteAddress(Const.OPTION_REG, 255);

            setBit(Const.TRISA, 4);
            setBit(Const.TRISA, 3);
            setBit(Const.TRISA, 2);
            setBit(Const.TRISA, 1);
            setBit(Const.TRISA, 0);

            setBit(Const.TRISB, 7);
            setBit(Const.TRISB, 6);
            setBit(Const.TRISB, 5);
            setBit(Const.TRISB, 4);
            setBit(Const.TRISB, 3);
            setBit(Const.TRISB, 2);
            setBit(Const.TRISB, 1);
            setBit(Const.TRISB, 0);

        }

        Platform.runLater(() -> updateUI());
    }

    /**
     * Setzt die das Bit 'bitPosition' and Speicheradresse 'address' auf den Wert 1
     */
    private void setBit(int address, int bitPosition){
        int byteValue = memory.getAbsoluteAddress(address);
        byteValue = (byteValue | (1 << (bitPosition)));
        memory.setAbsoluteAddress(address, byteValue);
    }
    /**
     * Setzt die das Bit 'bitPosition' and Speicheradresse 'address' auf den Wert 0
     */
    private void clearBit(int address, int bitPosition){
        int byteValue = memory.getAbsoluteAddress(address);
        byteValue = (byteValue & ~(1 << (bitPosition)));
        memory.setAbsoluteAddress(address, byteValue);
    }

}