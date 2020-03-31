package seedu.nuke.util;

public class ExceptionMessage {
    public static final String MESSAGE_MISSING_MODULE_CODE = "ALERT! Module code is missing.\n";

    public static final String MESSAGE_EXCESS_PARAMETERS = "ALERT! Too many parameters.\n";

    public static final String MESSAGE_INPUT_LENGTH_EXCEEDED = "ALERT! Enter less than 100 characters.\n";

    public static final String MESSAGE_EMPTY_INPUT = "ALERT! Enter a command.\n";

    public static final String MESSAGE_INVALID_COMMAND = "ALERT! Unrecognised command.\n";

    public static final String MESSAGE_MODULE_NOT_FOUND = "ALERT! Module is not found.\n";
    public static final String MESSAGE_MODULE_NOT_PROVIDED = "ALERT! The module is not provided by NUS currently.\n";
    public static final String MESSAGE_DUPLICATE_MODULE = "ALERT! The module already exists.\n";

    public static final String MESSAGE_CATEGORY_NOT_FOUND = "Alert! Category is not found.\n";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "ALERT! The category already exists.\n";

    public static final String MESSAGE_TASK_NOT_FOUND = "ALERT! Task is not found.\n";
    public static final String MESSAGE_DUPLICATE_TASK = "ALERT! The task already exists.\n";

    public static final String MESSAGE_TASK_FILE_NOT_FOUND = "ALERT! File is not found.\n";
    public static final String MESSAGE_DUPLICATE_TASK_FILE = "ALERT! The file already exists.\n";

    public static final String MESSAGE_MISSING_PARAMETERS = "Alert! Some parameters are missing.\n";

    public static final String MESSAGE_INVALID_PRIORITY = "Alert! Priority should be a number between 0 and 100.\n";

    public static final String MESSAGE_INVALID_DEADLINE_FORMAT = "Alert! Datetime format is invalid.\n";

    public static final String MESSAGE_DELETE_ERROR = "ALERT! There was an error in the deletion.\n";

    public static final String MESSAGE_LIST_NUMBER_NOT_FOUND = "List number not found! Deletion is aborted.\n";

    public static final String MESSAGE_FAILED_DIRECTORY_TRAVERSAL = "Unable to traverse further.\n";
    public static final String MESSAGE_DIRECTORY_NOT_FOUND = "Alert! The next directory could not be found.\n";
    public static final String MESSAGE_INCORRECT_DIRECTORY_LEVEL =
            "Alert! Incorrect directory level to execute command.\n"
            + "Either move to the appropriate directory level, or enter the full directory path.\n";

    public static final String MESSAGE_INVALID_PARAMETERS = "Alert! Invalid or missing parameters found!\n";
    public static final String MESSAGE_INVALID_PREFIX = "Alert! Invalid prefix(es) found!\n";
    public static final String MESSAGE_DUPLICATE_PREFIX_FOUND = "Alert! There are duplicate prefix(es).\n";

    public static final String MESSAGE_FILE_IO_EXCEPTION = "Alert! There was an error in retrieving your file.\n";
    public static final String MESSAGE_FILE_SYSTEM_EXCEPTION = "Alert! There was an error in retrieving your file. "
            + "Please check your file is not currently running.\n";
    public static final String MESSAGE_INVALID_FILE_PATH = "Alert! Invalid file path.\n";
    public static final String MESSAGE_ADD_FILE_NOT_FOUND =
            "Alert! File does not exists. Please check file path again.\n";
    public static final String MESSAGE_FILE_SECURITY_EXCEPTION =
            "Alert! File access was denied by device's security program.\n";
    public static final String MESSAGE_DELETE_FILE_ERROR = "Alert! There was an error in deleting your file.\n";
    public static final String MESSAGE_FILE_NOT_FOUND = "Alert! File does not exists.\n";
    public static final String MESSAGE_MISSING_FILE_PATH = "Alert! File path is missing.\n";
}
