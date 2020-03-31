package seedu.nuke.gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class DirectoryTree extends TreeView<Label> {

    /**
     * Constructs the Directory Tree class.
     */
    public DirectoryTree() {
        super();
        setStyle("-fx-font-family: Consolas; -fx-font-size: 11pt; -fx-font-weight: bold; "
                + "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; "
                + "-fx-border-color: lightgrey; -fx-border-radius: 3;");
        setShowRoot(false);

        populateTree();
    }

    private void populateTree() {
        TreeItem<Label> root = new TreeItem<>(new Label("Root"));

        // Get and sort Module List
        ArrayList<Module> moduleList = ModuleManager.getModuleList();
        moduleList.sort(Comparator.comparing(Module::getModuleCode));

        for (Module module : moduleList) {
            // Add module to tree
            TreeItem<Label> moduleItem = new TreeItem<>();
            styleTreeItem(moduleItem, root, module.getModuleCode(), Color.SANDYBROWN);

            // Get and sort Category List
            ArrayList<Category> categoryList = module.getCategories().getCategoryList();
            categoryList.sort(Comparator.comparing(Category::getCategoryName));

            for (Category category : categoryList) {
                // Add category to tree
                TreeItem<Label> categoryItem = new TreeItem<>();
                styleTreeItem(categoryItem, moduleItem, category.getCategoryName(), Color.DARKSEAGREEN);

                // Get and sort Task List
                ArrayList<Task> taskList = category.getTasks().getTaskList();
                taskList.sort(Comparator.comparing(Task::getDescription));

                for (Task task : taskList) {
                    // Add task to tree
                    TreeItem<Label> taskItem = new TreeItem<>();
                    if (task.getDeadline() == null) {
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORNFLOWERBLUE);
                    } else if (task.isDone()) {
                        // Task is done but not deleted yet
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.LIGHTGRAY);
                    } else if (task.getDeadline().isDue()) {
                        // Highlight if task is due
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CRIMSON);
                        taskItem.getValue().setStyle("-fx-background-color: PeachPuff");
                    } else if (task.getDeadline().isBefore(LocalDate.now().plusDays(3))) {
                        // Highlight if task is near deadline (within 2 days)
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORAL);
                        taskItem.getValue().setStyle("-fx-background-color: Moccasin");
                    } else {
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORNFLOWERBLUE);
                    }
                }
            }
        }
        this.setRoot(root);
    }

    private void styleTreeItem(TreeItem<Label> treeItem, TreeItem<Label> parentItem, String name, Color color) {
        Label treeLabel = new Label(name);
        treeLabel.setTextFill(color);
        treeLabel.setPadding(new Insets(3));
        treeItem.setValue(treeLabel);
        treeItem.setExpanded(true);
        parentItem.getChildren().add(treeItem);
    }

    public void refresh() {
        populateTree();
    }
}
