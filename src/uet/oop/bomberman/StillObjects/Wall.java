package uet.oop.bomberman.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends StillObject {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        time = 0;
    }

    public void TerminateProcess() {
        BombermanGame.map.getTILE_MAP()[yblock][xblock] = ' ';
        img = Sprite.movingSprite(Sprite.brick_exploded,
                Sprite.brick_exploded1,Sprite.brick_exploded2,time,30).getFxImage();
        time += 1;
        if(time >= 30){
            terminate = false;
        }
    }
}
