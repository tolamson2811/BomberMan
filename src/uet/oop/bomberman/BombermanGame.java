package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.StillObjects.Items;
import uet.oop.bomberman.StillObjects.StillObject;
import uet.oop.bomberman.StillObjects.Wall;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menus.BombermanMenu;
import uet.oop.bomberman.menus.TextInGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private GraphicsContext gc;
    private static Canvas canvas;
    public static Scene scene;
    public static List<Entity> entities = new ArrayList<>();
    public static List<StillObject> stillObjects = new ArrayList<>();

    public static GameMap map = new GameMap();

    public static Bomber bomberman = new Bomber(5, 5, Sprite.player_right);
    public static boolean running = false;
    public static int countLevel = 1;
    public static int scoreNumber = 0;
    public static Group root = new Group();

    public static BombermanMenu menu;

    static {
        try {
            menu = new BombermanMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static TextInGame level, bomb, wallPass, score, life, flame, speed;
    public static MediaPlayer titleScreen = null;
    public static MediaPlayer stageTheme = null;
    public static MediaPlayer placeBomb = null;
    public static MediaPlayer bombExplode = null;
    public static ArrayList<TextInGame> textArray = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // Tao Canvas
        canvas = new Canvas(ConstVar.TILE_SIZE * ConstVar.WIDTH, ConstVar.TILE_SIZE * ConstVar.HEIGHT + ConstVar.TILE_SIZE - 20);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        // Tao scene
        scene = new Scene(root);
        scene.setFill(Color.BLACK);
        map.ReadMap();
        //  scene vao stage
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

        menu.generate();

        Rectangle rectangle = new Rectangle();
        root.getChildren().add(rectangle);
        entities.add(bomberman);

        //GAME LOOP
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    render();
                    update();
                    menu.handleInGame();
                }
            }

        };
        map.LoadMap();
        timer.start();

    }

    public void update() {
        for(int i = 0;i < entities.size();i++) {
            if(!entities.get(i).isAlive() && entities.get(i).getStopWatch().getElapsedTime() >1500) {
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
