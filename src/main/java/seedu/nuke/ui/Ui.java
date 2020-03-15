package seedu.nuke.ui;

import seedu.nuke.command.CommandResult;
import seedu.nuke.module.Module;
import seedu.nuke.data.ModuleManager;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String LS = System.lineSeparator();

    private final Scanner in;
    private final PrintStream out;

    /** Constructs the <code>UI</code>. */
    public Ui() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    /**
     * Reads the user input from the command line.
     *
     * @return The input given by the user
     */
    public String getInput() {
        return in.nextLine().trim();
    }

    /**
     * Shows the result message after the command given by the user input is executed.
     * <br>
     * Also, shows the <b>Task List</b> or <i>Search List</i> if indicated in <code>result</code>.
     *
     * @param result The result after executing a command given by the user input
     */
    public void showResult(CommandResult result) {
        out.println(result.getFeedbackToUser().replace("\n", LS));
        if (result.isShowTasks()) {
            printShownList(result.getShownList());
        }
    }

    /**
     * Shows a message to the user.
     *
     * @param message Message to be shown
     */
    public void showSystemMessage(String message) {
        out.println(message.replace("\n", LS));
    }

    public void printShownList(ArrayList<String> shownList) {
        final String DIVIDER = String.format("%s%s%s\n", "+", "-".repeat(100), "+");
        StringBuilder listToShow = new StringBuilder();

        System.out.println(DIVIDER);

        for (String str: shownList) {
            System.out.println(str);
        }

        System.out.println(DIVIDER);

    }

    /**
     * Returns the list of all tasks created by the user.
     *
     * @return The formatted list to be shown to the user
     */


    public void showDeadlines(ArrayList<String> deadlines) {

    }
}