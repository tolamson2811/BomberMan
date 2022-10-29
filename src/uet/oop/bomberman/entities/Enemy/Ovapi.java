package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.Collision;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

public class Ovapi extends Enemy{
    private int time = 0;

    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];

    public Ovapi(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        movevalX = 0;
        movevalY = 0;
        img = sprite.getFxImage();
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.OVAPI_SPEED;
        w = 47;
        h = 47;
        wall_pass = true;
        bom_pass = true;
        life = 1;
        preBlock = ' ';
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
        int randX = (int) (Math.random() * 3) -1;
        int randY = (int) (Math.random() * 3) -1;
        PathFinding(BombermanGame.bomberman.getXblock()+randX, BombermanGame.bomberman.getYblock()+randY,dis);
    }
    public void moveMent() {
        char dir = ' ';


        if (this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0){
            BombermanGame.map.setTILE_MAP(yblock, xblock, preBlock);
            xblock = this.getXblock();
            yblock = this.getYblock();
            if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == ' ' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'i'
                    || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'x' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == '*') {
                preBlock = BombermanGame.map.getTILE_MAP()[yblock][xblock];
            }
            BombermanGame.map.setTILE_MAP(yblock, xblock, 'V');
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
            BombermanGame.scoreNumber -= 200;
        }
    }


    /**
     * BFS xuyên tường xuyên bom ra xung qunah bomber.
     */
    public void PathFinding(int startX,int startY,int [][] dis) {
        if(startX != this.getXblock() || startY != this.getYblock()) {

            if (startX + 1 < ConstVar.WIDTH && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '#'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != 'I'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '*'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != 'b
                        && dis[startY][startX] + 1 < dis[startY][startX+1]) {
                dis[startY][startX + 1] = dis[startY][startX] + 1;
                PathFinding(startX + 1, startY,dis);
            }
            if (startX - 1 >= 0 && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '#'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != 'I'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '*'
//                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != 'b')
                    && dis[startY][startX] + 1 < dis[startY][startX - 1]) {
                dis[startY][startX - 1] = dis[startY][startX] + 1;
                PathFinding(startX - 1, startY,dis);
            }
            if (startY - 1 >= 0 && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '#'
//                    && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != 'I'
//                    && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '*'
//                    && BombermanGame.map.getTILE_MAP()[startY-1][startX ] != 'b')
                    && dis[startY][startX] + 1 < dis[startY - 1][startX] ) {
                dis[startY - 1][startX] = dis[startY][startX] + 1;
                PathFinding(startX, startY - 1,dis);
            }
            if (startY + 1 < ConstVar.HEIGHT && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '#'
//                    && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != 'I'
//                    && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '*'
//                    && BombermanGame.map.getTILE_MAP()[startY+1][startX] != 'b')
                    && dis[startY][startX] + 1 < dis[startY + 1][startX]) {
                dis[startY + 1][startX] = dis[startY][startX] + 1;
                PathFinding(startX, startY + 1,dis);
            }
        }
    }
    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.ovapi_right1,
                    Sprite.ovapi_right2, Sprite.ovapi_right3,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.ovapi_left1,
                    Sprite.ovapi_left2,Sprite.ovapi_left3,time,30).getFxImage();
            time += 1;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.ovapi_right1,
                    Sprite.ovapi_right2, Sprite.ovapi_right3,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.ovapi_left1,
                    Sprite.ovapi_left2,Sprite.ovapi_left3,time,30).getFxImage();
            time += 1;
        }

        if(!alive) {
            if(stopWatch.getElapsedTime() <= 600) {
                img = Sprite.ovapi_dead.getFxImage();

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
