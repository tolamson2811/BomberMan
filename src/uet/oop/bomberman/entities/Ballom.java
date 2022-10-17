package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.Utils.StopWatch;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends Entity {

    private WALK_TYPE status;
    
    private int time = 0;
    private boolean isStuck = false;

    private int timeMoveTile = 8;

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
        double randomDirection = Math.random();
        if (this.x % ConstVar.TILE_SIZE <= 1 && this.y % ConstVar.TILE_SIZE <= 1) {
            randomDirection = Math.random();

            if (timeMoveTile >= 8) {
                randomDirection = Math.random();
                if (randomDirection < 0.25) {
                    moveLeft();
                } else if (randomDirection < 0.5) {
                    moveRight();
                } else if (randomDirection < 0.75) {
                    moveUp();
                } else {
                    moveDown();
                }
                timeMoveTile = 0;
            } else {
                timeMoveTile++;
            }

            if (isStuck) {
                if (randomDirection < 0.25) {
                    moveLeft();
                } else if (randomDirection < 0.5) {
                    moveRight();
                } else if (randomDirection < 0.75) {
                    moveUp();
                } else {
                    moveDown();
                }
                isStuck = false;
            }

        }

        BombermanGame.map.mapCollision(this);

        if (movevalX == 0 && movevalY == 0) {
            isStuck = true;
        }

        x += movevalX;
        y += movevalY;


        boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

        if(check) {
            BombermanGame.bomberman.setAlive(false);
        }
    }

    public void moveLeft() {
        status = WALK_TYPE.LEFT;
        movevalX = ConstVar.BALLOM_SPEED;
        movevalY = 0;
    }

    public void moveRight() {
        status = WALK_TYPE.RIGHT;
        movevalX = -ConstVar.BALLOM_SPEED;
        movevalY = 0;
    }

    public void moveUp() {
        status = WALK_TYPE.UP;
        movevalY = -ConstVar.BALLOM_SPEED;
        movevalX = 0;
    }

    public void moveDown() {
        status = WALK_TYPE.DOWN;
        movevalY = ConstVar.BALLOM_SPEED;
        movevalX = 0;
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
    }
}