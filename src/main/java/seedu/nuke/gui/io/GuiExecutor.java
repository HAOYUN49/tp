package seedu.nuke.gui.io;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.nuke.Executor;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.gui.util.TextUtil;
import seedu.nuke.util.ListCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuiExecutor {
    private static TextFlow consoleScreen;

    public GuiExecutor(TextFlow consoleScreen) {
        GuiExecutor.consoleScreen = consoleScreen;
    }

    /**
     * Executes the corresponding action based on the command given by the user in the console.
     *
     * @param userInput
     *  The input given by the user
     */
    public void executeAction(String userInput) {
        CommandResult result = Executor.executeCommand(userInput);
        displayResult(result);

        if (ExitCommand.isExit()) {
            Stage window = (Stage) consoleScreen.getScene().getWindow();
            window.close();
        }
    }

    /**
     * Displays the result on the console screen to the user.
     *
     * @param result
     *  The result from executing the command
     */
    private void displayResult(CommandResult result) {
        if (result.getFeedbackToUser().isEmpty()) {
            return;
        }
        Text feedbackToUser = TextUtil.createText(String.format("%s\n\n", result.getFeedbackToUser()), Color.BLUE);
        consoleScreen.getChildren().add(feedbackToUser);

        DirectoryLevel dataType = result.getDirectoryLevel();
        String listTableToShow;
        switch (dataType) {
        case MODULE:
            ArrayList<Module> moduleList = result.getShownList().stream()
                    .map(Module.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createModuleListTable(moduleList);
            break;

        case CATEGORY:
            ArrayList<Category> categoryList = result.getShownList().stream()
                    .map(Category.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createCategoryListTable(categoryList);
            break;

        case TASK:
            ArrayList<Task> taskList = result.getShownList().stream()
                    .map(Task.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createTaskListTable(taskList);
            break;

        case FILE:
            ArrayList<TaskFile> fileList = result.getShownList().stream()
                    .map(TaskFile.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createFileListTable(fileList);
            break;

        default:
            return;
        }

        Text taskListTable = TextUtil.createText(String.format("%s\n\n", listTableToShow), Color.DEEPSKYBLUE);
        consoleScreen.getChildren().add(taskListTable);
    }

    /**
     * Opens up the file chooser for the user to select a file to add to the File List.
     *
     * @return
     *  A String array containing the path and name of the selected file
     */
    public static String[] executeFileChooser() {
        Stage stage = (Stage) consoleScreen.getScene().getWindow();
        String[] chosenFileInformation = new String[2];

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File To Add");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            chosenFileInformation[0] = chosenFile.getAbsolutePath();
            chosenFileInformation[1] = chosenFile.getName();
        } else {
            return null;
        }
        return chosenFileInformation;
    }
}
