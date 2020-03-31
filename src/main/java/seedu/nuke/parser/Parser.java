package seedu.nuke.parser;

import seedu.nuke.Executor;

import seedu.nuke.command.ChangeDirectoryCommand;
import seedu.nuke.command.Command;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.command.HelpCommand;
import seedu.nuke.command.IncorrectCommand;
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
import seedu.nuke.command.filtercommand.FilterCommand;
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
import seedu.nuke.command.promptcommand.ConfirmationStatus;
import seedu.nuke.command.promptcommand.DeleteConfirmationPrompt;
import seedu.nuke.command.promptcommand.ListNumberPrompt;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.exception.InvalidFormatException;
import seedu.nuke.util.DateTime;
import seedu.nuke.util.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_PREFIX_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INVALID_DEADLINE_FORMAT;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INVALID_PARAMETERS;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INVALID_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INVALID_PRIORITY;
import static seedu.nuke.util.Message.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nuke.util.Message.MESSAGE_INVALID_DELETE_INDICES;
import static seedu.nuke.util.Message.MESSAGE_NO_EDIT;


public class Parser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<parameters>.*)");
    private static final String WHITESPACES = "\\s+";
    private static final String NONE = "";

    private static final String GENERIC_LIST_COMMAND = "ls";
    private static final String GENERIC_ADD_COMMAND = "mkdir";
    private static final String GENERIC_DELETE_COMMAND = "rm";

    public static final String MODULE_PREFIX = "-m";
    public static final String CATEGORY_PREFIX = "-c";
    public static final String TASK_PREFIX = "-t";
    public static final String FILE_PREFIX = "-f";
    public static final String PRIORITY_PREFIX = "-p";
    public static final String DEADLINE_PREFIX = "-d";
    public static final String ALL_FLAG = "-a";
    public static final String EXACT_FLAG = "-e";
    public static final String TAG_PREFIX = "-g";

    private static final String COMMAND_WORD_GROUP = "commandWord";
    private static final String PARAMETERS_GROUP = "parameters";
    private static final String IDENTIFIER_GROUP = "identifier";
    private static final String MODULE_GROUP = "moduleCode";
    private static final String CATEGORY_GROUP = "categoryName";
    private static final String TASK_GROUP = "taskDescription";
    private static final String FILE_GROUP = "fileInfo";
    private static final String DEADLINE_GROUP = "deadline";
    private static final String PRIORITY_GROUP = "priority";
    private static final String EXACT_GROUP = "exact";
    private static final String ALL_GROUP = "all";
    private static final String OPTIONAL_GROUP = "optional";
    private static final String INVALID_GROUP = "invalid";

    /**
     * Parses the input string read by the <b>UI</b> and converts the string into a specific <b>Command</b>, which is
     * to be executed by the <b>Nuke</b> program.
     * <p></p>
     * <b>Note</b>: The user input has to start with a certain keyword (i.e. <i>command word</i>), otherwise an
     * <i>Invalid Command Exception</i> will be thrown.
     *
     * @param input The user input read by the <b>UI</b>
     * @return The <b>corresponding</b> command to be executed
     * @see seedu.nuke.ui.Ui
     * @see Command
     */
    public Command parseCommand(String input) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT + HelpCommand.MESSAGE_USAGE);
        }
        String commandWord = matcher.group(COMMAND_WORD_GROUP).toLowerCase();
        String parameters = matcher.group(PARAMETERS_GROUP);

        try {
            switch (commandWord) {

            case GENERIC_LIST_COMMAND:
                return prepareGenericListCommand(parameters.trim());

            case GENERIC_ADD_COMMAND:
                return prepareGenericAddCommand(parameters);

            case GENERIC_DELETE_COMMAND:
                return prepareGenericDeleteCommand(parameters);

            case AddModuleCommand.COMMAND_WORD:
                return prepareAddModuleCommand(parameters);
            case AddCategoryCommand.COMMAND_WORD:
                return prepareAddCategoryCommand(parameters);
            case AddTaskCommand.COMMAND_WORD:
                return prepareAddTaskCommand(parameters);
            case AddFileCommand.COMMAND_WORD:
                return prepareAddFileCommand(parameters);
            case AddTagCommand.COMMAND_WORD:
                return prepareAddTagCommand(parameters);
                
            case DeleteModuleCommand.COMMAND_WORD:
                return prepareDeleteAndListModuleCommand(parameters, true);
            case DeleteCategoryCommand.COMMAND_WORD:
                return prepareDeleteAndListCategoryCommand(parameters, true);
            case DeleteTaskCommand.COMMAND_WORD:
                return prepareDeleteAndListTaskCommand(parameters, true);
            case DeleteFileCommand.COMMAND_WORD:
                return prepareDeleteAndListFileCommand(parameters, true);

            case ListModuleCommand.COMMAND_WORD:
                return prepareDeleteAndListModuleCommand(parameters, false);
            case ListCategoryCommand.COMMAND_WORD:
                return prepareDeleteAndListCategoryCommand(parameters, false);
            case ListTaskCommand.COMMAND_WORD:
                return prepareDeleteAndListTaskCommand(parameters, false);
            case ListFileCommand.COMMAND_WORD:
                return prepareDeleteAndListFileCommand(parameters, false);
            case ListModuleTasksDeadlineCommand.COMMAND_WORD:
                return new ListModuleTasksDeadlineCommand(parameters.trim());
            case ListAllTasksDeadlineCommand.COMMAND_WORD:
                return new ListAllTasksDeadlineCommand();

            case EditModuleCommand.COMMAND_WORD:
                return prepareEditModuleCommand(parameters);
            case EditCategoryCommand.COMMAND_WORD:
                return prepareEditCategoryCommand(parameters);
            case EditTaskCommand.COMMAND_WORD:
                return prepareEditTaskCommand(parameters);
            case EditFileCommand.COMMAND_WORD:
                return prepareEditFileCommand(parameters);
            case MarkAsDoneCommand.COMMAND_WORD:
                return prepareMarkAsDoneCommand(parameters);

            case ChangeDirectoryCommand.COMMAND_WORD:
                return prepareChangeDirectoryCommand(parameters);

            case OpenFileCommand.COMMAND_WORD:
                return prepareOpenFileCommand(parameters);

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            default:
                return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT + HelpCommand.MESSAGE_USAGE);
            }
        } catch (InvalidParameterException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PARAMETERS);
        } catch (DuplicatePrefixException e) {
            return new IncorrectCommand(MESSAGE_DUPLICATE_PREFIX_FOUND);
        } catch (InvalidPrefixException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PREFIX);
        }
    }


    /**
     * Prepares the command to change the current directory.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to change the current directory
     */
    private Command prepareChangeDirectoryCommand(String parameters) {
        if (parameters.trim().equals("..")) {
            return new ChangeDirectoryCommand();
        } else {
            return new ChangeDirectoryCommand(parameters.trim());
        }
    }

    /**
     * Prepare the command to list the content based on the directory of the user is currently in.
     * @param parameters The parameters given by the user
     * @return The command to change the current directory
     * @throws InvalidPrefixException exception is thrown when prefix is invalid.
     * @throws InvalidParameterException exception is thrown when parameter is invalid.
     * @throws DuplicatePrefixException exception is thrown when duplicated prefix is provided.
     */
    private Command prepareGenericListCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        switch (DirectoryTraverser.getCurrentDirectoryLevel()) {
        case ROOT:
            if (parameters.isEmpty()) {
                return prepareDeleteAndListModuleCommand(parameters, false);
            } else {
                parameters = " " + MODULE_PREFIX + " " + parameters;
                return prepareDeleteAndListCategoryCommand(parameters, false);
            }
        case MODULE:
            if (parameters.isEmpty()) {
                return prepareDeleteAndListCategoryCommand(parameters, false);
            } else {
                parameters = " " + CATEGORY_PREFIX + " " + parameters;
                return prepareDeleteAndListTaskCommand(parameters, false);
            }
        case CATEGORY:
            if (parameters.isEmpty()) {
                return prepareDeleteAndListTaskCommand(parameters, false);
            } else {
                parameters = " " + TASK_PREFIX + " " + parameters;
                return prepareDeleteAndListFileCommand(parameters, false);
            }
        case TASK:
            return prepareDeleteAndListFileCommand(parameters, false);
        default:
            return new IncorrectCommand(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        }
    }

    /**
     * Prepare the command to add the content based on the directory of the user is currently in.
     * @param parameters The parameters given by the user
     * @return The command to change the current directory
     * @throws InvalidPrefixException exception is thrown when prefix is invalid.
     * @throws InvalidParameterException exception is thrown when parameter is invalid.
     * @throws DuplicatePrefixException exception is thrown when duplicated prefix is provided.
     */
    private Command prepareGenericAddCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        switch (DirectoryTraverser.getCurrentDirectoryLevel()) {
        case ROOT:
            return prepareAddModuleCommand(parameters);
        case MODULE:
            return prepareAddCategoryCommand(parameters);
        case CATEGORY:
            return prepareAddTaskCommand(parameters);
        case TASK:
            return prepareAddFileCommand(parameters);
        default:
            return new IncorrectCommand(MESSAGE_INCORRECT_DIRECTORY_LEVEL + HelpCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Prepare the command to delete the content based on the directory of the user is currently in.
     * @param parameters The parameters given by the user
     * @return The command to change the current directory
     * @throws InvalidPrefixException exception is thrown when prefix is invalid.
     * @throws InvalidParameterException exception is thrown when parameter is invalid.
     * @throws DuplicatePrefixException exception is thrown when duplicated prefix is provided.
     */
    private Command prepareGenericDeleteCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        switch (DirectoryTraverser.getCurrentDirectoryLevel()) {
        case ROOT:
            return prepareDeleteAndListModuleCommand(parameters, true);
        case MODULE:
            return prepareDeleteAndListCategoryCommand(parameters, true);
        case CATEGORY:
            return prepareDeleteAndListTaskCommand(parameters, true);
        case TASK:
            return prepareDeleteAndListFileCommand(parameters, true);
        default:
            return new IncorrectCommand(MESSAGE_INCORRECT_DIRECTORY_LEVEL + HelpCommand.MESSAGE_USAGE);
        }
    }

    /* Prepare Add Commands */

    /**
     * Prepares the command to add a module.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to add a module
     */
    private Command prepareAddModuleCommand(String parameters)
            throws InvalidParameterException, DuplicatePrefixException, InvalidPrefixException {
        Matcher matcher = AddModuleCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher);

        String moduleCode = matcher.group(IDENTIFIER_GROUP).trim();

        return new AddModuleCommand(moduleCode);
    }

    /**
     * Prepares the command to add a category.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to add a category
     */
    private Command prepareAddCategoryCommand(String parameters)
            throws InvalidParameterException, DuplicatePrefixException, InvalidPrefixException {
        Matcher matcher = AddCategoryCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, PRIORITY_PREFIX);

        String categoryName = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String priority = matcher.group(PRIORITY_GROUP).replace(PRIORITY_PREFIX, NONE).trim();

        if (priority.isEmpty()) {
            return new AddCategoryCommand(moduleCode, categoryName);
        }

        try {
            return new AddCategoryCommand(moduleCode, categoryName, parsePriority(priority));
        } catch (NumberFormatException | InvalidPriorityException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PRIORITY);
        }
    }

    /**
     * Prepares the command to add a task.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to add a task
     */
    private Command prepareAddTaskCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = AddTaskCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX,
                DEADLINE_PREFIX, PRIORITY_PREFIX);

        String taskDescription = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = AddTaskCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, DEADLINE_GROUP, PRIORITY_GROUP);

        String deadline = optionalAttributes[0].replace(DEADLINE_PREFIX, NONE).trim();
        String priority = optionalAttributes[1].replace(PRIORITY_PREFIX, NONE).trim();

        DateTime deadlineToSet;
        try {
            deadlineToSet = DateTimeFormat.stringToDateTime(deadline);
        } catch (DateTimeFormat.InvalidDateTimeException e) {
            return new IncorrectCommand(MESSAGE_INVALID_DEADLINE_FORMAT);
        }

        if (priority.isEmpty()) {
            return new AddTaskCommand(moduleCode, categoryName, taskDescription, deadlineToSet);
        }

        try {
            return new AddTaskCommand(moduleCode, categoryName, taskDescription, deadlineToSet,
                    parsePriority(priority));
        } catch (NumberFormatException | InvalidPriorityException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PRIORITY);
        }
    }

    private Command prepareAddTagCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = AddTagCommand.REGEX_FORMATS.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX, TAG_PREFIX);

        String info = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String taskDescription = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();

        return new AddTagCommand(new ArrayList<>(Arrays.asList(info)), moduleCode, categoryName, taskDescription);
    }

    /**
     * Prepares the command to add a file.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to add a file
     */
    private Command prepareAddFileCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = AddFileCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX, FILE_PREFIX);

        String fileName = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String taskDescription = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();
        String filePath = matcher.group(FILE_GROUP).replace(FILE_PREFIX, NONE).trim();

        return new AddFileCommand(moduleCode, categoryName, taskDescription, fileName, filePath);
    }

    /* Prepare Delete and List Commands */

    /**
     * Prepares the command to delete modules or show filtered modules.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to delete modules or show filtered modules
     */
    private Command prepareDeleteAndListModuleCommand(String parameters, boolean isDelete)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = FilterCommand.MODULE_REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, EXACT_FLAG, ALL_FLAG);

        String moduleKeyword = matcher.group(IDENTIFIER_GROUP).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = FilterCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, EXACT_GROUP);

        String exactFlag = optionalAttributes[0].trim();
        boolean isExact = !exactFlag.isEmpty();

        if (isDelete) {
            return new DeleteModuleCommand(moduleKeyword, isExact);
        } else {
            return new ListModuleCommand(moduleKeyword, isExact);
        }
    }

    /**
     * Prepares the command to delete categories or show filtered categories.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to delete categories or show filtered categories
     */
    private Command prepareDeleteAndListCategoryCommand(String parameters, boolean isDelete)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = FilterCommand.CATEGORY_REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, EXACT_FLAG, ALL_FLAG);

        String categoryKeyword = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleKeyword = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = FilterCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, EXACT_GROUP, ALL_GROUP);

        String exactFlag = optionalAttributes[0].trim();
        boolean isExact = !exactFlag.isEmpty();
        String allFlag = optionalAttributes[1].trim();
        boolean isAll = !allFlag.isEmpty();

        if (isDelete) {
            return new DeleteCategoryCommand(moduleKeyword, categoryKeyword, isExact, isAll);
        } else {
            return new ListCategoryCommand(moduleKeyword, categoryKeyword, isExact, isAll);
        }
    }

    /**
     * Prepares the command to delete tasks or show filtered tasks.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to delete tasks or show filtered tasks
     */
    private Command prepareDeleteAndListTaskCommand(String parameters, boolean isDelete)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = FilterCommand.TASK_REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, EXACT_FLAG, ALL_FLAG);

        String taskKeyword = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleKeyword = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryKeyword = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = FilterCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, EXACT_GROUP, ALL_GROUP);

        String exactFlag = optionalAttributes[0].trim();
        boolean isExact = !exactFlag.isEmpty();
        String allFlag = optionalAttributes[1].trim();
        boolean isAll = !allFlag.isEmpty();

        if (isDelete) {
            return new DeleteTaskCommand(moduleKeyword, categoryKeyword, taskKeyword, isExact, isAll);
        } else {
            return new ListTaskCommand(moduleKeyword, categoryKeyword, taskKeyword, isExact, isAll);
        }
    }

    /**
     * Prepares the command to delete files or show filtered files.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to delete files or show filtered files
     */
    private Command prepareDeleteAndListFileCommand(String parameters, boolean isDelete)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = FilterCommand.FILE_REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX, EXACT_FLAG, ALL_FLAG);

        String fileKeyword = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleKeyword = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryKeyword = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String taskKeyword = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = FilterCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, EXACT_GROUP, ALL_GROUP);

        String exactFlag = optionalAttributes[0].trim();
        boolean isExact = !exactFlag.isEmpty();
        String allFlag = optionalAttributes[1].trim();
        boolean isAll = !allFlag.isEmpty();

        if (isDelete) {
            return new DeleteFileCommand(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword, isExact, isAll);
        } else {
            return new ListFileCommand(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword, isExact, isAll);
        }
    }

    /* Prepare Edit Commands */

    /**
     * Prepares the command to edit a module.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to edit a module
     */
    private Command prepareEditModuleCommand(String parameters)
            throws InvalidParameterException, DuplicatePrefixException, InvalidPrefixException {
        Matcher matcher = EditModuleCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX);

        String oldModuleCode = matcher.group(IDENTIFIER_GROUP).trim();
        String newModuleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();

        if (isNothingToEdit(newModuleCode)) {
            return new IncorrectCommand(MESSAGE_NO_EDIT);
        }

        return new EditModuleCommand(oldModuleCode, newModuleCode);
    }

    /**
     * Prepares the command to edit a category.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to edit a category
     */
    private Command prepareEditCategoryCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = EditCategoryCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, PRIORITY_PREFIX);

        String oldCategoryName = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String newCategoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String newPriority = matcher.group(PRIORITY_GROUP).replace(PRIORITY_PREFIX, NONE).trim();

        if (isNothingToEdit(newCategoryName, newPriority)) {
            return new IncorrectCommand(MESSAGE_NO_EDIT);
        }

        if (newPriority.isEmpty()) {
            return new EditCategoryCommand(oldCategoryName, moduleCode, newCategoryName);
        }

        try {
            return new EditCategoryCommand(oldCategoryName, moduleCode, newCategoryName, parsePriority(newPriority));
        } catch (NumberFormatException | InvalidPriorityException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PRIORITY);
        }
    }

    /**
     * Prepares the command to edit a task.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to edit a task
     */
    private Command prepareEditTaskCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = EditTaskCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX,
                DEADLINE_PREFIX, PRIORITY_PREFIX);

        String oldTaskDescription = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String newTaskDescription = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();

        String optionalParameters = matcher.group(OPTIONAL_GROUP);
        Matcher optionalMatcher = EditTaskCommand.REGEX_OPTIONAL_FORMAT.matcher(optionalParameters);
        String[] optionalAttributes = getOptionalAttributes(optionalMatcher, DEADLINE_GROUP, PRIORITY_GROUP);

        String newDeadline = optionalAttributes[0].replace(DEADLINE_PREFIX, NONE).trim();
        String newPriority = optionalAttributes[1].replace(PRIORITY_PREFIX, NONE).trim();

        if (isNothingToEdit(newTaskDescription, newDeadline, newPriority)) {
            return new IncorrectCommand(MESSAGE_NO_EDIT);
        }

        DateTime newDeadlineToSet;
        try {
            newDeadlineToSet = DateTimeFormat.stringToDateTime(newDeadline);
        } catch (DateTimeFormat.InvalidDateTimeException e) {
            return new IncorrectCommand(MESSAGE_INVALID_DEADLINE_FORMAT);
        }

        if (newPriority.isEmpty()) {
            return new EditTaskCommand(oldTaskDescription, moduleCode, categoryName, newTaskDescription,
                    newDeadlineToSet);
        }

        try {
            return new EditTaskCommand(oldTaskDescription, moduleCode, categoryName, newTaskDescription,
                    newDeadlineToSet, parsePriority(newPriority));
        } catch (NumberFormatException | InvalidPriorityException e) {
            return new IncorrectCommand(MESSAGE_INVALID_PRIORITY);
        }
    }

    /**
     * Prepares the command to edit a file.
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to edit a file
     */
    private Command prepareEditFileCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = EditFileCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX, FILE_PREFIX);

        String oldFileName = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String taskDescription = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();
        String newFileName = matcher.group(FILE_GROUP).replace(FILE_PREFIX, NONE).trim();

        if (isNothingToEdit(newFileName)) {
            return new IncorrectCommand(MESSAGE_NO_EDIT);
        }

        return new EditFileCommand(oldFileName, moduleCode, categoryName, taskDescription, newFileName);
    }

    private Command prepareMarkAsDoneCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = MarkAsDoneCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX);

        String taskDescription = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();

        return new MarkAsDoneCommand(moduleCode, categoryName, taskDescription);
    }

    /* Miscellaneous Commands */

    /**
     * Prepares the command to open file(s).
     *
     * @param parameters
     *  The parameters given by the user
     * @return
     *  The command to open file(s)
     */
    private Command prepareOpenFileCommand(String parameters)
            throws InvalidPrefixException, InvalidParameterException, DuplicatePrefixException {
        Matcher matcher = OpenFileCommand.REGEX_FORMAT.matcher(parameters);
        validateParameters(parameters, matcher, MODULE_PREFIX, CATEGORY_PREFIX, TASK_PREFIX);

        String fileName = matcher.group(IDENTIFIER_GROUP).trim();
        String moduleCode = matcher.group(MODULE_GROUP).replace(MODULE_PREFIX, NONE).trim();
        String categoryName = matcher.group(CATEGORY_GROUP).replace(CATEGORY_PREFIX, NONE).trim();
        String taskDescription = matcher.group(TASK_GROUP).replace(TASK_PREFIX, NONE).trim();

        return new OpenFileCommand(moduleCode, categoryName, taskDescription, fileName);
    }

    /**
     * Checks if the priority is between 0 and 100 inclusive.
     *
     * @param priority
     *  The priority given by the user
     * @return
     *  <code>TRUE</code> if the priority is within range, or <code>FALSE</code> otherwise
     */
    private boolean isPriorityWithinRange(int priority) {
        return priority >= 0 && priority <= 20;
    }

    /**
     * Parses a string as a priority.
     *
     * @param priorityString
     *  The string representing the priority
     * @return
     *  The numeric priority of the string
     * @throws InvalidPriorityException
     *  If the priority is invalid as it is not within the expected range
     */
    private int parsePriority(String priorityString) throws InvalidPriorityException {
        int priorityToSet = Integer.parseInt(priorityString);
        if (!isPriorityWithinRange(priorityToSet)) {
            throw new InvalidPriorityException();
        }
        return priorityToSet;
    }

    /**
     * Validate the parameters given by the user for the respective command.
     *
     * @param parameters
     *  The parameters given by the user
     * @param matcher
     *  The matcher to match for attributes and check for validity
     * @param parameterPrefixes
     *  The prefixes used for the respective command
     * @throws InvalidParameterException
     *  If an invalid parameter is found in the parameters or the parameters do not match the expected format
     * @throws DuplicatePrefixException
     *  If a duplicate prefix is found in the parameters
     * @throws InvalidPrefixException
     *  If an invalid prefix is found in the parameters
     */
    private void validateParameters(String parameters, Matcher matcher, String... parameterPrefixes)
            throws InvalidParameterException, DuplicatePrefixException, InvalidPrefixException {
        for (String prefix : parameterPrefixes) {
            if (parameters.split(String.format(" %s", prefix)).length > 2) {
                throw new DuplicatePrefixException();
            }
        }

        if (!matcher.matches()) {
            throw new InvalidParameterException();
        }

        if (!matcher.group(INVALID_GROUP).trim().isEmpty()) {
            throw new InvalidPrefixException();
        }
    }

    /**
     * Returns any matched optional attributes that is to be used to prepare the respective command.
     *
     * @param matcher
     *  The matcher to find the optional attributes
     * @param groups
     *  The name of the groups for the optional attributes
     * @return
     *  The list of found optional attributes
     */
    private String[] getOptionalAttributes(Matcher matcher, String... groups) {
        // No need to find duplicate prefixes since already done so in validateParameters method
        String[] optionalAttributes  = new String[groups.length];
        Arrays.fill(optionalAttributes, NONE);

        int i = 0;
        for (String group : groups) {
            while (matcher.find()) {
                if (!matcher.group(group).isEmpty()) {
                    optionalAttributes[i] = matcher.group(group);
                    break;
                }
            }
            i++;
            matcher.reset();
        }
        return optionalAttributes;
    }

    /**
     * Checks if there is anything to edit from the input given by the user.
     *
     * @param attributes
     *  The attributes to be edited
     * @return
     *  <code>TRUE</code> if there is nothing to edit, or <code>FALSE</code> otherwise
     */
    private boolean isNothingToEdit(String... attributes) {
        for (String attribute : attributes) {
            if (!attribute.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Parses the input as a confirmation status to a confirmation prompt for the user, and prepares the
     * corresponding command.
     *
     * @param userInput
     *  The input given by the user
     * @return
     *  The command to be executed
     */
    public Command parseConfirmation(String userInput) {
        switch (userInput) {
        case "yes":
        case "y":
            return new DeleteConfirmationPrompt(ConfirmationStatus.CONFIRM);

        case "no":
        case "n":
            return new DeleteConfirmationPrompt(ConfirmationStatus.ABORT);

        default:
            return new DeleteConfirmationPrompt(ConfirmationStatus.WAIT);
        }
    }

    /**
     * Parses the input as indices to prepare a command to utilise the parsed indices.
     *
     * @param input
     *  The input string given by the user
     * @return
     *  The command to be executed
     */
    public Command parseIndices(String input) {
        final Matcher matcher = ListNumberPrompt.INDICES_FORMAT.matcher(input.trim());

        if (!matcher.matches()) {
            Executor.terminatePrompt();
            return new IncorrectCommand(MESSAGE_INVALID_DELETE_INDICES);
        }

        String indicesString = matcher.group("indices");
        String[] separatedIndicesString = indicesString.split(WHITESPACES);

        // Convert String array to Integer ArrayList and removing duplicates
        ArrayList<Integer> indices = Stream.of(separatedIndicesString)
                .map(Integer::parseInt).distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        // Decrement each index by 1 due to 0-based indexing
        IntStream.range(0, indices.size())
                .forEach(currentValue -> indices.set(currentValue, indices.get(currentValue) - 1));

        return new ListNumberPrompt(indices);
    }

    public static class InvalidParameterException extends InvalidFormatException {
    }

    public static class InvalidPrefixException extends InvalidFormatException {
    }

    public static class DuplicatePrefixException extends InvalidFormatException {
    }

    public static class InvalidPriorityException extends InvalidFormatException {
    }
}
