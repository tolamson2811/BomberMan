package uet.oop.bomberman.StillObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import uet.oop.bomberman.graphics.Sprite;

public abstract class StillObject extends Shape {
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int xblock;
    protected int yblock;
    protected int w;
    protected int h;

    protected Image img;

    protected Sprite sprite;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas

    public StillObject(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
    }
    public StillObject(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.xblock = xUnit;
        this.yblock = yUnit;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
