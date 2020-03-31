package seedu.nuke.data;

import seedu.nuke.directory.Category;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Root;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.exception.DuplicateDataException;
import seedu.nuke.exception.ModuleNotProvidedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A manager that manages all modules.
 * Contains a Module List and performs operations related to modules
 */
public class ModuleManager implements Iterable<Module> {
    private static ModuleManager moduleManager = null;
    private static Root root;
    private static ArrayList<Module> moduleList;
    private static HashMap<String, String> modulesMap;

    private static final String NO_KEYWORD = "";

    private ModuleManager(Root root, HashMap<String, String> modulesMap) {
        ModuleManager.root = root;
        ModuleManager.modulesMap = modulesMap;
        moduleList = new ArrayList<>();
    }

    /**
     * Returns the instance of the Module Manager class.
     *
     * @param root
     *  The root of the Directory Tree
     * @param modulesMap
     *  The modules provided by NUS
     * @return
     *  The instance of the Module Manager
     */
    public static ModuleManager getInstance(Root root, HashMap<String, String> modulesMap) {
        if (moduleManager == null) {
            moduleManager = new ModuleManager(root, modulesMap);
        }
        return moduleManager;
    }

    public static Root getRoot() {
        return root;
    }

    public static HashMap<String, String> getModulesMap() {
        return modulesMap;
    }

    /**
     * Sets the entire Module List to a new list.
     *
     * @param moduleList
     *  The new Module List to be set
     */
    public static void setModuleList(ArrayList<Module> moduleList) {
        ModuleManager.moduleList = moduleList;
    }

    /**
     * method to return all the modules.
     * @return all modules
     */
    public static ArrayList<Module> getModuleList() {
        return moduleList;
    }

    /**
     *  Finds a module with the specified module code in the Module List.
     *
     * @param moduleCode
     *  The module code of the module to be found
     * @return
     *  The found module with the specified module code
     * @throws ModuleNotFoundException
     *  If the module is not found in the Module List
     */
    public static Module getModule(String moduleCode) throws ModuleNotFoundException {
        for (Module module : moduleList) {
            if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {
                return module;
            }
        }
        throw new ModuleNotFoundException();
    }

    /**
     * Searches the Module List for the module with the specified module code, then searches the module's
     * Category List for the category with the specified name.
     *
     * @param moduleCode
     *  The module code of the module to be found
     * @param categoryName
     *  The name of the category to be found
     * @return
     *  The found category with the specified name, with its parent module with the specified module code
     * @throws ModuleNotFoundException
     *  If the module is not found
     * @throws CategoryManager.CategoryNotFoundException
     *  If the category is not found
     */
    public static Category getCategory(String moduleCode, String categoryName)
            throws ModuleNotFoundException, CategoryManager.CategoryNotFoundException {
        return getModule(moduleCode).getCategories().getCategory(categoryName);
    }

    /**
     * Searches the Module List for the module with the specified module code, then searches the module's
     * Category List for the category with the specified name, then searches the category's Task List for
     * the task with the specified description.
     *
     * @param moduleCode
     *  The module code of the module to be found
     * @param categoryName
     *  The name of the category to be found
     * @param taskDescription
     *  The description of the task to be found
     * @return
     *  The found task with the specified description, with its parent category and module with the specified
     *      module code and category name respectively
     * @throws ModuleNotFoundException
     *  If the module is not found
     * @throws CategoryManager.CategoryNotFoundException
     *  If the category is not found
     * @throws TaskManager.TaskNotFoundException
     *  If the task is not found
     */
    public static Task getTask(String moduleCode, String categoryName, String taskDescription)
            throws ModuleNotFoundException, CategoryManager.CategoryNotFoundException,
            TaskManager.TaskNotFoundException {
        return getCategory(moduleCode, categoryName).getTasks().getTask(taskDescription);
    }

    /**
     * Searches the Module List for the module with the specified module code, then searches the module's
     * Category List for the category with the specified name, then searches the category's Task List for
     * the task with the specified description, then searches the task's File List for the file with the
     * specified name.
     *
     * @param moduleCode
     *  The module code of the module to be found
     * @param categoryName
     *  The name of the category to be found
     * @param taskDescription
     *  The description of the task to be found
     * @return
     *  The found task with the specified description, with its parent category and module with the specified
     *      module code and category name respectively
     * @throws ModuleNotFoundException
     *  If the module is not found
     * @throws CategoryManager.CategoryNotFoundException
     *  If the category is not found
     * @throws TaskManager.TaskNotFoundException
     *  If the task is not found
     * @throws TaskFileManager.TaskFileNotFoundException
     *  If the file is not found
     */
    public static TaskFile getFile(String moduleCode, String categoryName, String taskDescription, String fileName)
            throws ModuleNotFoundException, CategoryManager.CategoryNotFoundException,
            TaskManager.TaskNotFoundException, TaskFileManager.TaskFileNotFoundException {
        return getTask(moduleCode, categoryName, taskDescription).getFiles().getFile(fileName);
    }

    /**
     * Checks for duplicates of the same module code in the Module List.
     * @param moduleCode
     *  The module code to check
     * @return
     *  <code>TRUE</code> if there exists a duplicate, and <code>FALSE</code> otherwise
     */
    public static boolean contains(String moduleCode) {
        for (Module module : moduleList) {
            if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a module to the Module List.
     *
     * @param toAdd
     *  The module to be added
     */
    public static void add(Module toAdd) throws DuplicateModuleException, ModuleNotProvidedException {
        //check duplicate
        if (contains(toAdd.getModuleCode())) {
            throw new DuplicateModuleException();
        } else if (modulesMap.size() > 0 && !modulesMap.containsKey(toAdd.getModuleCode())) {
            throw new ModuleNotProvidedException();
        } else {
            String moduleTitle = modulesMap.get(toAdd.getModuleCode());
            toAdd.setTitle(moduleTitle);
            moduleList.add(toAdd);
        }
    }

    /**
     * return an orders lists of all tasks of all modules.
     * @return an ArrayList of String which represents the ordered list
     */
    public static ArrayList<String> checkDeadline() {
        ArrayList<String> deadlines = new ArrayList<>();
        ArrayList<Task> allTasks = sortAllTasks();
        for (Task task: allTasks) {
            deadlines.add(String.format("%-30s", task.getDescription()) + " "
                    + String.format("%-8s", task.getParent().getParent().getModuleCode())
                    + String.format("%-10s", task.getParent().getCategoryName())
                    + "   deadline: " + task.getDeadline().toShow());
        }
        return deadlines;
    }

    /**
     * sort all tasks of all modules in ascending order of deadlines.
     * @return list of sorted tasks
     */
    public static ArrayList<Task> sortAllTasks() {
        ArrayList<Task> allTasks = getAllTasks();
        Collections.sort(allTasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                String t1Deadline = t1.getDeadline() == null ? "" : t1.getDeadline().getDateTimeSortFormat();
                String t2Deadline = t2.getDeadline() == null ? "" : t2.getDeadline().getDateTimeSortFormat();
                return t1Deadline.compareToIgnoreCase(t2Deadline);
            }
        });
        return allTasks;
    }

    /**
     * Clears all tasks in list.
     */
    public void clear() {
        moduleList.clear();
    }

    /**
     * Delete a module to the Module List.
     * @param toDelete
     *  The module to be deleted
     */
    public static void delete(Module toDelete) {
        moduleList.remove(toDelete);
    }

    /**
     * Deletes a <b>Module</b> with <code>module code</code> in the <b>Module List</b>.
     *
     * @param moduleCode
     *  The module code of the <b>Module</b> to be deleted
     * @throws ModuleNotFoundException
     *  If the module with the specified module code is not found in the <b>Module List</b>
     * @see Module
     */
    public Module delete(String moduleCode) throws ModuleNotFoundException {
        if (getModuleWithCode(moduleCode) != null) {
            Module toDelete = getModuleWithCode(moduleCode);
            getAllTasks().removeIf(task ->
                    task.getParent().getParent().getModuleCode().toUpperCase().equals(moduleCode));
            moduleList.removeIf(module -> module.getModuleCode().equalsIgnoreCase(moduleCode));
            return toDelete;
        } else {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Edits a module in the Module List.
     *
     * @param toEdit
     *  The module to be edited
     * @param newModuleCode
     *  The new module code of the module
     * @throws ModuleNotProvidedException
     *  If there is no module with the new module code offered by NUS
     * @throws DuplicateModuleException
     *  If there are duplicate modules with the same module code as the new module code in the Module List
     */
    public static void edit(Module toEdit, String newModuleCode)
            throws ModuleNotProvidedException, DuplicateModuleException {
        if (!modulesMap.containsKey(newModuleCode)) {
            throw new ModuleNotProvidedException();
        }
        if (!toEdit.isSameModule(newModuleCode) && contains(newModuleCode)) {
            throw new DuplicateModuleException();
        }
        String newTitle = modulesMap.get(newModuleCode);
        toEdit.setModuleCode(newModuleCode);
        toEdit.setTitle(newTitle);
    }

    /* Retrieve a specific Data (Category / Task / File) List. Only 1 list is retrieved */

    /**
     * Retrieves the Category List of the module with the specified module code.
     *
     * @param moduleCode
     *  The module code of the module to retrieve the Category List from
     * @return
     *  The Category List of the found module
     * @throws ModuleNotFoundException
     *  If the module with the specified module code is not found in the Module List
     */
    public static CategoryManager retrieveList(String moduleCode) throws ModuleNotFoundException {
        return getModule(moduleCode).getCategories();
    }

    /**
     * Retrieves the Task List of the category with the specified name and has its parent module with the
     * specified module code.
     *
     * @param moduleCode
     *  The module code of the module containing the category to retrieve the Task List from
     * @param categoryName
     *  The name of the category to retrieve the Task List from
     * @return
     *  The Task List of the found category
     * @throws ModuleNotFoundException
     *  If the module with the specified module code is not found in the Module List
     * @throws CategoryManager.CategoryNotFoundException
     *  If the category with the specified name is not found in the Category List
     */
    public static TaskManager retrieveList(String moduleCode, String categoryName)
            throws ModuleNotFoundException, CategoryManager.CategoryNotFoundException {
        return retrieveList(moduleCode).retrieveList(categoryName);
    }

    /**
     * Retrieves the File List of the task with the specified description, and has its parent category with the
     * specified name and parent module with the specified module code.
     *
     * @param moduleCode
     *  The module code of the module containing the category to retrieve the File List from
     * @param categoryName
     *  The name of the category to retrieve the File List from
     * @param taskDescription
     *  The description of the task to retrieve the File List from
     * @return
     *  The Task List of the found category
     * @throws ModuleNotFoundException
     *  If the module with the specified module code is not found in the Module List
     * @throws CategoryManager.CategoryNotFoundException
     *  If the category with the specified name is not found in the Category List
     * @throws TaskManager.TaskNotFoundException
     *  If the task with the specified description is not found in the Task List
     */
    public static TaskFileManager retrieveList(String moduleCode, String categoryName, String taskDescription)
            throws ModuleNotFoundException, CategoryManager.CategoryNotFoundException,
            TaskManager.TaskNotFoundException {
        return retrieveList(moduleCode, categoryName).retrieveList(taskDescription);
    }

    /* Filters for data (module / task / category) that *contains* given keywords (i.e. not exact match)
     *  in a case-insensitive manner. There may be multiple data that matches. */

    /**
     * Filter for modules in the Module List with module code that contains the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @return
     *  The list of filtered modules
     */
    public static ArrayList<Module> filter(String moduleKeyword) {
        ArrayList<Module> filteredModuleList = new ArrayList<>();
        for (Module module : moduleList) {
            if (module.getModuleCode().toLowerCase().contains(moduleKeyword.toLowerCase())) {
                filteredModuleList.add(module);
            }
        }
        return filteredModuleList;
    }

    /**
     * Filter for modules in the Module List with module code that contains the specified module keyword,
     * then for categories in the Category List of the filtered modules with name that contains the specified
     * category keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @return
     *  The list of filtered categories
     */
    public static ArrayList<Category> filter(String moduleKeyword, String categoryKeyword) {
        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Module module : filter(moduleKeyword)) {
            filteredCategoryList.addAll(module.getCategories().filter(categoryKeyword));
        }
        return filteredCategoryList;
    }

    /**
     * Filter for modules in the Module List with module code that contains the specified module keyword,
     * then for categories in the Category List of the filtered modules with name that contains the specified
     * category keyword, then for tasks in the Task List of the filtered categories with description that
     * contains the specified task keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     * The keyword to filter the tasks
     * @return
     *  The list of filtered tasks
     */
    public static ArrayList<Task> filter(String moduleKeyword, String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filter(moduleKeyword, categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filter(taskKeyword));
        }
        return filteredTaskList;
    }

    /**
     * Filter for modules in the Module List with module code that contains the specified module keyword,
     * then for categories in the Category List of the filtered modules with name that contains the specified
     * category keyword, then for tasks in the Task List of the filtered categories with description that
     * contains the specified task keyword, then for files in the File List of the filtered tasks with name
     * that contains the specified file keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @param fileKeyword
     *  The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public static ArrayList<TaskFile> filter(String moduleKeyword, String categoryKeyword, String taskKeyword,
            String fileKeyword) {
        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (Task task : filter(moduleKeyword, categoryKeyword, taskKeyword)) {
            filteredFileList.addAll(task.getFiles().filter(fileKeyword));
        }
        return filteredFileList;
    }

    /* Filters for data (module / task / category) that *matches exactly* the given keywords in a case-insensitive
     *  manner. Empty keywords, however, will instead collect all instances of the data.
     *  There may be multiple data that matches. */

    /**
     * Filter for modules in the Module List with module code that matches <b>exactly</b> the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @return
     *  The list of filtered modules
     */
    public static ArrayList<Module> filterExact(String moduleKeyword) {
        // Returns all modules in the Module List if no keyword is provided.
        if (moduleKeyword.equals(NO_KEYWORD)) {
            return moduleList;
        }

        ArrayList<Module> filteredModuleList = new ArrayList<>();
        for (Module module : moduleList) {
            if (module.getModuleCode().toLowerCase().equals(moduleKeyword.toLowerCase())) {
                filteredModuleList.add(module);
            }
        }
        return filteredModuleList;
    }

    /**
     * Filter for modules in the Module List with module code that matches <b>exactly</b> the specified module
     * keyword, then for categories in the Category List of the filtered modules with name that that matches
     * <b>exactly</b> the specified category keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @return
     *  The list of filtered categories
     */
    public static ArrayList<Category> filterExact(String moduleKeyword, String categoryKeyword) {
        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Module module : filterExact(moduleKeyword)) {
            filteredCategoryList.addAll(module.getCategories().filterExact(categoryKeyword));
        }
        return filteredCategoryList;
    }

    /**
     * Filter for modules in the Module List with module code that matches <b>exactly</b> the specified module
     * keyword, then for categories in the Category List of the filtered modules with name that that matches
     * <b>exactly</b> the specified category keyword, then for tasks in the Task List of the filtered categories
     * with description that that matches <b>exactly</b> the specified task keyword
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @return
     *  The list of filtered tasks
     */
    public static ArrayList<Task> filterExact(String moduleKeyword, String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filterExact(moduleKeyword, categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filterExact(taskKeyword));
        }
        return filteredTaskList;
    }

    /**
     * Filter for modules in the Module List with module code that matches <b>exactly</b> the specified module
     * keyword, then for categories in the Category List of the filtered modules with name that that matches
     * <b>exactly</b> the specified category keyword, then for tasks in the Task List of the filtered categories
     * with description that that matches <b>exactly</b> the specified task keyword, then for files in the File List
     * that matches <b>exactly</b> the specified file keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param moduleKeyword
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @param fileKeyword
     *  The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public static ArrayList<TaskFile> filterExact(String moduleKeyword, String categoryKeyword, String taskKeyword,
            String fileKeyword) {
        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (Task task : filterExact(moduleKeyword, categoryKeyword, taskKeyword)) {
            filteredFileList.addAll(task.getFiles().filterExact(fileKeyword));
        }
        return filteredFileList;
    }

    /**
     * Returns all the tasks across the entire Module List.
     *
     * @return
     *  An Array List of all the tasks in the entire Module List
     */
    public static ArrayList<Task> getAllTasks() {
        return filter(NO_KEYWORD, NO_KEYWORD, NO_KEYWORD);
    }

    @Override
    public Iterator<Module> iterator() {
        return moduleList.iterator();
    }

    //public void addTaskToModule(TaskManager taskManager, Task taskToAdd) throws TaskManager.DuplicateTaskException {
    //    taskManager.add(taskToAdd);
    //    allTasks.add(taskToAdd);
    //}
    //
    //public void removeTask(TaskManager taskManager, Task taskToDelete) {
    //    taskManager.delete(taskToDelete);
    //    allTasks.remove(taskToDelete);
    //}


    /**
     * get a module object according to moduleCode.
     * @param moduleCode the moduleCode of the module
     * @return a module object that has the moduleCode
     */
    public static Module getModuleWithCode(String moduleCode) {
        for (Module module: moduleList) {
            if (module.getModuleCode().equals(moduleCode)) {
                assert module.getModuleCode().equals(moduleCode);
                return module;
            }
        }
        return null;
    }

    /**
     * return total number of tasks in all modules.
     */
    public static int countAllTasks() {
        return filter(NO_KEYWORD, NO_KEYWORD, NO_KEYWORD).size();
    }

    public static class ModuleNotFoundException extends DataNotFoundException {
    }

    public static class DuplicateModuleException extends DuplicateDataException {
    }
}
