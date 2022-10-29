package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.utils.StopWatch;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int xblock;
    protected int yblock;
    protected int w;
    protected int h;

    protected int movevalX;

    protected int movevalY;

    protected boolean isInBomb = false;

    protected WALK_TYPE status;

//    protected int currentX1 = (int) (getX() / ConstVar.TILE_SIZE);
//    protected int currentX2 ;
//    protected int currentY1 ;
//    protected int currentY2 ;


    public enum WALK_TYPE {
        RIGHT, LEFT, UP, DOWN
    }
    protected Image img;

    protected Sprite sprite;

    protected boolean alive = true;
    protected int life;
    protected StopWatch stopWatch = new StopWatch();

    protected boolean wall_pass;
    protected boolean flame_pass;
    protected boolean bom_pass;

    protected boolean boosted;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

    protected boolean hit = false;

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isWall_pass() {
        return wall_pass;
    }

    public void setWall_pass(boolean wall_pass) {
        this.wall_pass = wall_pass;
    }

    public boolean isFlame_pass() {
        return flame_pass;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }
    public void setFlame_pass(boolean flame_pass) {
        this.flame_pass = flame_pass;
    }

    public boolean isBom_pass() {
        return bom_pass;
    }

    public void setBom_pass(boolean bom_pass) {
        this.bom_pass = bom_pass;
    }

    public boolean isInBomb() {
        return isInBomb;
    }

    public void setInBomb(boolean inBomb) {
        isInBomb = inBomb;
    }

    public int getXblock() {
        return (x%ConstVar.TILE_SIZE >= 32) ?(x/ConstVar.TILE_SIZE+1):(x/ConstVar.TILE_SIZE) ;
    }


    public int getYblock() {
        return (y%ConstVar.TILE_SIZE >= 24) ?(y/ConstVar.TILE_SIZE+1):(y/ConstVar.TILE_SIZE);
    }

    public void setYblock(int yblock) {
        this.yblock = yblock;
    }

    public int getCurrentX1() {
        return getX()/ ConstVar.TILE_SIZE;
    }

    public int getCurrentX2() {
        return (getX() + getW()) / ConstVar.DEFAULT_SIZE;
    }

    public int getCurrentY1() {
        return (getY() / ConstVar.DEFAULT_SIZE);
    }

    public int getCurrentY2() {
        return (int) (getY() + getH()) / ConstVar.DEFAULT_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public double getMovevalX() {
        return movevalX;
    }

    public void setMovevalX(int movevalX) {
        this.movevalX = movevalX;
    }

    public void plusMoveValX() {
        this.movevalX++;
    }

    public void plusMoveValY() {
        this.movevalY++;
    }

    public void minusMoveValX() {
        this.movevalX--;
    }

    public void minusMoveValY() {
        this.movevalY--;
    }

    public double getMovevalY() {
        return movevalY;
    }

    public void setMovevalY(int movevalY) {
        this.movevalY = movevalY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean life) {
        this.alive = life;
    }

    public StopWatch getStopWatch() {
        return stopWatch;
    }


    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
    }

    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.img = img;
    }

    public Entity (int xUnit, int yUnit, Sprite sprite) {
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.sprite = sprite;
        this.img = sprite.getFxImage();
    }




    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}