package Controllers;


import Core.QR;
import com.google.zxing.WriterException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QRControl {
    public static ImageView iv = new ImageView();
    public static ImageView ok = new ImageView();
    public static Image okImage;
    private static Image image;

    static {
        try {
            image = new Image(new FileInputStream(QR.temporaryQr));
            okImage = new Image(new FileInputStream(QR.okPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init ()
    {
        iv.setFitHeight(400);
        iv.setFitWidth(400);
        iv.setImage(image);
        ok.setFitWidth(295);
        ok.setFitHeight(237);
    }

    public static void scanned (String newData) throws IOException, WriterException {
        QR.newQR(newData);
        image = new Image(new FileInputStream(QR.path));
        iv.setImage(image);
    }
}
