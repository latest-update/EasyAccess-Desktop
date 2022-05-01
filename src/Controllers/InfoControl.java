package Controllers;

import Core.RestApi;
import com.google.zxing.WriterException;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class InfoControl {
    public static Text text = new Text();
    public static Text connection = new Text();
    public static Text cardReader = new Text("Apply your ID card");

    public static void setCardReader (String info)
    {
        cardReader.setText(info);
    }

    public static void setInfo (String info)
    {
        text.setText(info);
    }

    public static void setConnection(String info) throws IOException, WriterException {
        connection.setText(info);
        setStatusOfConnection(info);
    }

    private static void setStatusOfConnection(String status) throws IOException, WriterException {
        switch (status)
        {
            case "CONNECTING":
            case "RECONNECTING":
                text.setText("Please wait to connection");
                connection.setFill(Color.DARKORANGE);
                break;

            case "CONNECTED":
                RestApi.init();
                text.setText("Scan QR to record your time");
                connection.setFill(Color.GREEN);
                break;

            default:
                text.setText("Not available to record your time via QR \n Use a ID card");
                connection.setFill(Color.RED);
        }
    }
}
