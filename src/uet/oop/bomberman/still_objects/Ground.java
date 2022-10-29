package uet.oop.bomberman.still_objects;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Ground extends StillObject {

    public Ground(int x, int y,int level) {
        super(x, y);
        if(level == 1) {
            img = Sprite.grass.getFxImage();
        }else if(level == 2) {
            img = Sprite.lava_gr.getFxImage();
        } else if (level == 3) {
            img = Sprite.ice_gr.getFxImage();
        }
    }

}
