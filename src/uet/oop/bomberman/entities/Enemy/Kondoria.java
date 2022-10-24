package uet.oop.bomberman.entities.Enemy;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Kondoria extends Enemy{

    private int time = 0;

    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];

    public Kondoria(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        movevalX = 0;
        movevalY = 0;
        img = sprite.getFxImage();
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.KONDORIA_SPEED;
        w = 47;
        h = 47;
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
        }else {
            if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) {
                double randomDirection = Math.random();
                if (randomDirection < 0.25) {
                    dir = 'L';
                } else if (randomDirection < 0.5) {
                    dir = 'R';
                } else if (randomDirection < 0.75) {
                    dir = 'U';
                } else {
                    dir = 'D';
                }
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
     * DFS né BOMB.
     */
    public void PathFinding(int startX,int startY,int [][] dis) {
        if(startX != this.getXblock() || startY != this.getYblock()) {

            if (startX + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != 'I' 
                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != 'b')
                    && dis[startY][startX] + 1 < dis[startY][startX+1]) {
                dis[startY][startX + 1] = dis[startY][startX] + 1;
                PathFinding(startX + 1, startY,dis);
            }
            if (startX - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != 'I' 
                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != 'b')
                    && dis[startY][startX] + 1 < dis[startY][startX - 1]) {
                dis[startY][startX - 1] = dis[startY][startX] + 1;
                PathFinding(startX - 1, startY,dis);
            }
            if (startY - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != 'I' 
                    && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '*'
                    && BombermanGame.map.getTILE_MAP()[startY-1][startX ] != 'b')
                    && dis[startY][startX] + 1 < dis[startY - 1][startX] ) {
                dis[startY - 1][startX] = dis[startY][startX] + 1;
                PathFinding(startX, startY - 1,dis);
            }
            if (startY + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != 'I' 
                    && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '*'
                    && BombermanGame.map.getTILE_MAP()[startY+1][startX] != 'b')
                    && dis[startY][startX] + 1 < dis[startY + 1][startX]) {
                dis[startY + 1][startX] = dis[startY][startX] + 1;
                PathFinding(startX, startY + 1,dis);
            }
        }
    }
    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.kondoria_right1,
                    Sprite.kondoria_right2, Sprite.kondoria_right3,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.kondoria_left1,
                    Sprite.kondoria_left2,Sprite.kondoria_left3,time,30).getFxImage();
            time += 1;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.kondoria_right1,
                    Sprite.kondoria_right2, Sprite.kondoria_right3,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.kondoria_left1,
                    Sprite.kondoria_left2,Sprite.kondoria_left3,time,30).getFxImage();
            time += 1;
        }

        if(!alive) {
                img = Sprite.kondoria_dead.getFxImage();
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

