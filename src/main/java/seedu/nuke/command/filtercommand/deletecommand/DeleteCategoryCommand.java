package seedu.nuke.command.filtercommand.deletecommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Directory;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_CATEGORIES_FOUND;
import static seedu.nuke.util.Message.messageConfirmDeleteCategory;
import static seedu.nuke.util.Message.messagePromptDeleteCategoryIndices;

/**
 * <h3>Delete Category Command</h3>
 * A <b>Command</b> to delete <b>Category(s)</b> from the <b>Category List</b>.
 * @see Command
 * @see Category
 */
public class DeleteCategoryCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "delc";
    public static final String FORMAT = COMMAND_WORD + " <category name> -m <module code> [ -e -a ]";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Delete a category under module"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    private String moduleCode;
    private String categoryName;
    private boolean isExact;
    private boolean isAll;

    /**
     * Constructs the command to delete a category.
     *
     * @param moduleCode
     *  The module code of the module to delete the category
     * @param categoryName
     *  The name of the category
     * @param isExact
     *  Checks if categories are to be filtered exactly
     * @param isAll
     *  Checks if filtering is to be done across all modules
     */
    public DeleteCategoryCommand(String moduleCode, String categoryName, boolean isExact, boolean isAll) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.isExact = isExact;
        this.isAll = isAll;
    }

    /**
     *  Executes the initial phase of the category deletion process depending on the number of categories filtered.
     *  If there are no categories in the filtered list, then the deletion ends.
     *  Otherwise, if there is one, there will be a prompt to request confirmation.
     *  If there are multiple categories instead, there will be a prompt to choose which categories to delete.
     *
     * @param filteredCategories
     *  The filtered list of categories containing possibly the categories to be deleted
     * @return
     *  The result of the execution
     */
    @Override
    protected CommandResult executeInitialDelete(ArrayList<Directory> filteredCategories) {
        final int categoriesCount = filteredCategories.size();
        if (categoriesCount == 0) {
            return new CommandResult(MESSAGE_NO_CATEGORIES_FOUND);
        } else if (categoriesCount == 1) {
            Executor.preparePromptConfirmation();
            Executor.setFilteredList(filteredCategories, DirectoryLevel.CATEGORY);
            Category toDelete = (Category) filteredCategories.get(0);
            return new CommandResult(messageConfirmDeleteCategory(toDelete));
        } else {
            Executor.preparePromptIndices();
            Executor.setFilteredList(filteredCategories, DirectoryLevel.CATEGORY);
            return new CommandResult(messagePromptDeleteCategoryIndices(filteredCategories));
        }
    }

    /**
     * Executes the <b>Delete Category Command</b> to delete <b>Category(s)</b> with the <code>category name</code>
     * from the <b>Category List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Category
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Directory> filteredCategories = createFilteredCategoryList(moduleCode, categoryName, isExact, isAll);
        return executeInitialDelete(filteredCategories);
    }
}
