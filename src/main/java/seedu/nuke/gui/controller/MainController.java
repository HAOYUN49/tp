package seedu.nuke.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.module.Module;
import seedu.nuke.parser.Parser;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import static seedu.nuke.util.Message.*;

public class MainController implements Initializable {
    @FXML
    private TextFlow consoleScreen;

    @FXML
    private TextField console;

    @FXML
    private ScrollPane consoleScreenScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        consoleScreen.setStyle("-fx-font-family: Consolas; -fx-font-size: 12pt");
        Text logo = createText(MESSAGE_LOGO, Color.MAGENTA);
        Text welcomeMessage = createText(String.format("%s\n%s\n\n", MESSAGE_WELCOME_1, MESSAGE_WELCOME_2), Color.BLUE);
        Text divider = createText("-".repeat(80) + "\n");

        consoleScreen.getChildren().addAll(logo, divider, welcomeMessage);
    }

    public void submitInput() throws InterruptedException {
        String userInput = console.getText();
        executeCommand(userInput);
        console.clear();
    }



    /* Non-controller methods */
    private Text createText(String input) {
        return new Text(input);
    }

    private Text createText(String input, Color color) {
        Text text = new Text(input);
        text.setFill(color);
        return text;
    }

    private void executeCommand(String userInput) throws InterruptedException {
        Command command = new Parser().parseCommand(userInput);
        CommandResult result = command.execute();
        displayResult(result);

        if (ExitCommand.isExit()) {
            Stage window = (Stage) console.getScene().getWindow();
            window.close();
        }
    }

    private void displayResult(CommandResult result) {
        // Auto scroll-down
        consoleScreenScrollPane.vvalueProperty().bind(consoleScreen.heightProperty());

        Text feedbackToUser = createText(String.format("%s\n\n", result.getFeedbackToUser()), Color.BLUE);
        consoleScreen.getChildren().add(feedbackToUser);

        if (result.isShowTasks()) {
            Text taskList = createText(String.format("%s\n", createModuleList()), Color.BROWN);
            consoleScreen.getChildren().add(taskList);
        }

    }

    private String createModuleList() {
        String DIVIDER = String.format("%s%s%s\n", "+", "-".repeat(78), "+");
        StringBuilder listToShow = new StringBuilder();
        listToShow.append(DIVIDER);
        Iterator var3 = ModuleManager.getModuleList().iterator();

        while (var3.hasNext()) {
            Module module = (Module)var3.next();
            listToShow.append(String.format("%s\n", module.getModuleCode()));
        }

        listToShow.append(DIVIDER);
        return listToShow.toString();
    }
}
