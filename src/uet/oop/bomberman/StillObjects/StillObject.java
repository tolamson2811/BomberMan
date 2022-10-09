package uet.oop.bomberman.StillObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Các đối tượng cố định như tường gạch cỏ.
 */
public abstract class StillObject {
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int xblock;
    protected int yblock;
    protected int w = Sprite.SCALED_SIZE;
    protected int h = Sprite.SCALED_SIZE;

    protected boolean terminate;
    protected Image img;

    protected Sprite sprite;

    protected int time;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

    public StillObject(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.terminate = false;
    }
    public StillObject(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.img = img;
        this.terminate = false;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public int getXblock() {
        return xblock;
    }

    public int getYblock() {
        return yblock;
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

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

}
