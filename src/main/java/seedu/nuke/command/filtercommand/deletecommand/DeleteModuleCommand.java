package seedu.nuke.command.filtercommand.deletecommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.Module;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_MODULES_FOUND;
import static seedu.nuke.util.Message.messageConfirmDeleteModule;
import static seedu.nuke.util.Message.messagePromptDeleteModuleIndices;

/**
 * <h3>Delete Module Command</h3>
 * A <b>Command</b> to delete <b>Module(s)</b> from the <b>Module List</b>.
 * @see Command
 * @see Module
 */
public class DeleteModuleCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "delm";
    public static final String FORMAT = "delm <module code> [ -e ]";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Delete module"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    private String moduleCode;
    private boolean isExact;

    /**
     * Constructs the command to delete a module.
     *
     * @param moduleCode
     *  The module code of the module
     * @param isExact
     *  Checks if modules are to be filtered exactly
     */
    public DeleteModuleCommand(String moduleCode, boolean isExact) {
        this.moduleCode = moduleCode;
        this.isExact = isExact;
    }

    /**
     *  Executes the initial phase of the module deletion process depending on the number of modules filtered.
     *  If there are no modules in the filtered list, then the deletion ends.
     *  Otherwise, if there is one, there will be a prompt to request confirmation.
     *  If there are multiple modules instead, there will be a prompt to choose which modules to delete.
     *
     * @param filteredModules
     *  The filtered list of modules containing possibly the modules to be deleted
     * @return
     *  The result of the execution
     */
    @Override
    protected CommandResult executeInitialDelete(ArrayList<Directory> filteredModules) {
        final int modulesCount = filteredModules.size();
        if (modulesCount == 0) {
            return new CommandResult(MESSAGE_NO_MODULES_FOUND);
        } else if (modulesCount == 1) {
            Executor.preparePromptConfirmation();
            Executor.setFilteredList(filteredModules, DirectoryLevel.MODULE);
            Module toDelete = (Module) filteredModules.get(0);
            return new CommandResult(messageConfirmDeleteModule(toDelete));
        } else {
            Executor.preparePromptIndices();
            Executor.setFilteredList(filteredModules, DirectoryLevel.MODULE);
            return new CommandResult(messagePromptDeleteModuleIndices(filteredModules));
        }
    }

    /**
     * Executes the <b>Delete Module Command</b> to delete <b>Module(s)</b> with the <code>module code</code> from the
     * <b>Module List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Module
     * @see ModuleManager
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Directory> filteredModules = createFilteredModuleList(moduleCode, isExact);
        return executeInitialDelete(filteredModules);
    }
}
