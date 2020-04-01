package seedu.nuke.command;

import static seedu.nuke.ui.Ui.commands;
import static seedu.nuke.util.Message.MESSAGE_HELP;

public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";
    public static final String FORMAT = COMMAND_WORD + " [ <command word> ]";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Show helping guides"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    public HelpCommand() {
    }

    @Override
    public CommandResult execute() {


        return new CommandResult(MESSAGE_HELP, commands);
    }
}
