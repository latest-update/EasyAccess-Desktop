package Controllers;

import Core.QR;
import Core.RestApi;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Scanner;

public class CardControl {
    public static String okPath = "src/Resources/OK-CIRCLE.png";
    public static ImageView ok = new ImageView();
    public static Image okImage;

    static {
        try {
            okImage = new Image(new FileInputStream(okPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init ()
    {
        ok.setFitHeight(20);
        ok.setFitWidth(20);
    }

    public CardControl (String filePath) throws IOException {
        try {
            StringBuilder data = new StringBuilder();
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }
            myReader.close();
            RestApi.cardScanned(data.toString().replaceAll("[\r\n]+", " "));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
