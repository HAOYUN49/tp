package seedu.nuke.command;

import static seedu.nuke.util.Message.MESSAGE_EXIT;

public class ExitCommand extends TaskCommand {
    public static final String COMMAND_WORD = "bye";
    public static final String FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Exit from Nuke"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    private static boolean isExit; // To check if user requests to exit the program

    public ExitCommand() {
        isExit = false;
    }

    /**
     * Executes the <b>Exit Command</b> to exit the <b>Nuke</b> program.
     *
     * @return The <b>Command Result</b> of the execution
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        isExit = true;
        return new CommandResult(MESSAGE_EXIT);
    }

    /**
     * Checks if the user has requested to exit the program (after confirmation if necessary).
     *
     * @return <code>TRUE</code> if an exit is requested, and <code>FALSE</code> otherwise
     */
    public static boolean isExit() {
        return isExit; // Returns true if command is exit command
    }
}
