package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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

    protected Image img;

    protected Sprite sprite;

    public boolean life = true;


    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

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

    public double getMovevalY() {
        return movevalY;
    }

    public void setMovevalY(int movevalY) {
        this.movevalY = movevalY;
    }

    public int currentX1 = (int) (getX() / Sprite.SCALED_SIZE);
    public int currentX2 = (int) (getX() + getW()) / Sprite.SCALED_SIZE;
    public int currentY1 = (int) (getY() / Sprite.SCALED_SIZE);
    public int currentY2 = (int) (getY() + getH()) / Sprite.SCALED_SIZE;


    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
    }
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.img = img;
    }
    public Entity (int xUnit, int yUnit, Sprite sprite) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.sprite = sprite;
    }

//    public boolean isKill() {
//
//    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}