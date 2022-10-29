package uet.oop.bomberman.menus;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.utils.ConstVar;
public class TextInGame extends Text {

    public TextInGame() {

    }

    public TextInGame(String content, double transX) {
        this.setText(content);
        this.setX(transX);
        this.setY(ConstVar.TILE_SIZE * ConstVar.HEIGHT + 25);
        this.setFont(Font.font("28 Days Later", FontWeight.BOLD, 25));
        this.setFill(Color.ORANGE);
    }

    public TextInGame(String content, double transX, double transY) {
        this.setText(content);
        this.setX(transX);
        this.setY(transY);
        this.setFont(Font.font("28 Days Later", FontWeight.BOLD, 50));
        this.setFill(Color.ORANGE);
    }

}