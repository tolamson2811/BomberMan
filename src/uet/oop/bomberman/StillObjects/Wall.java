package uet.oop.bomberman.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends StillObject {

    public Wall(int x, int y, Image img) {
        super(x, y, img);
        time = 0;
    }

    public void TerminateProcess() {

        img = Sprite.movingSprite(Sprite.brick_exploded,
                Sprite.brick_exploded1,Sprite.brick_exploded2,time,24).getFxImage();
        time += 1;
        if(time >= 24){
            if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'I') {
                System.out.println("hi");
                BombermanGame.map.getTILE_MAP()[yblock][xblock] = 'i';
            }else {
                BombermanGame.map.getTILE_MAP()[yblock][xblock] = ' ';
            }
            terminate = false;
        }
    }
}
