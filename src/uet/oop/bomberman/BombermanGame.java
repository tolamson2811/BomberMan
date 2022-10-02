package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    public static Scene scene;
    private List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public GameMap map = new GameMap(0,0);
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);
        map.ReadMap();

        //  scene vao stage
        stage.setTitle("BombearMinn");
        stage.setScene(scene);
        stage.show();

        Rectangle rectangle = new Rectangle();
        //root.getChildren().add(rectangle);
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        bomberman.init();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
//                rectangle.setX(bomberman.getX());
//                rectangle.setY(bomberman.getY());
//                rectangle.setWidth(32);
//                rectangle.setHeight(32);
//                rectangle.setFill(null);
//                rectangle.setStrokeWidth(1);
//                rectangle.setStroke(Color.BLUEVIOLET);
                render();
                update();
            }
        };
        map.LoadMap();
        timer.start();
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}