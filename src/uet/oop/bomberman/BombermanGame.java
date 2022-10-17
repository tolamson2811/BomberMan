package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.stage.Stage;
import uet.oop.bomberman.StillObjects.Items;
import uet.oop.bomberman.StillObjects.StillObject;
import uet.oop.bomberman.StillObjects.Wall;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
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
//    private Bomber bomberman;
    public static List<Entity> entities = new ArrayList<>();
    public static List<StillObject> stillObjects = new ArrayList<>();


    public static GameMap map = new GameMap();

    public static Collision collision = new Collision();

    public static Bomber bomberman = new Bomber(5, 5, Sprite.player_right);

    public static Oneal oneal1 = new Oneal(7, 5, Sprite.oneal_right1);


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(ConstVar.TILE_SIZE * WIDTH, ConstVar.TILE_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);
        map.ReadMap();
        //  scene vao stage
        stage.setTitle("BomberMinn");
        stage.setScene(scene);
        stage.show();

        Rectangle rectangle = new Rectangle();
        root.getChildren().add(rectangle);
        entities.add(bomberman);
        entities.add(oneal1);
        //GAME LOOP
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }

        };
        map.LoadMap();
        timer.start();

    }

    public void update() {
        for(int i = 0;i < entities.size();i++) {
            if(!entities.get(i).isAlive()) {
                entities.remove(i);
                i--;
            }else{
                entities.get(i).update();
            }
        }
        if(bomberman.isPlaceBom()){
            for(int i = 0;i < stillObjects.size();i++) {
                StillObject o = stillObjects.get(i);
                if(o.isTerminate()){
                    Wall a = (Wall) o;
                    a.TerminateProcess();
                    if(!o.isTerminate()) {
                        stillObjects.remove(i);
                        i--;
                    }
                }
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0;i < stillObjects.size();i++) {
            stillObjects.get(i).render(gc);
            if(stillObjects.get(i) instanceof Items) {
                Items a = (Items) stillObjects.get(i);
                if(Collision.checkCollision(bomberman,a) && map.getTILE_MAP()[a.getYblock()][a.getXblock()] == 'i') {
                    a.takeEffect(bomberman);
                    stillObjects.remove(i);
                    i--;
                }
            }
        }
        bomberman.HandleBomb(gc);
        entities.forEach(g -> g.render(gc));

    }
}