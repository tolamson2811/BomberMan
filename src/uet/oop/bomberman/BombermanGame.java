package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.still_objects.*;
import uet.oop.bomberman.utils.Collision;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.utils.Sound;
import uet.oop.bomberman.utils.StopWatch;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menus.BombermanMenu;
import uet.oop.bomberman.menus.TextInGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static Canvas getCanvas() {
        return canvas;
    }
    private GraphicsContext gc;
    private static Canvas canvas;
    public static Scene scene;
    public static List<Entity> entities = new ArrayList<>();
    public static List<StillObject> stillObjects = new ArrayList<>();

    public static GameMap map = new GameMap();

    public static Bomber bomberman = new Bomber(1, 1, Sprite.player_right);
    public static boolean running = false;
    public static int countLevel = 1;
    public static int scoreNumber = 0;

    public static StopWatch timeLevelPass = new StopWatch();
    public static StopWatch timeGameOver = new StopWatch();
    public static StopWatch timeWinGame = new StopWatch();
    public static StopWatch hasDied = new StopWatch();

    public static boolean win = false;

    public static Group root = new Group();

    public static BombermanMenu menu;

    static {
        try {
            menu = new BombermanMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static TextInGame level, bomb, enemy, score, life, flame, speed, stage1, stage2, stage3, gameOver, winGame;
    public static MediaPlayer titleScreen = null;
    public static MediaPlayer stageTheme = null;
    public static MediaPlayer placeBomb = null;
    public static MediaPlayer bombExplode = null;
    public static MediaPlayer soundDied = null;
    public static MediaPlayer soundLevelStart = null;
    public static MediaPlayer soundWinGame = null;
    public static MediaPlayer soundPower = null;
    public static boolean inPortal = false;
    public static int enemyNumber = entities.size() - 1;

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
        map.LoadMap();
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
                if (running || timeLevelPass.getElapsedTime() > 3000 || timeGameOver.getElapsedTime() > 3000 || timeWinGame.getElapsedTime() > 3000) {
                    if (timeLevelPass.getElapsedTime() > 3000 && !win) {
                        timeLevelPass = new StopWatch();
                        if (countLevel <= 3) {
                            menu.nextLevel();
                            System.out.println("check");
                        }
                    }

                    if (timeGameOver.getElapsedTime() > 3000) {
                        timeGameOver = new StopWatch();
                        menu.generate();
                    }

                    if (timeWinGame.getElapsedTime() > 14000) {
                        Platform.exit();
                    }

                    render();
                    update();
                    menu.handleInGame();
                }
            }
        };
//        map.LoadMap();
        timer.start();

    }

    public void update() {
        for(int i = 0;i < entities.size();i++) {
            if(!entities.get(i).isAlive() && entities.get(i).getStopWatch().getElapsedTime() >1500) {
                map.setTILE_MAP(entities.get(i).getYblock(),entities.get(i).getXblock(),' ');
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
                    Brick a = (Brick) o;
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
            if(stillObjects.get(i) instanceof Portal) {
                Portal p = (Portal) stillObjects.get(i);
                if(Collision.checkCollision(bomberman,p) && entities.size() == 1  && BombermanGame.map.getTILE_MAP()[p.getYblock()][p.getXblock()] == 'x'){
                    inPortal = true;
                    countLevel++;
                    if (countLevel > 3) {
                        win = true;
                    }
                    bomberman.setX(240);
                    bomberman.setY(240);
                }
            }
            if(stillObjects.get(i) instanceof Items) {
                Items a = (Items) stillObjects.get(i);
                if(Collision.checkCollision(bomberman,a) && map.getTILE_MAP()[a.getYblock()][a.getXblock()] == 'i') {
                    if (BombermanMenu.isRunning) {
                        Media media = Sound.soundPower;
                        soundPower = new MediaPlayer(media);
                        soundPower.play();
                    }
                    a.takeEffect(bomberman);
                    stillObjects.remove(i);
                    map.setTILE_MAP(a.getYblock(),a.getXblock(),' ');
                    i--;
                }
            }
        }
        bomberman.HandleBomb(gc);
        entities.forEach(g -> g.render(gc));

    }
}
