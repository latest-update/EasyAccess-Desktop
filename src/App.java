import Controllers.CardControl;
import Controllers.QRControl;
import Controllers.SceneControl;
import Controllers.StageControl;
import Core.WebSocket;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        WebSocket.init();
        QRControl.init();
        CardControl.init();
        SceneControl.init();

        StageControl.defaultStage(stage, SceneControl.main, "default");
        stage.show();

        cardScan(new Stage());
    }

    private void cardScan (Stage stage)
    {
        StageControl.defaultStage(stage, SceneControl.gate, "default-gate");
        stage.show();
    }

}
