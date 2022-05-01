package Core;

import App.QrData;
import App.ScanResponse;
import Controllers.AdditionControl;
import Controllers.CardControl;
import Controllers.InfoControl;
import Controllers.QRControl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.WriterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApi {
    private static String gateKey = "kSaa12rNVXCsd";
    private static String homeUrl = "https://easystemness.herokuapp.com/api/";

    public static void init() throws IOException, WriterException {
        try {
            URL url = new URL(homeUrl + "getTtid/" + gateKey);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            QrData data = gson.fromJson(content.toString(), QrData.class);

            QRControl.scanned(data.code);
        } catch (Exception e) {
            System.out.println(e);
            InfoControl.setConnection("DISCONNECTED");
        }

    }

    public static void cardScanned(String cardData) throws IOException {
        URL url = new URL (homeUrl + "scan");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = cardData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ScanResponse data = gson.fromJson(response.toString(), ScanResponse.class);

            System.out.println(response.toString());
            if (data.user_name != null) {
                new Thread(() -> {
                    try {
                        CardControl.ok.imageProperty().set(CardControl.okImage);
                        Thread.sleep(3000);
                        CardControl.ok.imageProperty().set(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            AdditionControl.replaceTextForAWhile(InfoControl.cardReader, "Hi! " + data.user_name, 3);

        }

    }

}
