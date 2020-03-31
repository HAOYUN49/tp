package seedu.nuke.gui.component;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import seedu.nuke.command.ChangeDirectoryCommand;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.command.HelpCommand;
import seedu.nuke.command.addcommand.AddCategoryCommand;
import seedu.nuke.command.addcommand.AddModuleCommand;
import seedu.nuke.command.addcommand.AddTaskCommand;
import seedu.nuke.command.editcommand.EditCategoryCommand;
import seedu.nuke.command.editcommand.EditModuleCommand;
import seedu.nuke.command.editcommand.EditTaskCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteCategoryCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteModuleCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTaskCommand;
import seedu.nuke.command.filtercommand.listcommand.ListCategoryCommand;
import seedu.nuke.command.filtercommand.listcommand.ListModuleCommand;
import seedu.nuke.command.filtercommand.listcommand.ListTaskCommand;
import seedu.nuke.gui.util.CommandFormatText;
import seedu.nuke.gui.util.TextUtil;

public class SyntaxConsole extends TextFlow {

    public SyntaxConsole() {
        super();
        customiseStyle();
    }

    private void customiseStyle() {
        this.setStyle("-fx-font-family: Consolas; -fx-font-size: 12pt; -fx-background-color: aliceblue;"
                + "-fx-border-color: lightgrey; -fx-border-radius: 3");
        this.setMinHeight(30);
        this.setPadding(new Insets(5));
    }

    private String getCommandWord(String input) {
        String whiteSpace = "\\s+";
        return input.trim().split(whiteSpace)[0]; // Get the first word in the input
    }

    /**
     * Shows the command format hint to the user based on the command word entered by the user.
     *
     * @param input
     *  The current input typed in the console.
     */
    public void showCommandFormat(String input) {
        this.getChildren().clear();
        String commandWord = getCommandWord(input);
        this.getChildren().add(CommandFormatText.getCommandFormat(commandWord));
    }
}
