package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Entity {

    protected int speed;

    protected int point;
    protected char preBlock;
    public Enemy (int xUnit, int yUnit, Sprite sprite) {
        super(xUnit,yUnit,sprite);
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
        this.sprite = sprite;
    }

    public abstract void moveMent();

    public abstract void Animation();
    public void moveLeft() {
        status = WALK_TYPE.LEFT;
        movevalX = -speed;
        movevalY = 0;
    }

    public void moveRight() {
        status = WALK_TYPE.RIGHT;
        movevalX = speed;
        movevalY = 0;
    }

    public void moveUp() {
        status = WALK_TYPE.UP;
        movevalY = -speed;
        movevalX = 0;
    }

    public void moveDown() {
        status = WALK_TYPE.DOWN;
        movevalY = speed;
        movevalX = 0;
    }
}
