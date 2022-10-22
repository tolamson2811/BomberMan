
package uet.oop.bomberman.entities.Enemy;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;


public class Oneal extends Enemy {

    private int time = 0;

    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];

    private boolean track;
    private boolean changeDir;
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
        char dir = ' ';
        if(track) {
            if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0){
                xblock = this.getXblock();
                yblock = this.getYblock();
                CalculateDisMap();
            }
            if(dis[yblock][xblock]-1  == dis[yblock-1][xblock] ){
                dir = 'U';
            }else if(dis[yblock][xblock]-1 == dis[yblock+1][xblock] ){
                dir = 'D';
            }else if(dis[yblock][xblock]-1 == dis[yblock][xblock-1] ){
                dir = 'L';
            }else if(dis[yblock][xblock]-1 == dis[yblock][xblock+1]){
                dir = 'R';
            }
        }else{
            if (movevalX == 0 || Math.abs(this.getXblock() - xblock) >= 4) {
                changeDir = !changeDir;
            }
            dir = (changeDir) ? 'L' : 'R';
            if(Math.sqrt(Math.pow(BombermanGame.bomberman.getX() - x,2) + Math.pow(BombermanGame.bomberman.getY()-y,2)) < 300){
                track = true;
                dir = ' ';
            }
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

        if(life <= 0) {
            alive = false;
            stopWatch.start();
        }
        boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

        if(check && !BombermanGame.bomberman.isHit()) {
            BombermanGame.bomberman.setHit(true);
            BombermanGame.bomberman.setLife(BombermanGame.bomberman.getLife()-1);
            BombermanGame.bomberman.getStopWatch().start();

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
            if(currentx == this.getXblock() && currenty == this.getYblock()) {
                break;
            }
            if (currentx + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '*'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty][currentx + 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx + 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx+1,currenty));
            }
            if (currentx - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '*'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty][currentx - 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx - 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx-1,currenty));
            }
            if (currenty - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '*'
                    /*&& BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b'*/) && dis[currenty - 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty - 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty-1));
            }
            if (currenty + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '*'
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
            img = Sprite.oneal_dead.getFxImage();
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

