package seedu.nuke.command.addcommand;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.CategoryManager;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.TaskManager;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Task;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.CATEGORY_PREFIX;
import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.parser.Parser.TASK_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_CATEGORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_NOT_FOUND;
import static seedu.nuke.util.Message.MESSAGE_TAG_ADDED;

public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addg";
    public static final String FORMAT = COMMAND_WORD
            + " <tag info> -m <module code> -c <category name>"
            + " -t <task description>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Add tags to task"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    public static final Pattern REGEX_FORMATS = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)+)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<taskDescription>(?:\\s+" + TASK_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<invalid>.*)"
    );
    private final ArrayList<String> infos;
    private String taskDescription;
    private String moduleCode;
    private String categoryName;

    /**
     * Constructs an Add Tag Command.
     *
     * @param infos
     *  The tag info
     * @param taskDescription
     *  The description of the task
     * @param moduleCode
     *  The module code of the parent module
     * @param categoryName
     *  The name of the parent category
     */
    public AddTagCommand(ArrayList<String> infos, String moduleCode, String categoryName, String taskDescription) {
        this.infos = infos;
        this.taskDescription = taskDescription;
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
    }

    protected CommandResult executeAdd() {
        try {
            Task toAddTag = DirectoryTraverser.getTaskDirectory(moduleCode, categoryName, taskDescription);
            toAddTag.setTag(infos);
            for (String info : infos) {
                assert toAddTag.getTags().contains(info) : "tag have been successfully added";
            }
            return new CommandResult(MESSAGE_TAG_ADDED);
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryManager.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        } catch (TaskManager.TaskNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_NOT_FOUND);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        }
    }

    @Override
    public CommandResult execute() {
        return executeAdd();
    }
}
