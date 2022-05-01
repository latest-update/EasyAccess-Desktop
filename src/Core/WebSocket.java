package Core;

import App.QrData;
import Controllers.InfoControl;
import Controllers.QRControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.zxing.WriterException;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.io.IOException;

public class WebSocket {
    private static String cluster = "ap1";
    private static String apiKey = "12e69ce1dc1e71cd52c5";
    private static String channelName = "EasyAccess";
    private static String name = "code-channel";

    public static void init()
    {
        PusherOptions options = new PusherOptions();
        options.setCluster(cluster);
//        options.setActivityTimeout((long)10000L);
//        options.setPongTimeout((long)5000L);

        Pusher pusher = new Pusher(apiKey, options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed from " + change.getPreviousState() +
                        " to " + change.getCurrentState());
                try {
                    InfoControl.setConnection(change.getCurrentState().toString());
                } catch (IOException | WriterException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting! " +
                        "\ncode: " + code +
                        "\nmessage: " + message +
                        "\nException: " + e
                );
            }
        }, ConnectionState.ALL);

        Channel channel = pusher.subscribe(channelName);

        channel.bind(name, new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                QrData data = gson.fromJson(event.getData(), QrData.class);

                try {
                    if (data.user_name != null) {
                        new Thread(() -> {
                            try {
                                QRControl.ok.imageProperty().set(QRControl.okImage);
                                Thread.sleep(3000);
                                QRControl.ok.imageProperty().set(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                    QRControl.scanned(data.code);

                } catch (IOException | WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
