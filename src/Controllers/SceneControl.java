package Controllers;

import Core.QR;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;

public class SceneControl {
    public static Scene main;
    public static Scene gate;

    public static void init ()
    {
        initMain();
        initGate();
    }

    private static void initMain ()
    {
        VBox box = new VBox(40);
        box.getChildren().addAll(QRControl.iv, InfoControl.text);
        box.setAlignment(Pos.CENTER);
        InfoControl.text.setTextAlignment(TextAlignment.CENTER);
        BorderPane bp = new BorderPane();
        bp.setCenter(box);
        bp.setBackground(Background.EMPTY);
        InfoControl.connection.setTranslateX(530);
        InfoControl.connection.setTranslateY(315);
        QRControl.ok.setTranslateY(-25);
        StackPane sp = new StackPane(bp, QRControl.ok, InfoControl.connection);

        main = new Scene(sp, 1200, 700);
    }

    private static void initGate ()
    {
        BorderPane bp = new BorderPane();
        bp.setCenter(InfoControl.cardReader);
        CardControl.ok.setTranslateY(-60);
        CardControl.ok.setTranslateX(185);
        StackPane sp = new StackPane(bp, CardControl.ok);

        gate = new Scene(sp, 400, 150);

        gate.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        gate.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                        try {
                            new CardControl(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });


    }
}
