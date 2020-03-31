package seedu.nuke.gui.util;

import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import seedu.nuke.command.ChangeDirectoryCommand;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.command.HelpCommand;
import seedu.nuke.command.OpenFileCommand;
import seedu.nuke.command.UndoCommand;
import seedu.nuke.command.addcommand.AddCategoryCommand;
import seedu.nuke.command.addcommand.AddFileCommand;
import seedu.nuke.command.addcommand.AddModuleCommand;
import seedu.nuke.command.addcommand.AddTagCommand;
import seedu.nuke.command.addcommand.AddTaskCommand;
import seedu.nuke.command.editcommand.EditCategoryCommand;
import seedu.nuke.command.editcommand.EditFileCommand;
import seedu.nuke.command.editcommand.EditModuleCommand;
import seedu.nuke.command.editcommand.EditTaskCommand;
import seedu.nuke.command.editcommand.MarkAsDoneCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteCategoryCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteFileCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteModuleCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTaskCommand;
import seedu.nuke.command.filtercommand.listcommand.ListAllTasksDeadlineCommand;
import seedu.nuke.command.filtercommand.listcommand.ListCategoryCommand;
import seedu.nuke.command.filtercommand.listcommand.ListFileCommand;
import seedu.nuke.command.filtercommand.listcommand.ListModuleCommand;
import seedu.nuke.command.filtercommand.listcommand.ListModuleTasksDeadlineCommand;
import seedu.nuke.command.filtercommand.listcommand.ListTaskCommand;

import static seedu.nuke.gui.io.GuiParser.ALL_FLAG;
import static seedu.nuke.gui.io.GuiParser.CATEGORY_PREFIX;
import static seedu.nuke.gui.io.GuiParser.DEADLINE_PREFIX;
import static seedu.nuke.gui.io.GuiParser.EXACT_FLAG;
import static seedu.nuke.gui.io.GuiParser.FILE_PREFIX;
import static seedu.nuke.gui.io.GuiParser.MODULE_PREFIX;
import static seedu.nuke.gui.io.GuiParser.PRIORITY_PREFIX;
import static seedu.nuke.gui.io.GuiParser.TASK_PREFIX;
import static seedu.nuke.gui.util.TextUtil.createText;

public class CommandFormatText {
    private static TextFlow commandFormat = new TextFlow();

    /**
     * Returns the command format hint to show the user as a TextFlow. The text is highlighted with various colors
     * to allow user to recognise and differentiate compulsory and optional attributes.
     *
     * @param commandWord
     *  The command word corresponding to the format to be shown
     * @return
     *  The command format hint
     */
    public static TextFlow getCommandFormat(String commandWord) {
        commandFormat.getChildren().clear();
        commandFormat.setStyle("-fx-font-size: 12pt");
        switch (commandWord) {
        case AddModuleCommand.COMMAND_WORD:
            return getAddModuleFormat();
        case AddCategoryCommand.COMMAND_WORD:
            return getAddCategoryFormat();
        case AddTaskCommand.COMMAND_WORD:
            return getAddTaskFormat();
        case AddFileCommand.COMMAND_WORD:
            return getAddFileFormat();
        case AddTagCommand.COMMAND_WORD:
            return getAddTagFormat();

        case DeleteModuleCommand.COMMAND_WORD:
            return getDeleteModuleFormat();
        case DeleteCategoryCommand.COMMAND_WORD:
            return getDeleteCategoryFormat();
        case DeleteTaskCommand.COMMAND_WORD:
            return getDeleteTaskFormat();
        case DeleteFileCommand.COMMAND_WORD:
            return getDeleteFileFormat();

        case ListModuleCommand.COMMAND_WORD:
            return getListModuleFormat();
        case ListCategoryCommand.COMMAND_WORD:
            return getListCategoryFormat();
        case ListTaskCommand.COMMAND_WORD:
            return getListTaskFormat();
        case ListFileCommand.COMMAND_WORD:
            return getListFileFormat();
        case ListAllTasksDeadlineCommand.COMMAND_WORD:
            return getListTaskDeadlineFormat();
        case ListModuleTasksDeadlineCommand.COMMAND_WORD:
            return getListModuleTaskDeadlineFormat();

        case EditModuleCommand.COMMAND_WORD:
            return getEditModuleFormat();
        case EditCategoryCommand.COMMAND_WORD:
            return getEditCategoryFormat();
        case EditTaskCommand.COMMAND_WORD:
            return getEditTaskFormat();
        case EditFileCommand.COMMAND_WORD:
            return getEditFileFormat();
        case MarkAsDoneCommand.COMMAND_WORD:
            return getDoneFormat();

        case ChangeDirectoryCommand.COMMAND_WORD:
            return getChangeDirectoryFormat();

        case OpenFileCommand.COMMAND_WORD:
            return getOpenFileFormat();

        case UndoCommand.COMMAND_WORD:
            return getUndoFormat();

        case ExitCommand.COMMAND_WORD:
            return getExitFormat();

        case HelpCommand.COMMAND_WORD:
            return getHelpFormat();

        default:
            String invalidString = "Invalid Command Word!";
            commandFormat.getChildren().add(createText(invalidString, Color.CRIMSON));
            return commandFormat;
        }
    }

    private static TextFlow getAddModuleFormat() {
        commandFormat.getChildren().addAll(
                createText(AddModuleCommand.COMMAND_WORD, Color.GREEN),
                createText(" <module code>", Color.BLUE)
        );
        return commandFormat;
    }

    private static TextFlow getAddCategoryFormat() {
        commandFormat.getChildren().addAll(
                createText(AddCategoryCommand.COMMAND_WORD, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code>", Color.BLUE),
                createText(String.format(" %s %s %s %s", "[", PRIORITY_PREFIX, "<priority>", "]"), Color.DARKGRAY)

        );
        return commandFormat;
    }

    private static TextFlow getAddTaskFormat() {
        commandFormat.getChildren().addAll(
                createText(AddTaskCommand.COMMAND_WORD, Color.GREEN),
                createText(" <task description> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name>", Color.BLUE),
                createText(String.format(" %s %s %s %s %s %s", "[", DEADLINE_PREFIX, "<deadline>",
                        PRIORITY_PREFIX, "<priority>", "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getAddFileFormat() {
        commandFormat.getChildren().addAll(
                createText(AddFileCommand.COMMAND_WORD, Color.GREEN),
                createText(" <file name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(TASK_PREFIX, Color.GREEN),
                createText(" <task description> ", Color.BLUE),
                createText(FILE_PREFIX, Color.GREEN),
                createText(" <file path>", Color.BLUE)
        );
        return commandFormat;
    }

    private static TextFlow getAddTagFormat() {
        commandFormat.getChildren().addAll(
                createText(AddTagCommand.COMMAND_WORD, Color.GREEN),
                createText(" <tag name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(TASK_PREFIX, Color.GREEN),
                createText(" <task description> ", Color.BLUE)
        );
        return commandFormat;
    }


    private static TextFlow getDeleteModuleFormat() {
        commandFormat.getChildren().addAll(
                createText(DeleteModuleCommand.COMMAND_WORD, Color.GREEN),
                createText(" <module code>", Color.BLUE),
                createText(String.format(" %s %s %s %s", "[", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getDeleteCategoryFormat() {
        commandFormat.getChildren().addAll(
                createText(DeleteCategoryCommand.COMMAND_WORD, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code>", Color.BLUE),
                createText(String.format(" %s %s %s %s", "[", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getDeleteTaskFormat() {
        commandFormat.getChildren().addAll(
                createText(DeleteTaskCommand.COMMAND_WORD, Color.GREEN),
                createText(" <task description> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name>", Color.BLUE),
                createText(String.format(" %s %s %s %s", "[", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getDeleteFileFormat() {
        commandFormat.getChildren().addAll(
                createText(DeleteFileCommand.COMMAND_WORD, Color.GREEN),
                createText(" <file name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(TASK_PREFIX, Color.GREEN),
                createText(" <task description>", Color.BLUE),
                createText(String.format(" %s %s %s %s", "[", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getListModuleFormat() {
        commandFormat.getChildren().addAll(
                createText(ListModuleCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s %s %s", "[", "<module keyword>",
                        EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getListCategoryFormat() {
        commandFormat.getChildren().addAll(
                createText(ListCategoryCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s %s %s %s %s", "[", "<category keyword>",
                        MODULE_PREFIX, "<module keyword>", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getListTaskFormat() {
        commandFormat.getChildren().addAll(
                createText(ListTaskCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s %s %s %s %s %s %s", "[", "<task keyword>",
                        MODULE_PREFIX, "<module keyword>",
                        CATEGORY_PREFIX, "<category keyword>", EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getListFileFormat() {
        commandFormat.getChildren().addAll(
                createText(ListFileCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s %s %s %s %s %s %s %s %s",
                        "[", "<file keyword>", MODULE_PREFIX, "<module keyword>",
                        CATEGORY_PREFIX, "<category keyword>", TASK_PREFIX, "<task keyword>",
                        EXACT_FLAG, ALL_FLAG, "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getListTaskDeadlineFormat() {
        commandFormat.getChildren().add(
                createText(ListAllTasksDeadlineCommand.COMMAND_WORD, Color.GREEN)
        );
        return commandFormat;
    }

    private static TextFlow getListModuleTaskDeadlineFormat() {
        commandFormat.getChildren().addAll(
                createText(ListModuleTasksDeadlineCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s", "[", "<module code>", "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getEditModuleFormat() {
        commandFormat.getChildren().addAll(
                createText(EditModuleCommand.COMMAND_WORD, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <new module code> ", Color.BLUE)
        );
        return commandFormat;
    }

    private static TextFlow getEditCategoryFormat() {
        commandFormat.getChildren().addAll(
                createText(EditCategoryCommand.COMMAND_WORD, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code>", Color.BLUE),
                createText(String.format(" %s %s %s %s %s %s", "[", CATEGORY_PREFIX, "<new category name>",
                        PRIORITY_PREFIX, "<new priority>", "]"), Color.DARKGRAY)

        );
        return commandFormat;
    }

    private static TextFlow getEditTaskFormat() {
        commandFormat.getChildren().addAll(
                createText(EditTaskCommand.COMMAND_WORD, Color.GREEN),
                createText(" <task description> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name>", Color.BLUE),
                createText(String.format(" %s %s %s %s %s %s %s %s", "[",
                        TASK_PREFIX, "<new task description>", DEADLINE_PREFIX, "<new deadline>",
                        PRIORITY_PREFIX, "<new priority>", "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getEditFileFormat() {
        commandFormat.getChildren().addAll(
                createText(EditFileCommand.COMMAND_WORD, Color.GREEN),
                createText(" <file name> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(TASK_PREFIX, Color.GREEN),
                createText(" <task description>", Color.BLUE)
        );
        return commandFormat;
    }

    private static TextFlow getDoneFormat() {
        commandFormat.getChildren().addAll(
                createText(MarkAsDoneCommand.COMMAND_WORD, Color.GREEN),
                createText(" <task description> ", Color.BLUE),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE)
        );
        return commandFormat;
    }

    private static TextFlow getChangeDirectoryFormat() {
        commandFormat.getChildren().addAll(
                createText(ChangeDirectoryCommand.COMMAND_WORD, Color.GREEN),
                createText(" <next directory name>", Color.BLUE),
                createText(" to traverse down; ", Color.DARKGRAY),
                createText(ChangeDirectoryCommand.COMMAND_WORD, Color.GREEN),
                createText("..", Color.BLUE),
                createText(" to traverse up", Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getOpenFileFormat() {
        commandFormat.getChildren().addAll(
                createText(OpenFileCommand.COMMAND_WORD, Color.GREEN),
                createText(" [ <file name> ] ", Color.DARKGRAY),
                createText(MODULE_PREFIX, Color.GREEN),
                createText(" <module code> ", Color.BLUE),
                createText(CATEGORY_PREFIX, Color.GREEN),
                createText(" <category name> ", Color.BLUE),
                createText(TASK_PREFIX, Color.GREEN),
                createText(" <task description>", Color.BLUE)
        );
        return commandFormat;
    }


    private static TextFlow getUndoFormat() {
        commandFormat.getChildren().addAll(
                createText(UndoCommand.COMMAND_WORD, Color.GREEN)
        );
        return commandFormat;
    }

    private static TextFlow getExitFormat() {
        commandFormat.getChildren().addAll(
                createText(ExitCommand.COMMAND_WORD, Color.GREEN)
        );
        return commandFormat;
    }

    private static TextFlow getHelpFormat() {
        commandFormat.getChildren().addAll(
                createText(HelpCommand.COMMAND_WORD, Color.GREEN),
                createText(String.format(" %s %s %s", "[", "<command word>", "]"), Color.DARKGRAY)
        );
        return commandFormat;
    }

    private static TextFlow getMakeDirectoryFormat() {
        commandFormat.getChildren().addAll(
                createText("mkdir", Color.GREEN)
        );
        return commandFormat;
    }

    private static TextFlow getListFormat() {
        commandFormat.getChildren().addAll(
                createText("ls", Color.GREEN)
        );
        return commandFormat;
    }
}
