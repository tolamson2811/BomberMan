package uet.oop.bomberman.still_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.ConstVar;
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
    protected int w = ConstVar.TILE_SIZE;
    protected int h = ConstVar.TILE_SIZE;

    protected boolean terminate;
    protected Image img;

    protected Sprite sprite;

    protected int time;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

    public StillObject(int xUnit, int yUnit) {
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.terminate = false;
    }
    public StillObject(int xUnit, int yUnit, Image img) {
        this.x = xUnit * ConstVar.TILE_SIZE;
        this.y = yUnit * ConstVar.TILE_SIZE;
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

    public static StillObject getItemAt(int xblock,int yblock) {
        for(StillObject a: BombermanGame.stillObjects) {
            if(a.getYblock() ==  yblock && a.getXblock() == xblock && a instanceof Items) {
                return a;
            }
        }
        return null;
    }

    public static StillObject getBrickAt(int xblock,int yblock) {
        for(StillObject a: BombermanGame.stillObjects) {
            if(a.getYblock() ==  yblock && a.getXblock() == xblock && a instanceof Brick) {
                return a;
            }
        }
        return null;
    }

    public void Remove() {
        BombermanGame.stillObjects.remove(this);
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
