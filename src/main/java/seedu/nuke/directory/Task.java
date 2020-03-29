package seedu.nuke.directory;

import seedu.nuke.data.TaskFileManager;
import seedu.nuke.util.DateTime;
import seedu.nuke.tag.Tag;

import java.util.ArrayList;

import static seedu.nuke.common.Constants.NO_ICON;
import static seedu.nuke.common.Constants.YES_ICON;

public class Task extends Directory implements Tag {
    private String description;
    private boolean isDone;
    private int priority;
    private DateTime deadline;
    private TaskFileManager files;
    // private String moduleCode;
    private ArrayList<String> tags;


    ///**
    // * constructor for the simplest task.
    // *
    // * @param description the description of the task
    // * @param moduleCode the module code of the module which the task belongs to
    // */
    //public Task(Module module, String description, String moduleCode) {
    //    super(module);
    //    this.description = description;
    //    this.files = new TaskFileManager();
    //    this.deadline = null;
    //    this.priority = -1;
    //    this.isDone = false;
    //    this.moduleCode = moduleCode;
    //    this.tags = null;
    //}


    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * Constructs the task.
     *
     * @param category
     *  The parent category of the task
     * @param description
     *  The description of the task
     * @param deadline
     *  The deadline of the task
     * @param priority
     *  The priority of the task
     */
    public Task(Category category, String description, DateTime deadline, int priority) {
        super(category);
        this.description = description;
        this.isDone = false;
        this.deadline = deadline;
        this.priority = priority;
        this.files = new TaskFileManager();
        this.tags = new ArrayList<>();
    }

    public void setFiles(TaskFileManager files) {
        this.files = files;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return
     *  The description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return
     *  The deadline of the task
     */
    public DateTime getDeadline() {
        return deadline;
    }

    /**
     * Edit the deadline of the task.
     *
     * @param deadline
     *  The deadline of the task
     */
    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the priority of the task.
     *
     * @return
     *  The priority of the task
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns an icon representing the done status of the task.
     *
     * @return
     *  The icon representing the done status of the task
     */
    public String getStatusIcon() {
        return (isDone ? YES_ICON : NO_ICON);
    }

    /**
     * Returns the File List of the task.
     *
     * @return
     *  The File List of the task
     */
    public TaskFileManager getFiles() {
        return files;
    }

    @Override
    public Category getParent() {
        return (Category) this.parent;
    }

    /**
     * Checks if one task has the same description as another.
     *
     * @param taskDescription
     *  The task description to check
     * @return
     *  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameTask(String taskDescription) {
        return this.description.equals(taskDescription);
    }

    /**
     * Prints the task as a string.
     *
     * @return
     *  The string that contains all the task information
     */
    @Override
    public String toString() {
        return String.format("%s %s by %s\n Files:\n%s", getStatusIcon(), getDescription(), getDeadline(), files);
    }

    @Override
    public void setTag(String info) {
        this.tags.add(info);
    }

    @Override
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public void removeAllTags() {
        tags.clear();
    }

    @Override
    public String getTag() {
        return this.tags == null ? "" : tags.toString();
    }
}
