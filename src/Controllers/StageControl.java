package Controllers;

import App.WindowNames;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageControl {
    public static Stage main;
    public static Stage gate;

    public static Stage defaultStage (Stage stage, Scene scene, String windowName)
    {
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(WindowNames.windowName(windowName));
        return stage;
    }
}
