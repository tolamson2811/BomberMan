package uet.oop.bomberman.entities.Enemy;

//import com.almasb.fxgl.entity.level.tiled.Tile;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Bombs.Bomb;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    private int time = 0;

    private int timeMoveTile = 8;
    private boolean isStuck = false;

    public Minvo(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.MINVO_SPEED;
        w = 47;
        h = 47;
    }

    @Override
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

        int minvoX = this.x / ConstVar.TILE_SIZE;
        int minvoY = this.y / ConstVar.TILE_SIZE;

        int bomberX = BombermanGame.bomberman.getX() / ConstVar.TILE_SIZE;
        int bomberY = BombermanGame.bomberman.getY() / ConstVar.TILE_SIZE;

        if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) {
            if (bomberX == minvoX) {
                movevalX = 0;
                movevalY = 0;
                if (bomberY < minvoY) {
                    moveUp();
                } else {
                    moveDown();
                }
            } else if (bomberY == minvoY) {
                movevalX = 0;
                movevalY = 0;
                if (bomberX < minvoX) {
                    moveLeft();
                } else {
                    moveRight();
                }
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

    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.minvo_right1,
                    Sprite.minvo_right2, Sprite.minvo_right3 ,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.minvo_left1,
                    Sprite.minvo_left2,Sprite.minvo_left3,time,9).getFxImage();
            time += 0.5;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.minvo_right1,
                    Sprite.minvo_right2,Sprite.minvo_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.minvo_right1,
                    Sprite.minvo_right2,Sprite.minvo_right3,time,9).getFxImage();
            time += 0.5;
        }
        if(!alive) {
            img = Sprite.minvo_dead.getFxImage();;
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