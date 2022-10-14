package uet.oop.bomberman.StillObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Items extends StillObject{

    public enum TYPE {
        SPEED,BOMB,FLAME,FLAMEPASS,BOMBPASS,WALLPASS,DETONATOR
    }

    private TYPE type;
    public Items(int xUnit, int yUnit,TYPE type) {
        super(xUnit, yUnit);
        this.type = type;
        setImg();
    }
    public void setImg() {
        switch (type) {
            case BOMB:
                img = Sprite.powerup_bombs.getFxImage();
                break;
            case SPEED:
                img = Sprite.powerup_speed.getFxImage();
                break;
            case FLAME:
                img = Sprite.powerup_flames.getFxImage();
                break;
            case FLAMEPASS:
                img = Sprite.powerup_flamepass.getFxImage();
                break;
            case BOMBPASS:
                img = Sprite.powerup_bombpass.getFxImage();
                break;
            case WALLPASS:
                img = Sprite.powerup_wallpass.getFxImage();
                break;
            case DETONATOR:
                img = Sprite.powerup_detonator.getFxImage();
                break;
        }
    }
    public void takeEffect(Entity e) {
        switch (type) {
            case BOMB:
                if (e instanceof Bomber) {
                    ((Bomber) e).plusBOMB_NUMBER();
                }
                break;
            case SPEED:
                if (e instanceof Bomber) {
                    ((Bomber) e).plusBOMBER_SPEED();
                    e.getStopWatch().start();
                    e.setBoosted(true);
                }
                break;
            case FLAME:
                if (e instanceof Bomber) {
                    ((Bomber) e).plusB_radius();
                }
                break;
            case BOMBPASS:
                e.setBom_pass(true);
                e.getStopWatch().start();
                e.setBoosted(true);
                break;
            case WALLPASS:
                e.setWall_pass(true);
                e.getStopWatch().start();
                e.setBoosted(true);
                break;
            case FLAMEPASS:
                e.setFlame_pass(true);
                e.getStopWatch().start();
                e.setBoosted(true);
                break;

        }
    }


}
