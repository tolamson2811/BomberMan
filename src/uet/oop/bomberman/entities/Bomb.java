package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private double radius;
    private double time;
    private boolean active;
    private boolean explode;
    private final List<Flame> explosion = new ArrayList<>();

    public double getRadius() {
        return radius;
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        w = (int) img.getWidth();
        h = (int) img.getHeight();
        active = false;
        explode = false;
        radius = 2;
        time = 0;
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

    public void placeBomb(boolean[][] MAP_COLLISIONS) {
        MAP_COLLISIONS[yblock][xblock] = true;
        img = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, time, 30).getFxImage();
        System.out.println(xblock + " " + yblock);
        if (time < 70) {
            time += 0.5;
        } else {
            time = 0;
            active = false;
            int spread = 1;
            MAP_COLLISIONS[yblock][xblock] = false;
            explosion.add(new Flame(xblock, yblock, Flame.TYPE.CENTER));
            //check left to add explosion
            while (spread <= radius && !MAP_COLLISIONS[yblock][xblock + spread]) {

                if (spread == radius) {
                    explosion.add(new Flame(xblock + spread, yblock, Flame.TYPE.RIGHT));
                } else {
                    explosion.add(new Flame(xblock + spread, yblock, Flame.TYPE.HORIONTAL));
                }
                spread++;
            }
            spread = 1;
            while (spread <= radius &&!MAP_COLLISIONS[yblock][xblock - spread] ) {

                if (spread == radius) {
                    explosion.add(new Flame(xblock - spread, yblock, Flame.TYPE.LEFT));
                } else {
                    explosion.add(new Flame(xblock - spread, yblock, Flame.TYPE.HORIONTAL));
                }
                spread++;
            }
            spread = 1;
            while (spread <= radius && !MAP_COLLISIONS[yblock + spread][xblock]  ) {

                if (spread == radius) {
                    explosion.add(new Flame(xblock, yblock + spread, Flame.TYPE.DOWN));
                } else {
                    explosion.add(new Flame(xblock, yblock + spread, Flame.TYPE.VERTICAL));
                }
                spread++;
            }
            spread = 1;
            while (spread <= radius && !MAP_COLLISIONS[yblock -spread][xblock]) {

                if (spread == radius) {
                    explosion.add(new Flame(xblock, yblock - spread, Flame.TYPE.TOP));
                } else {
                    explosion.add(new Flame(xblock, yblock - spread, Flame.TYPE.VERTICAL));
                }
                spread++;
            }


        }
    }
    public boolean Explode(GraphicsContext gc) {
        for (Flame flame : explosion) {
            flame.update();
            flame.render(gc);
        }
        time++;
        if (time > 50) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        placeBomb(BombermanGame.map.getMAP_COLLISION());
    }
}
