package chapter2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.accessibility.Action;
import javafx.scene.control.Button;

/**
 * Created by simjunbo on 2018-02-12.
 */
public class GUIEvent {
    public static void main(String[] args) {
        Button button = new Button("Send");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //label.setText("Sent!!");
            }
        });

        // 람다 방식으로
        //button.setOnAction((Action event) -> label.setText("Sent!!"));
    }
}
