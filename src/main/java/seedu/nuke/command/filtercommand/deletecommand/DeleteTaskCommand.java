package seedu.nuke.command.filtercommand.deletecommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.Task;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_TASKS_FOUND;
import static seedu.nuke.util.Message.messageConfirmDeleteTask;
import static seedu.nuke.util.Message.messagePromptDeleteTaskIndices;

/**
 * <h3>Delete Task Command</h3>
 * A <b>Command</b> to delete <b>Task(s)</b> from the <b>Task List</b>.
 * @see Command
 * @see Task
 */
public class DeleteTaskCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "delt";
    public static final String FORMAT = COMMAND_WORD + " <task description> + -m <module code> "
            + "-c <category name> [ -e -a ]";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Delete a task of module"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private boolean isExact;
    private boolean isAll;

    /**
     * Constructs the command to delete a task.
     *
     * @param moduleCode
     *  The module code of the module that has the category to delete the task
     * @param categoryName
     *  The name of the category to delete the task
     * @param taskDescription
     *  The description of the task
     * @param isExact
     *  Checks if categories are to be filtered exactly
     * @param isAll
     *  Checks if filtering is to be done across all modules, categories and tasks
     */
    public DeleteTaskCommand(String moduleCode, String categoryName, String taskDescription,
                             boolean isExact, boolean isAll) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.isExact = isExact;
        this.isAll = isAll;
    }

    /**
     *  Executes the initial phase of the task deletion process depending on the number of tasks filtered.
     *  If there are no tasks in the filtered list, then the deletion ends.
     *  Otherwise, if there is one, there will be a prompt to request confirmation.
     *  If there are multiple tasks instead, there will be a prompt to choose which tasks to delete.
     *
     * @param filteredTasks
     *  The filtered list of tasks containing possibly the tasks to be deleted
     * @return
     *  The result of the execution
     */
    @Override
    protected CommandResult executeInitialDelete(ArrayList<Directory> filteredTasks) {
        final int taskCount = filteredTasks.size();
        if (taskCount == 0) {
            return new CommandResult(MESSAGE_NO_TASKS_FOUND);
        } else if (taskCount == 1) {
            Executor.preparePromptConfirmation();
            Executor.setFilteredList(filteredTasks, DirectoryLevel.TASK);
            Task toDelete = (Task) filteredTasks.get(0);
            return new CommandResult(messageConfirmDeleteTask(toDelete));
        } else {
            Executor.preparePromptIndices();
            Executor.setFilteredList(filteredTasks, DirectoryLevel.TASK);
            return new CommandResult(messagePromptDeleteTaskIndices(filteredTasks));
        }
    }

    /**
     * Executes the <b>Delete Task Command</b> to delete <b>Task<(s)</b> with the <code>task description</code>
     * from the <b>Task List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Task
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Directory> filteredTasks =
                createFilteredTaskList(moduleCode, categoryName, taskDescription, isExact, isAll);
        return executeInitialDelete(filteredTasks);
    }
}
