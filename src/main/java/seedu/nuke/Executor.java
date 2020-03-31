package seedu.nuke;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.promptcommand.PromptType;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Directory;
import seedu.nuke.exception.IncorrectDirectoryLevelException;
import seedu.nuke.parser.Parser;

import java.util.ArrayList;

public class Executor {
    /* Variables to transit between parsing regular commands and prompts */
    private static PromptType promptType = PromptType.NONE;
    private static DirectoryLevel directoryLevel = DirectoryLevel.NONE;
    private static ArrayList<Directory> filteredList;
    private static ArrayList<Integer> indices;

    private static boolean isGui = false;

    /**
     * Prepares the system to prepare a prompt for the user to input indices.
     */
    public static void preparePromptIndices() {
        promptType = PromptType.INDICES;
    }

    /**
     * Prepares the system to prepare a confirmation prompt for the user.
     */
    public static void preparePromptConfirmation() {
        promptType = PromptType.CONFIRMATION;
    }

    /**
     * Prepares the system to terminate prompt mode and revert to normal parsing of commands.
     */
    public static void terminatePrompt() {
        promptType = PromptType.NONE;
    }

    /**
     * Returns the list of indices inputted by the user during a prompt.
     *
     * @return
     *  The list of indices inputted by the user
     */
    public static ArrayList<Integer> getIndices() {
        return indices;
    }

    /**
     * Overwrite with new list of indices inputted by the user.
     *
     * @param indices
     *  The list of indices inputted by the user
     */
    public static void setIndices(ArrayList<Integer> indices) {
        Executor.indices = indices;
    }

    /**
     * Returns the prompt type of the current execution.
     *
     * @return
     *  The prompt type
     */
    public static PromptType getPromptType() {
        return promptType;
    }

    /**
     * Returns the level of the directory (module, category, task, file) in the filtered list of directories that is
     * used during prompt mode.
     *
     * @return
     *  The level of the directories in the list
     */
    public static DirectoryLevel getDirectoryLevel() {
        return directoryLevel;
    }

    /**
     * Returns the filtered list of directories used in prompt mode.
     *
     * @return
     *  The filtered list of directories
     */
    public static ArrayList<Directory> getFilteredList() {
        return filteredList;
    }

    /**
     * Overwrites the current filtered list of directories with a new filtered list.
     *
     * @param filteredList
     *  The list of directories
     * @param directoryLevel
     *  The level of the directories in the list
     */
    public static void setFilteredList(ArrayList<Directory> filteredList, DirectoryLevel directoryLevel) {
        Executor.filteredList = filteredList;
        Executor.directoryLevel = directoryLevel;
    }

    public static boolean isGui() {
        return isGui;
    }

    /**
     * Indicate that the program is running via GUI instead of CLI.
     */
    public static void setIsGui() {
        isGui = true;
    }

    /**
     * Executes command.
     *
     * @param userInput The input from the user to be parsed and executed as a command
     * @return commandResult that contains the execute output information
     */
    public static CommandResult executeCommand(String userInput) {
        Command command;
        switch (promptType) {

        case CONFIRMATION:
            command = new Parser().parseConfirmation(userInput.toLowerCase());
            break;

        case INDICES:
            command = new Parser().parseIndices(userInput);
            break;

        default:
            command = new Parser().parseCommand(userInput);
            break;
        }

        return execute(command);
    }

    public static CommandResult execute(Command command) {
        return command.execute();
    }
}
