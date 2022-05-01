package Core;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QR {
    private static String charset = "UTF-8";
    private static String data = "https://heroku-app/easyaccess/DAS3242IJODs";
    public static String path = "src/Resources/QR.png";
    public static String okPath = "src/Resources/OK.png";
    public static String temporaryQr = "src/Resources/QR-REP.jpg";

    private static void createQR(Map hashMap) throws WriterException, IOException
    {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(QR.data.getBytes(QR.charset), QR.charset),
                BarcodeFormat.QR_CODE, 1200, 1200);

        MatrixToImageWriter.writeToFile(
                matrix,
                QR.path.substring(QR.path.lastIndexOf('.') + 1),
                new File(QR.path));
    }

    public static void execQR () throws IOException, WriterException {
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        createQR(hashMap);
        System.out.println("QR Code Generated!!!");
    }

    public static void newQR (String data) throws IOException, WriterException {
        QR.data = data;
        execQR();
    }

}
