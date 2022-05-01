package Controllers;


import javafx.scene.text.Text;

public class AdditionControl {
    public static void replaceTextForAWhile (Text text, String temporaryText, int sec) {
        String sourceText = text.getText();
        new Thread(() -> {
           try {
               text.setText(temporaryText);
               Thread.sleep(sec * 1000);
               text.setText(sourceText);
           } catch (Exception e) {
               e.printStackTrace();
           }
        }).start();
    }
}
