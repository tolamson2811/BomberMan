package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;

import java.lang.reflect.Type;

public class Flame extends Entity {

    enum TYPE {
        HORIONTAL, VERTICAL, TOP, DOWN, LEFT, RIGHT, CENTER
    }

    private TYPE flametype;
    private int time;
    private final Sprite[] horizontal = {Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2};
    private final Sprite[] vertical = {Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2};
    private final Sprite[] top = {Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2};
    private final Sprite[] down = {Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2};
    private final Sprite[] left = {Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2};
    private final Sprite[] right = {Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2};
    private final Sprite[] center = {Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2};

    public Flame(int xUnit, int yUnit, TYPE a) {
        super(xUnit, yUnit);
        flametype = a;
    }

    public void Animation() {
        int i =1;
        if (flametype == TYPE.HORIONTAL) {
            int diff = time % 30;
            img = horizontal[diff / 10].getFxImage();
            
        }
        if (flametype == TYPE.VERTICAL) {

            int diff = time % 30;
            img = vertical[diff / 10].getFxImage();
            
        }
        if (flametype == TYPE.TOP) {

            int diff = time % 30;
            img = top[diff / 10].getFxImage();
            

        }
        if (flametype == TYPE.DOWN) {

            int diff = time % 30;
            img = down[diff / 10].getFxImage();
            
        }
        if (flametype == TYPE.CENTER) {

            int diff = time % 30;
            img = center[diff / 10].getFxImage();
            
        }
        if (flametype == TYPE.LEFT) {

            int diff = time % 30;
            img = left[diff / 10].getFxImage();
            
        }
        if (flametype == TYPE.RIGHT) {

            int diff = time % 30;
            img = right[diff / 10].getFxImage();
            
        }
        if(time > 30) {
            i = -1;
        }
        time+= i;

    }


    @Override
    public void update() {
        Animation();
    }
}
