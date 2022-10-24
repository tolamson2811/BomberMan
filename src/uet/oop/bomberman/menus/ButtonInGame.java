package uet.oop.bomberman.menus;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ButtonInGame extends Button {
    public ButtonInGame() {

    }

    public ButtonInGame(String title, double transX, double transY, double width, double height, Font font, String backgroundColor) {
        this.setText(title);
        this.setTranslateX(transX);
        this.setTranslateY(transY);
        this.setPrefSize(width, height);
        this.setStyle(backgroundColor);
        this.setFont(font);
        this.setTextFill(Color.WHITE);
        this.setOnMouseEntered(e -> this.setTextFill(Color.RED));
        this.setOnMouseExited(e -> this.setTextFill(Color.WHITE));
    }

    public ButtonInGame(String title, double transX, double transY, double width, double height, Font font, Paint color) {
        this.setText(title);
        this.setTranslateX(transX);
        this.setTranslateY(transY);
        this.setPrefSize(width, height);
        this.setStyle("-fx-background-color: #000;");
        this.setFont(font);
        this.setTextFill(color);
    }

}
