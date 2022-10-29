package uet.oop.bomberman.still_objects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends StillObject {


    public Wall(int x, int y, int level) {
        super(x, y);
        if(level == 1) {
            img = Sprite.wall.getFxImage();
        }else if(level == 2) {
            img = Sprite.lava_wall.getFxImage();

        }else{
            img = Sprite.ice_wall.getFxImage();
        }
    }



}
