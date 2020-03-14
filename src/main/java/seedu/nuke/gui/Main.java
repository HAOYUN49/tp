package seedu.nuke.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Load modules and tasks
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("V \u2022 NUS");
        stage.getIcons().add(new Image("images/venus_icon.png"));
        stage.setMinWidth(1000);
        stage.setMinHeight(600);

        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent mainRoot = sceneLoader.load();
        Scene main = new Scene(mainRoot);

        stage.setScene(main);
        stage.show();
    }
}
