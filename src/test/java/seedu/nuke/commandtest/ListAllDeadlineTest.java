package seedu.nuke.commandtest;

import org.junit.jupiter.api.Test;
import seedu.nuke.Executor;
import seedu.nuke.Nuke;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.filtercommand.listcommand.ListAllTasksDeadlineCommand;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.parser.Parser;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nuke.util.Message.MESSAGE_NO_TASK_IN_LIST;

/**
 * Junit test class to test ListAllTasksDeadlineCommand.
 */
public class ListAllDeadlineTest {

    @Test
    public void testTasksCounting() {
        Nuke nuke;
        //try {
        //    nuke = new Nuke();
        //    command = new ListAllTasksDeadlineCommand();
        //} catch (FileNotFoundException e) {
        //    System.out.println(e.getMessage());
        //    return;
        //}
        Command command = new ListAllTasksDeadlineCommand();
        CommandResult result = Executor.execute(command);
        if (result.getDirectoryLevel() == DirectoryLevel.TASK) {
            assertEquals(ModuleManager.countAllTasks(), result.getShownList().size());
        } else {
            assertEquals(0, ModuleManager.countAllTasks());
        }

    }

    @Test
    public void testEmptyTaskList() {
        Nuke nuke;
        //try {
        //    nuke = new Nuke();
        //} catch (FileNotFoundException e) {
        //    System.out.println(e.getMessage());
        //    return;
        //}
        ModuleManager.getInstance(null, null);
        Command command = new Parser().parseCommand(ListAllTasksDeadlineCommand.COMMAND_WORD);
        CommandResult result = Executor.execute(command);
        if (ModuleManager.countAllTasks() == 0) {
            assertEquals(MESSAGE_NO_TASK_IN_LIST, result.getFeedbackToUser());
            //assertEquals(MESSAGE_NO_TASK_IN_LIST, new ListAllTasksDeadlineCommand().execute().getFeedbackToUser());
        }
    }
}
