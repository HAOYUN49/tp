package seedu.nuke.util;

import org.fusesource.jansi.Ansi;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Message {

    public static final Ansi.Color COLOR_ALERT = Ansi.Color.RED;
    public static final Ansi.Color COLOR_OUTPUT = Ansi.Color.WHITE;
    public static final Ansi.Color COLOR_ASK_CONFIRMATION = Ansi.Color.BLUE;
    public static final Ansi.Color COLOR_FRAME = Ansi.Color.GREEN;


    public static final String MESSAGE_LOGO = "      ___          ___          ___          ___     \n"
            + "     /__/\\        /__/\\        /__/|        /  /\\    \n"
            + "     \\  \\:\\       \\  \\:\\      |  |:|       /  /:/_   \n"
            + "      \\  \\:\\       \\  \\:\\     |  |:|      /  /:/ /\\  \n"
            + "  _____\\__\\:\\  ___  \\  \\:\\  __|  |:|     /  /:/ /:/_ \n"
            + " /__/::::::::\\/__/\\  \\__\\:\\/__/\\_|:|____/__/:/ /:/ /\\\n"
            + " \\  \\:\\~~\\~~\\/\\  \\:\\ /  /:/\\  \\:\\/:::::/\\  \\:\\/:/ /:/\n"
            + "  \\  \\:\\  ~~~  \\  \\:\\  /:/  \\  \\::/~~~~  \\  \\::/ /:/ \n"
            + "   \\  \\:\\       \\  \\:\\/:/    \\  \\:\\       \\  \\:\\/:/  \n"
            + "    \\  \\:\\       \\  \\::/      \\  \\:\\       \\  \\::/   \n"
            + "     \\__\\/        \\__\\/        \\__\\/        \\__\\/    \n"
            + "\n";

    public static final String MESSAGE_WELCOME_1 = "Welcome to NUKE";
    public static final String MESSAGE_WELCOME_2 = "What can I do for you?";
    public static final String MESSAGE_NO_TASK_IN_LIST = "There is no task in the list!\n";

    public static String messageTaskSuccessfullyList(int taskCount) {
        return String.format("There are (is) %d task(s) in the list!\n", taskCount);
    }

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n";
    public static final String MESSAGE_EXIT = "Bye. Hope to see you again soon.";
    public static final String DIVIDER = "-".repeat(100);

    public static final String MESSAGE_SHOW_LIST = "Here are what you are looking for...\n";

    public static final String MESSAGE_NO_MODULES_FOUND = "Sorry. No modules found.\n";
    public static final String MESSAGE_NO_CATEGORIES_FOUND = "Sorry. No categories found.\n";
    public static final String MESSAGE_NO_TASKS_FOUND = "Sorry. No tasks found.\n";
    public static final String MESSAGE_NO_FILES_FOUND = "Sorry. No files found.\n";


    public static final String MESSAGE_GO_INTO_MODULE = "please go inside a Module!\n";
    public static final String MESSAGE_HELP = "Here are valid commands and corresponding format:\n";

    public static final String MESSAGE_TAG_ADDED = "Tag added!";


    public static String messageAddModuleSuccess(String moduleCode, String title) {
        return String.format("SUCCESS!! Module %s %s has been added.\n", moduleCode, title);
    }

    public static String messageAddCategorySuccess(String categoryName) {
        return String.format("SUCCESS!! Category %s is created.\n", categoryName);
    }

    public static String messageAddTaskSuccess(String taskDescription) {
        return String.format("SUCCESS!! Task %s is created.\n", taskDescription);
    }

    public static String messageAddFileSuccess(String fileName) {
        return String.format("SUCCESS!! File %s is added.\n", fileName);
    }

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "SUCCESS!! Module(s) have been deleted.\n";
    public static final String MESSAGE_DELETE_CATEGORY_SUCCESS = "SUCCESS!! Category/Categories have been deleted.\n";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "SUCCESS!! Task(s) have been deleted.\n";
    public static final String MESSAGE_DELETE_FILE_SUCCESS = "SUCCESS!! Files(s) have been deleted.\n";

    public static final String MESSAGE_DELETE_ABORTED = "Deletion is aborted.\n";

    /**
     * Creates the message to confirm deletion of a module.
     *
     * @param toDelete
     *  The module to be deleted
     * @return
     *  The message to confirm deletion of the module
     */
    public static String messageConfirmDeleteModule(Module toDelete) {
        return String.format("Confirm delete %s %s?\n", toDelete.getModuleCode(), toDelete.getTitle());
    }

    /**
     * Creates the message to confirm deletion of multiple modules.
     *
     * @param filteredModules
     *  The filtered list of modules
     * @param toDeleteIndices
     *  The indices of the modules to be deleted from the list
     * @return
     *  The message to confirm deletion of modules
     */
    public static String messageConfirmDeleteModule(ArrayList<Module> filteredModules,
                                                    ArrayList<Integer> toDeleteIndices) {
        StringBuilder promptMessage = new StringBuilder();
        promptMessage.append("Confirm delete these modules?\n");
        for (int index : toDeleteIndices) {
            String toDeleteModuleCode = filteredModules.get(index).getModuleCode();
            String toDeleteTitle = filteredModules.get(index).getTitle();
            promptMessage.append(String.format("%s %s\n", toDeleteModuleCode, toDeleteTitle));
        }
        return promptMessage.toString();
    }

    /**
     * Creates the message to prompt user to enter the indices of the modules to be deleted from the list.
     *
     * @param filteredModules
     *  The filtered list of modules to be deleted
     * @return
     *  The message to prompt the user to enter the indices
     */
    public static String messagePromptDeleteModuleIndices(ArrayList<Directory> filteredModules) {
        ArrayList<Module> modules = filteredModules.stream()
                .map(Module.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
        return "Multiple matching modules found.\n"
                + ListCreator.createModuleListTable(modules)
                + "\nEnter the list number(s) of the modules to delete.\n";
    }

    /**
     * Creates the message to confirm deletion of a category.
     *
     * @param toDelete
     *  The category to be deleted
     * @return
     *  The message to confirm deletion of the category
     */
    public static String messageConfirmDeleteCategory(Category toDelete) {
        return String.format("Confirm delete %s?\n", toDelete.getCategoryName());
    }

    /**
     * Creates the message to confirm deletion of multiple categories.
     *
     * @param filteredCategories
     *  The filtered list of categories
     * @param toDeleteIndices
     *  The indices of the categories to be deleted from the list
     * @return
     *  The message to confirm deletion of categories
     */
    public static String messageConfirmDeleteCategory(ArrayList<Category> filteredCategories,
                                                      ArrayList<Integer> toDeleteIndices) {
        StringBuilder promptMessage = new StringBuilder();
        promptMessage.append("Confirm delete these categories?\n");
        for (int index : toDeleteIndices) {
            String toDeleteCategoryName = filteredCategories.get(index).getCategoryName();
            promptMessage.append(String.format("%s\n", toDeleteCategoryName));
        }
        return promptMessage.toString();
    }

    /**
     * Creates the message to prompt user to enter the indices of the categories to be deleted from the list.
     *
     * @param filteredCategories
     *  The filtered list of categories to be deleted
     * @return
     *  The message to prompt the user to enter the indices
     */
    public static String messagePromptDeleteCategoryIndices(ArrayList<Directory> filteredCategories) {
        ArrayList<Category> categories = filteredCategories.stream()
                .map(Category.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
        return "Multiple matching categories found.\n"
                + ListCreator.createCategoryListTable(categories)
                + "\nEnter the list number(s) of the categories to delete.\n";
    }

    /**
     * Creates the message to confirm deletion of a task.
     *
     * @param toDelete
     *  The task to be deleted
     * @return
     *  The message to confirm deletion of the task
     */
    public static String messageConfirmDeleteTask(Task toDelete) {
        return String.format("Confirm delete %s?\n", toDelete.getDescription());
    }

    /**
     * Creates the message to confirm deletion of multiple tasks.
     *
     * @param filteredTasks
     *  The filtered list of tasks
     * @param toDeleteIndices
     *  The indices of the tasks to be deleted from the list
     * @return
     *  The message to confirm deletion of tasks
     */
    public static String messageConfirmDeleteTask(ArrayList<Task> filteredTasks, ArrayList<Integer> toDeleteIndices) {
        StringBuilder promptMessage = new StringBuilder();
        promptMessage.append("Confirm delete these tasks?\n");
        for (int index : toDeleteIndices) {
            String toDeleteTaskDescription = filteredTasks.get(index).getDescription();
            promptMessage.append(String.format("%s\n", toDeleteTaskDescription));
        }
        return promptMessage.toString();
    }

    /**
     * Creates the message to prompt user to enter the indices of the tasks to be deleted from the list.
     *
     * @param filteredTasks
     *  The filtered list of tasks to be deleted
     * @return
     *  The message to prompt the user to enter the indices
     */
    public static String messagePromptDeleteTaskIndices(ArrayList<Directory> filteredTasks) {
        ArrayList<Task> tasks = filteredTasks.stream()
                .map(Task.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
        return "Multiple matching tasks found.\n"
                + ListCreator.createTaskListTable(tasks)
                + "\nEnter the list number(s) of the tasks to delete.\n";
    }

    /**
     * Creates the message to confirm deletion of a file.
     *
     * @param toDelete
     *  The file to be deleted
     * @return
     *  The message to confirm deletion of the file
     */
    public static String messageConfirmDeleteFile(TaskFile toDelete) {
        return String.format("Confirm delete %s?\n", toDelete.getFileName());
    }

    /**
     * Creates the message to confirm deletion of multiple files.
     *
     * @param filteredFiles
     *  The filtered list of files
     * @param toDeleteIndices
     *  The indices of the files to be deleted from the list
     * @return
     *  The message to confirm deletion of files
     */
    public static String messageConfirmDeleteFile(ArrayList<TaskFile> filteredFiles,
          ArrayList<Integer> toDeleteIndices) {
        StringBuilder promptMessage = new StringBuilder();
        promptMessage.append("Confirm delete these files?\n");
        for (int index : toDeleteIndices) {
            String toDeleteFileName = filteredFiles.get(index).getFileName();
            promptMessage.append(String.format("%s\n", toDeleteFileName));
        }
        return promptMessage.toString();
    }

    /**
     * Creates the message to prompt user to enter the indices of the files to be deleted from the list.
     *
     * @param filteredFiles
     *  The filtered list of files to be deleted
     * @return
     *  The message to prompt the user to enter the indices
     */
    public static String messagePromptDeleteFileIndices(ArrayList<Directory> filteredFiles) {
        ArrayList<TaskFile> files = filteredFiles.stream()
                .map(TaskFile.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));
        return "Multiple matching files found.\n"
                + ListCreator.createFileListTable(files)
                + "\nEnter the list number(s) of the tasks to delete.\n";
    }

    public static final String MESSAGE_PROMPT_FORMAT = "Enter 'yes' to confirm or 'no' to abort.\n";
    public static final String MESSAGE_INVALID_DELETE_INDICES = "Deletion aborted due to invalid index provided.\n";
    public static final String MESSAGE_ILLEGAL_DELETE =
            "Attempting to delete the current or its parent's directory.\nMove out of the directory first.\n";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "SUCCESS!! Module has been updated.\n";
    public static final String MESSAGE_EDIT_CATEGORY_SUCCESS = "SUCCESS!! Category has been updated.\n";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "SUCCESS!! Task has been updated.\n";
    public static final String MESSAGE_NO_EDIT = "There is nothing to edit.\n";

    public static final String MESSAGE_UNDO_SUCCESS = "SUCCESS!! Data has been reverted to last state.\n";

    public static final String MESSAGE_OPEN_FILE_SUCCESS = "SUCCESS!! The files are opening now...\n";
    public static final String MESSAGE_NO_FILE_CHOSEN = "No file was chosen.\n";
}
