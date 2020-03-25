package seedu.nuke.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleLoader;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.directory.Root;
import seedu.nuke.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class InfiNus extends Application {

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Load modules and tasks
        Root root = new Root();
        Ui ui = new Ui();
        HashMap<String, String> modulesMap = ModuleLoader.load("moduleList.json");
        StorageManager storageManager = new StorageManager("save.txt");
        ModuleManager moduleManager = ModuleManager.getInstance(root, modulesMap);
        storageManager.load2();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("INFI \u2022 NUS");
        stage.getIcons().add(new Image("images/venus_icon.png"));
        stage.setMinWidth(1200);
        stage.setMinHeight(700);

        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent mainRoot = sceneLoader.load();
        Scene main = new Scene(mainRoot);

        stage.setScene(main);
        stage.show();
    }
}
