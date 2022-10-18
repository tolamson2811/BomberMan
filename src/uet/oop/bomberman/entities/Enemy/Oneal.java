
package uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Algo;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;


public class Oneal extends Enemy {

    private WALK_TYPE status;

    private int time = 0;

    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];

    private boolean track;
    private boolean changeDir;
    private int stucktime = 0;
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
        Algo.BFS(BombermanGame.bomberman.getXblock(), BombermanGame.bomberman.getYblock(),dis,this.getXblock(),this.getYblock());
    }

    public void moveMent() {
        char dir = ' ';
        if(track) {
            if ((this.x % ConstVar.TILE_SIZE == 0 && this.y % ConstVar.TILE_SIZE == 0) || (movevalY == 0 && movevalX == 0)){
                xblock = this.getXblock();
                yblock = this.getYblock();
                CalculateDisMap();
            }
            int min = Integer.MAX_VALUE;
            min = Math.min(min,dis[yblock-1][xblock]);
            min = Math.min(min,dis[yblock+1][xblock]);
            min = Math.min(min,dis[yblock][xblock-1]);
            min = Math.min(min,dis[yblock][xblock+1]);
            if(min == dis[yblock-1][xblock] ){
                dir = 'U';
            }else if(min == dis[yblock+1][xblock] ){
                dir = 'D';
            }else if(min == dis[yblock][xblock-1] ){
                dir = 'L';
            }else{
                dir = 'R';
            }
        }else{
            if (movevalX == 0 || Math.abs(this.getXblock() - xblock) >= 4) {
                changeDir = !changeDir;
            }
            dir = (changeDir) ? 'L' : 'R';
            if(Math.sqrt(Math.pow(BombermanGame.bomberman.getX() - x,2) + Math.pow(BombermanGame.bomberman.getY()-y,2)) < 300){
                track = true;
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

        boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

        if(check) {
            BombermanGame.bomberman.setAlive(false);
        }
    }

    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,9).getFxImage();
            time += 0.5;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,9).getFxImage();
            time += 0.5;
        }

        if(!alive) {
            img = Sprite.oneal_dead.getFxImage();;
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

