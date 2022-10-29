package uet.oop.bomberman.entities.Enemy;

//import com.almasb.fxgl.entity.level.tiled.Tile;
import javafx.scene.paint.Stop;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.Collision;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.StopWatch;

public class Minvo extends Enemy {
    private int time = 0;

    private int timeMoveTile = 8;
    private boolean isStuck = false;

    StopWatch stuckTime = new StopWatch();

    public Minvo(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.MINVO_SPEED;
        w = 47;
        h = 47;
        life = 2;
        preBlock = ' ';
    }

    @Override
    public void moveMent() {

        double randomDirection;

        if (this.x % ConstVar.TILE_SIZE <= 1 && this.y % ConstVar.TILE_SIZE <= 1) {
            BombermanGame.map.setTILE_MAP(yblock, xblock, preBlock);
            xblock = this.getXblock();
            yblock = this.getYblock();
            if (BombermanGame.map.getTILE_MAP()[yblock][xblock] == ' ' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'i'
                    || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'x') {
                preBlock = BombermanGame.map.getTILE_MAP()[yblock][xblock];
            }
            BombermanGame.map.setTILE_MAP(yblock, xblock, 'M');
        }
        if (isStuck) {
            if(stuckTime.getElapsedTime() > 1000) {
                isStuck = false;
                stuckTime.stop();
            }
        }else {
            int bomberX = BombermanGame.bomberman.getXblock();
            int bomberY = BombermanGame.bomberman.getYblock();

            if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) {
                if (bomberX == this.getXblock()) {
                    movevalX = 0;
                    movevalY = 0;
                    if (bomberY < this.getYblock()) {
                        moveUp();
                    } else {
                        moveDown();
                    }
                } else if (bomberY == this.getYblock()) {
                    movevalX = 0;
                    movevalY = 0;
                    if (bomberX < this.getXblock()) {
                        moveLeft();
                    } else {
                        moveRight();
                    }
                }
            }

            if (movevalX == 0 && movevalY == 0) {
                stuckTime.start();
                isStuck = true;
            }
        }


        if (this.x % ConstVar.TILE_SIZE <= 1 && this.y % ConstVar.TILE_SIZE <= 1) {
            if (timeMoveTile >= 5) {
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
        }
        BombermanGame.map.mapCollision(this);

        x += movevalX;
        y += movevalY;

        if(life <= 0 ) {
            alive = false;
            stopWatch.start();
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
        if(hit) {
            if(stopWatch.getElapsedTime() >=2000) {
                  hit = false;
                  stopWatch.stop();
            }
        }
        if(!hit || stopWatch.getElapsedTime() %200 >=100 ){
            if (status == WALK_TYPE.RIGHT) {
                img = Sprite.movingSprite(Sprite.minvo_right1,
                        Sprite.minvo_right2, Sprite.minvo_right3 ,time, 60).getFxImage();
                time += 1;
            } else if (status == WALK_TYPE.LEFT) {
                img = Sprite.movingSprite(Sprite.minvo_left1,
                        Sprite.minvo_left2,Sprite.minvo_left3,time, 60).getFxImage();
                time += 1;
            }else if (status == WALK_TYPE.UP) {
                img = Sprite.movingSprite(Sprite.minvo_right1,
                        Sprite.minvo_right2,Sprite.minvo_right3,time, 60).getFxImage();
                time += 1;
            } else if (status == WALK_TYPE.DOWN) {
                img = Sprite.movingSprite(Sprite.minvo_right1,
                        Sprite.minvo_right2,Sprite.minvo_right3,time, 60).getFxImage();
                time += 1;
            }
        }else{
            img = null;
        }

        if(!alive) {

            if(stopWatch.getElapsedTime() <= 600) {
                img = Sprite.minvo_dead.getFxImage();
            }else{
                if(stopWatch.getElapsedTime() <= 900) {
                    img = Sprite.mob_dead1.getFxImage();
                }
                else if(stopWatch.getElapsedTime() <= 1200) {
                    img = Sprite.mob_dead2.getFxImage();
                }
                else {
                    img = Sprite.mob_dead3.getFxImage();
                }
            }
            BombermanGame.map.setTILE_MAP(yblock,xblock,' ');
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