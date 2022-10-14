package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends Entity {

    private WALK_TYPE status;

    private static final int SPEED = 1;

    private int time = 0;

    enum WALK_TYPE {
        LEFT, RIGHT, UP, DOWN, DIE
    }

    public Ballom(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        img = sprite.getFxImage();
        status = WALK_TYPE.RIGHT;
        w = 47;
        h = 47;
    }

    public void moveMent() {
        if (this.x % 48 <= 1 && this.y % 48 <= 1) {
            double randomDirection = Math.random();
            if (randomDirection < 0.25) {
                moveLeft();
            } else if (randomDirection > 0.25 && randomDirection < 0.5) {
                moveRight();
            } else if (randomDirection > 0.5 && randomDirection < 0.75) {
                moveUp();
            } else if (randomDirection > 0.75) {
                moveDown();
            }
        }
        BombermanGame.map.mapCollision(this);

        x += movevalX;
        y += movevalY;

        boolean check = BombermanGame.collision.checkCollision(this, BombermanGame.bomberman);

        if(check) {
            BombermanGame.bomberman.life = false;
        }
    }

    public void moveLeft() {
        status = WALK_TYPE.LEFT;
        movevalX = SPEED;
        movevalY = 0;
    }

    public void moveRight() {
        status = WALK_TYPE.RIGHT;
        movevalX = -SPEED;
        movevalY = 0;
    }

    public void moveUp() {
        status = WALK_TYPE.UP;
        movevalY = -SPEED;
        movevalX = 0;
    }

    public void moveDown() {
        status = WALK_TYPE.DOWN;
        movevalY = SPEED;
        movevalX = 0;
    }

    public void setDie() {
        status = WALK_TYPE.DIE;
    }

    public void animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2, Sprite.balloom_right3 ,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.balloom_left1,
                    Sprite.balloom_left2,Sprite.balloom_left3,time,9).getFxImage();
            time += 0.5;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2,Sprite.balloom_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2,Sprite.balloom_right3,time,9).getFxImage();
            time += 0.5;
        }
    }

    @Override
    public void update() {
        moveMent();
        animation();
        if(!life) {
            img = Sprite.movingSprite(Sprite.balloom_dead,
                    Sprite.balloom_dead,Sprite.balloom_dead,time,9).getFxImage();
            time += 0.5;
            BombermanGame.entities.remove(this);
        }
    }
}