package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {

    private int time = 0;

    private int timeMoveTile = 8;
    private boolean isStuck = false;

    public Doll(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.DOLL_SPEED;
        w = 47;
        h = 47;
        life = 1;
    }

    @Override
    public void moveMent() {
        double randomDirection = Math.random();
        if (this.x % ConstVar.TILE_SIZE <= 1 && this.y % ConstVar.TILE_SIZE <= 1) {
            randomDirection = Math.random();

            if (timeMoveTile >= 3) {
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
                double randomSpeed = Math.random();
                if (randomSpeed < 0.33) {
                    ConstVar.DOLL_SPEED = 1;
                } else if (randomSpeed < 0.66) {
                    ConstVar.DOLL_SPEED = 2;
                } else {
                    ConstVar.DOLL_SPEED = 3;
                }
                speed = ConstVar.DOLL_SPEED;
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


        if(life <= 0) {
            alive = false;
            stopWatch.start();
            BombermanGame.scoreNumber += 300;
            BombermanGame.enemyNumber--;
        }
        boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

        if(check && !BombermanGame.bomberman.isHit()) {
            BombermanGame.bomberman.setHit(true);
            BombermanGame.bomberman.setLife(BombermanGame.bomberman.getLife()-1);
            BombermanGame.bomberman.getStopWatch().start();
            BombermanGame.scoreNumber -= 200;
        }
    }

    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.doll_right1,
                    Sprite.doll_right2, Sprite.doll_right3 ,time,60).getFxImage();
            time += speed;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.doll_left1,
                    Sprite.doll_left2,Sprite.doll_left3,time,60).getFxImage();
            time += speed;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.doll_right1,
                    Sprite.doll_right2,Sprite.doll_right3,time,60).getFxImage();
            time += speed;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.doll_right1,
                    Sprite.doll_right2,Sprite.doll_right3,time,60).getFxImage();
            time += speed;
        }
        if(!alive) {
            img = Sprite.doll_dead.getFxImage();;
        }

    }

    @Override
    public void update() {
        if(alive){
            moveMent();
        }
        Animation();
    }
}