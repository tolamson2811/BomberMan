
package uet.oop.bomberman.entities.Enemy;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.Collision;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;


public class Oneal extends Enemy {

    private int time = 0;

    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];

    private boolean track;
    private boolean changeDir;
    private int timeMoveTile = 0;
    private char dir;
    public Oneal(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        img = sprite.getFxImage();
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.ONEAL_SPEED;
        w = 47;
        h = 47;
        track= false;
        changeDir = true;
        bom_pass = true;
        life = 1;
        preBlock = ' ';
        dir = ' ';


    }

    public void CalculateDisMap() {
        for (int i = 0; i < ConstVar.HEIGHT; i++) {
            for (int j = 0; j < ConstVar.WIDTH; j++) {
                if (i == BombermanGame.bomberman.getYblock() && j == BombermanGame.bomberman.getXblock()) {
                    dis[i][j] = 0;
                } else {
                    dis[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        PathFinding(BombermanGame.bomberman.getXblock(), BombermanGame.bomberman.getYblock(),dis);
    }

    public void moveMent() {
        if(alive) {
            if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) {
                BombermanGame.map.setTILE_MAP(yblock, xblock, preBlock);
                xblock = this.getXblock();
                yblock = this.getYblock();
                if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == ' ' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'i'
                        || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'x') {
                    preBlock = BombermanGame.map.getTILE_MAP()[yblock][xblock];
                }
                BombermanGame.map.setTILE_MAP(yblock, xblock, 'O');
            }
            track = Math.sqrt(Math.pow(BombermanGame.bomberman.getX() - x, 2) + Math.pow(BombermanGame.bomberman.getY() - y, 2)) < 400;
            if (track) {
                if ((this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) || (movevalX == 0 && movevalY ==0) ) {
                    CalculateDisMap();
                }
                if (dis[yblock][xblock] - 1 == dis[yblock - 1][xblock]) {
                    dir = 'U';
                } else if (dis[yblock][xblock] - 1 == dis[yblock + 1][xblock]) {
                    dir = 'D';
                } else if (dis[yblock][xblock] - 1 == dis[yblock][xblock - 1]) {
                    dir = 'L';
                } else if (dis[yblock][xblock] - 1 == dis[yblock][xblock + 1]) {
                    dir = 'R';
                }
            } else {
                if ((this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0 )|| (movevalX == 0 && movevalY ==0)) {
                    while(this.y % ConstVar.TILE_SIZE >= 24) {
                        if(this.y % ConstVar.TILE_SIZE == 0){
                            break;
                        }
                        y++;
                    }

                    while(this.y % ConstVar.TILE_SIZE < 24) {
                        if(this.y % ConstVar.TILE_SIZE == 0){
                            break;
                        }
                        y--;
                    }
                    if (timeMoveTile >= 5) {
                        changeDir = !changeDir;
                        timeMoveTile = 0;
                    }else{
                        timeMoveTile++;
                    }
                }
                dir = (changeDir) ? 'L' : 'R';

            }

            switch (dir) {
                case 'U':
                    moveUp();
                    break;
                case 'D':
                    moveDown();
                    break;
                case 'R':
                    moveRight();
                    break;
                case 'L':
                    moveLeft();
                    break;

            }
            BombermanGame.map.mapCollision(this);
            x += movevalX;
            y += movevalY;

            if (life <= 0) {
                alive = false;
                stopWatch.start();
            }
            boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

            if (check && !BombermanGame.bomberman.isHit()) {
                BombermanGame.bomberman.setHit(true);
                BombermanGame.bomberman.setLife(BombermanGame.bomberman.getLife() - 1);
                BombermanGame.bomberman.getStopWatch().start();
                BombermanGame.scoreNumber -= 200;
            }
        }
    }

    /**
     * BFS xuyên bom.
     */
    public void PathFinding(int startX,int startY,int [][] dis) {
        ArrayList<Pair<Integer,Integer>> node = new ArrayList<>();
        node.add(new Pair<>(startX,startY));
        while(!node.isEmpty()) {
            int currentx = node.get(0).getKey();
            int currenty = node.get(0).getValue();
            node.remove(0);
            if(currentx == xblock && currenty == yblock) {
                break;
            }
            if (currentx + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'X'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty][currentx + 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx + 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx+1,currenty));
            }
            if (currentx - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'X'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty][currentx - 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx - 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx-1,currenty));
            }
            if (currenty - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != 'X'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty - 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty - 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty-1));
            }
            if (currenty + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != 'X'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty + 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty + 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty+1));
            }
        }
    }
    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,60).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,60).getFxImage();
            time += 1;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,60).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,60).getFxImage();
            time += 1;
        }

        if(!alive) {
            if(stopWatch.getElapsedTime() <= 600) {
                img = Sprite.oneal_dead.getFxImage();

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
        moveMent();
        Animation();

    }
}

