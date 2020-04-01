package seedu.nuke.gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.data.storage.StoragePath;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.gui.component.AutoCompleteTextField;
import seedu.nuke.gui.component.DailyTaskCounter;
import seedu.nuke.gui.component.DirectoryTree;
import seedu.nuke.gui.component.SyntaxConsole;
import seedu.nuke.gui.io.GuiExecutor;
import seedu.nuke.gui.io.GuiParser;
import seedu.nuke.gui.util.TextUtil;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;

import static seedu.nuke.util.Message.DIVIDER;
import static seedu.nuke.util.Message.MESSAGE_LOGO;
import static seedu.nuke.util.Message.MESSAGE_WELCOME_1;
import static seedu.nuke.util.Message.MESSAGE_WELCOME_2;


public class MainController implements Initializable {

    /* Daily Task Counter Panel components */
    public Label overdueTaskCount;
    public VBox mondayBox;
    public Label mondayTaskCount;
    public Label mondayDate;
    public VBox tuesdayBox;
    public Label tuesdayTaskCount;
    public Label tuesdayDate;
    public VBox wednesdayBox;
    public Label wednesdayTaskCount;
    public Label wednesdayDate;
    public VBox thursdayBox;
    public Label thursdayTaskCount;
    public Label thursdayDate;
    public VBox fridayBox;
    public Label fridayTaskCount;
    public Label fridayDate;
    public VBox saturdayBox;
    public Label saturdayTaskCount;
    public Label saturdayDate;
    public VBox sundayBox;
    public Label sundayTaskCount;
    public Label sundayDate;

    /* Directory Tree components */
    @FXML
    private VBox directoryBox;
    private DirectoryTree directoryTree;

    /* Console components */
    @FXML
    private VBox consoleBox;
    private SyntaxConsole syntaxConsole;
    private AutoCompleteTextField console;
    @FXML
    private Label directoryPathLabel;

    /* Screen components */
    @FXML
    private TextFlow consoleScreen;
    @FXML
    private ScrollPane consoleScreenScrollPane;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Auto scroll-down
        consoleScreenScrollPane.vvalueProperty().bind(consoleScreen.heightProperty());
        consoleScreen.setStyle("-fx-font-family: Consolas; -fx-font-size: 12pt");

        directoryTree = new DirectoryTree();
        syntaxConsole = new SyntaxConsole();
        console = new AutoCompleteTextField();
        directoryPathLabel.setTextFill(Color.BLACK);

        directoryBox.getChildren().add(directoryTree);
        directoryTree.prefHeightProperty().bind(directoryBox.heightProperty());

        console.textProperty().addListener((observable, oldValue, newValue) -> {
            String currentUserInput = console.getText();
            syntaxConsole.getChildren().clear();
            syntaxConsole.getChildren().add(new GuiParser(console).smartParse(currentUserInput));
        });

        console.addEventFilter(KeyEvent.ANY, this::onKeyType);
        console.setOnAction(this::onSubmitInput);

        consoleBox.getChildren().addAll(syntaxConsole, console);

        refreshScene();
        welcomeUser();

        // Set focus on console on start
        Platform.runLater(() -> {
            console.requestFocus();
        });
    }


    private void welcomeUser() {
        Text logo = TextUtil.createText(MESSAGE_LOGO, Color.MAGENTA);
        Text welcomeMessage = TextUtil.createText(String.format("%s\n%s\n\n",
                MESSAGE_WELCOME_1, MESSAGE_WELCOME_2), Color.BLUE);
        Text divider = TextUtil.createText(DIVIDER + "\n");
        consoleScreen.getChildren().addAll(logo, divider, welcomeMessage);
    }

    private void onSubmitInput(ActionEvent actionEvent) {
        String userInput = console.getText().trim();
        new GuiExecutor(consoleScreen).executeAction(userInput);
        new StorageManager(StoragePath.SAVE_PATH).saveList();
        refreshScene();
    }


    /**
     * Handles the event when the user types on the console.
     *
     * @param keyEvent
     *  The key type / press event
     */
    public void onKeyType(KeyEvent keyEvent) {
        // Remove unnecessary action
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            keyEvent.consume();
            return;
        }

        switch (keyEvent.getCode()) {
        case UP:
            keyEvent.consume();
            System.out.println("View last command.");
            break;
        case DOWN:
            System.out.println("View earlier command.");
            break;
        case TAB:
            keyEvent.consume();
            syntaxConsole.showCommandFormat(console.getText());
            break;
        default:
            break;
        }
    }

    private void refreshScene() {
        refreshTaskCounterPanel();
        directoryTree.refresh();
        directoryPathLabel.setText(DirectoryTraverser.getFullPath());
        console.clear();
    }

    private void refreshTaskCounterPanel() {
        new DailyTaskCounter(overdueTaskCount).setOverdueTaskCount();
        new DailyTaskCounter(mondayBox, mondayTaskCount, mondayDate, DayOfWeek.MONDAY).setDailyTaskCount();
        new DailyTaskCounter(tuesdayBox, tuesdayTaskCount, tuesdayDate, DayOfWeek.TUESDAY).setDailyTaskCount();
        new DailyTaskCounter(wednesdayBox, wednesdayTaskCount, wednesdayDate, DayOfWeek.WEDNESDAY).setDailyTaskCount();
        new DailyTaskCounter(thursdayBox, thursdayTaskCount, thursdayDate, DayOfWeek.THURSDAY).setDailyTaskCount();
        new DailyTaskCounter(fridayBox, fridayTaskCount, fridayDate, DayOfWeek.FRIDAY).setDailyTaskCount();
        new DailyTaskCounter(saturdayBox, saturdayTaskCount, saturdayDate, DayOfWeek.SATURDAY).setDailyTaskCount();
        new DailyTaskCounter(sundayBox, sundayTaskCount, sundayDate, DayOfWeek.SUNDAY).setDailyTaskCount();
    }
}
