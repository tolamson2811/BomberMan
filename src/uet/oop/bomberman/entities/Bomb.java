package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity{
    private double radius;
    private double time;
    private boolean active;
    private boolean explode;

    public double getRadius() {
        return radius;
    }



    public Bomb(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        w = img.getWidth();
        h = img.getHeight();
        active = false;
        explode = false;
        radius = 1;
        time =0 ;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }
    public void placeBomb() {
        if (time % 30 == 10) {
            img = Sprite.bomb_1.getFxImage();
        } else if (time % 30 == 20) {
            img = Sprite.bomb_2.getFxImage();
        } else if (time % 30 == 29) {
            img = Sprite.bomb.getFxImage();
        }
        if(time < 70) {
            time += 0.5;
        }else{
            time  = 0;
            active = false;
            //Explode();
        }
        //System.out.println("je");
    }
    public void Explode() {
        if (time % 30 == 10) {
            img = Sprite.bomb_1.getFxImage();
        } else if (time % 30 == 20) {
            img = Sprite.bomb_2.getFxImage();
        } else if (time % 30 == 29) {
            img = Sprite.bomb.getFxImage();
        }
        if (time % 30 == 10) {
            img = Sprite.bomb_1.getFxImage();
        } else if (time % 30 == 20) {
            img = Sprite.bomb_2.getFxImage();
        } else if (time % 30 == 29) {
            img = Sprite.bomb.getFxImage();
        }
        if (time % 30 == 10) {
            img = Sprite.bomb_1.getFxImage();
        } else if (time % 30 == 20) {
            img = Sprite.bomb_2.getFxImage();
        } else if (time % 30 == 29) {
            img = Sprite.bomb.getFxImage();
        }
    }

    @Override
    public void update() {
        placeBomb();

    }
}