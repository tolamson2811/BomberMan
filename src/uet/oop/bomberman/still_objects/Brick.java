package uet.oop.bomberman.still_objects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends StillObject{
    public Brick(int xUnit, int yUnit,int level) {
        super(xUnit, yUnit);
        time = 0;
        if(level == 1) {
            img = Sprite.brick.getFxImage();
        }else if(level == 2) {
            img = Sprite.lava_brick.getFxImage();

        }else{
            img = Sprite.ice_brick.getFxImage();

        }

    }

    public void TerminateProcess() {

        img = Sprite.movingSprite(Sprite.brick_exploded,
                Sprite.brick_exploded1,Sprite.brick_exploded2,time,24).getFxImage();
        time += 1;
        if(time >= 24){
            if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'I') {
                System.out.println("hello");
                BombermanGame.map.setTILE_MAP(yblock,xblock,'i');
            }else if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'X') {
                System.out.println("hwe");
                BombermanGame.map.setTILE_MAP(yblock, xblock, 'x');
            }else {
                BombermanGame.map.setTILE_MAP(yblock,xblock,' ');
            }
            terminate = false;
        }
    }
}
