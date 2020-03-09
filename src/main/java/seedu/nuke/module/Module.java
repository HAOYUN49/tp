package seedu.nuke.module;

import seedu.nuke.task.Task;

import java.util.ArrayList;

public class Module {
    private String moduleCode;
    private String title;
    private String description;
    private ArrayList<Task> moduleTasks;

    /**
     * initialize a module with module code, title, and description
     * @param moduleCode
     * @param title
     * @param description
     */
    public Module(String moduleCode, String title, String description) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleTasks = new ArrayList<Task>();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Task> getStuffs() {
        return moduleTasks;
    }

    public boolean isSameModule(Module module) {
        return this.moduleCode.equals(module.moduleCode);
    }
}