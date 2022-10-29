package uet.oop.bomberman.menus;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.Sound;
import uet.oop.bomberman.utils.ConstVar;
import javafx.scene.text.Font;
import uet.oop.bomberman.utils.StopWatch;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static uet.oop.bomberman.BombermanGame.*;


public class BombermanMenu {

    private final InputStream inputFont1 = Files.newInputStream(Paths.get("res\\font\\TitleGame.TTF"));
    private final InputStream inputFont2 = Files.newInputStream(Paths.get("res\\font\\BOMBERMA.TTF"));
    private final InputStream inputFont3 = Files.newInputStream(Paths.get("res\\font\\BOMBERMA.TTF"));

    private final InputStream blackBackgroundFile = Files.newInputStream(Paths.get("res\\textures\\blackBackground.png"));
    private final Font font1 = Font.loadFont(inputFont1, 150);
    private final Font font2 = Font.loadFont(inputFont2, 60);
    private final Font font3 = Font.loadFont(inputFont3, 50);

    private final Image imageBlack = new Image(blackBackgroundFile);

    private final ImageView blackBackground = new ImageView(imageBlack);

    private final ButtonInGame titleGame = new ButtonInGame("B O M B E R M A N", 0, 50, 1440, 100, font1, Color.ORANGE);

    private final ButtonInGame startButton = new ButtonInGame("NEW GAME", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 350, 500, 80, font2, "-fx-background-color: #000;");
    private final ButtonInGame controlButton = new ButtonInGame("CONTROL", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 450, 500, 80, font2, "-fx-background-color: #000;");
    private final ButtonInGame exitButton = new ButtonInGame("EXIT", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 550, 500, 80, font2, "-fx-background-color: #000;");
    private final ButtonInGame continueButton = new ButtonInGame("CONTINUE", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 250, 500, 80, font2, "-fx-background-color: #000;");

    private static final ButtonInGame exitControlButton = new ButtonInGame();

    private static Text menuInGame = new Text();

    private static ImageView imageControlView = new ImageView();

    private static ImageView imageGameMenuView = new ImageView();
    private final ButtonInGame soundButton = new ButtonInGame("SOUND", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 140, 375, 280, 50, font3, "-fx-background-color: #585858;");
    private final ButtonInGame resumeButton = new ButtonInGame("RESUME", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 150, 275, 300, 50, font3, "-fx-background-color: #585858;");
    private final ButtonInGame exitGameButton = new ButtonInGame("EXIT", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 125, 475, 250, 50, font3, "-fx-background-color: #585858;");
    private final ButtonInGame mainMenuButton = new ButtonInGame("MAIN MENU", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 175, 500, 50, font3, "-fx-background-color: #585858;");
    private boolean isShowned = false;

    public static boolean isRunning = true;

    public BombermanMenu() throws IOException {

    }

    public void generate() {

        Media media = Sound.soundTitleScrene;
        titleScreen = new MediaPlayer(media);
        titleScreen.play();

        root.getChildren().add(titleGame);
        root.getChildren().add(startButton);
        root.getChildren().add(controlButton);
        root.getChildren().add(exitButton);

        handleExit();
        handleStart();
        handleControl();
        handleExitControl();
    }

    public void reset() {
        timeGameOver = new StopWatch();
        timeWinGame = new StopWatch();

        countLevel = 1;
        root.getChildren().clear();
        root.getChildren().add(BombermanGame.getCanvas());
        entities.clear();
        stillObjects.clear();
        bomberman.getBomstack().clear();
        bomberman = new Bomber(5, 5, Sprite.player_right);
        entities.add(bomberman);
        map.ReadMap();
        map.LoadMap();
        scoreNumber = 0;
        enemyNumber = entities.size() - 1;
        System.out.println(countLevel);
    }

    public void handleStart() {
        startButton.setOnMouseClicked(event -> {
            reset();

            root.getChildren().remove(blackBackground);
            root.getChildren().remove(winGame);

            titleScreen.stop();

            Media media = Sound.soundStageTheme;
            stageTheme = new MediaPlayer(media);
            stageTheme.play();

            scene.setFill(Color.GRAY);

            level = new TextInGame("Level: ", 10);

            bomb = new TextInGame("Bombs: ", 210);

            enemy = new TextInGame("Enemy: ", 380);

            score = new TextInGame("Score: ", 620);

            life = new TextInGame("Life: ", 820);

            flame = new TextInGame("Flame: ", 1020);

            speed = new TextInGame("Speed: ", 1220);

            menuInGame = new TextInGame("MENU", 1380);

            menuInGame.setOnMouseEntered(event1 -> menuInGame.setFill(Color.RED));
            menuInGame.setOnMouseExited(event1 -> menuInGame.setFill(Color.ORANGE));

            root.getChildren().add(level);
            root.getChildren().add(bomb);
            root.getChildren().add(enemy);
            root.getChildren().add(score);
            root.getChildren().add(life);
            root.getChildren().add(flame);
            root.getChildren().add(speed);
            root.getChildren().add(menuInGame);

            BombermanGame.running = true;
            root.getChildren().remove(titleGame);
            root.getChildren().remove(startButton);
            root.getChildren().remove(controlButton);
            root.getChildren().remove(exitButton);
        });

        continueButton.setOnMouseClicked(event -> {

            root.getChildren().remove(blackBackground);

            titleScreen.stop();

            Media media = Sound.soundStageTheme;
            stageTheme = new MediaPlayer(media);
            stageTheme.play();

            scene.setFill(Color.GRAY);

            level = new TextInGame("Level: ", 10);

            bomb = new TextInGame("Bombs: ", 210);

            enemy = new TextInGame("Enemy: ", 380);

            score = new TextInGame("Score: ", 620);

            life = new TextInGame("Life: ", 820);

            flame = new TextInGame("Flame: ", 1020);

            speed = new TextInGame("Speed: ", 1220);

            menuInGame = new TextInGame("MENU", 1380);

            menuInGame.setOnMouseEntered(event1 -> menuInGame.setFill(Color.RED));
            menuInGame.setOnMouseExited(event1 -> menuInGame.setFill(Color.ORANGE));

            root.getChildren().add(level);
            root.getChildren().add(bomb);
            root.getChildren().add(enemy);
            root.getChildren().add(score);
            root.getChildren().add(life);
            root.getChildren().add(flame);
            root.getChildren().add(speed);
            root.getChildren().add(menuInGame);

            BombermanGame.running = true;
            root.getChildren().remove(titleGame);
            root.getChildren().remove(startButton);
            root.getChildren().remove(controlButton);
            root.getChildren().remove(exitButton);
            root.getChildren().remove(continueButton);
        });
    }

    public void handleControl() {
        controlButton.setOnMouseClicked(event -> {

            FileInputStream is;
            try {
                is = new FileInputStream(Paths.get("target\\classes\\textures\\CONTROL.png").toFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


            Image image = new Image(is);
            imageControlView = new ImageView(image);
            imageControlView.setFitWidth(800);
            imageControlView.setFitHeight(500);
            imageControlView.setTranslateX((double) ConstVar.WIDTH * ConstVar.TILE_SIZE / 2 - 400);
            imageControlView.setTranslateY((double) ConstVar.HEIGHT * ConstVar.TILE_SIZE / 2 - 250);

            exitControlButton.setText("EXIT");
            exitControlButton.setTranslateY(500);
            exitControlButton.setTranslateX(900);
            exitControlButton.setPrefSize(200, 50);
            exitControlButton.setStyle("-fx-background-color: #fff; ");
            exitControlButton.setFont(font3);
            exitControlButton.setTextFill(Color.RED);
            exitControlButton.setOnMouseEntered(e -> exitControlButton.setTextFill(Color.ORANGE));
            exitControlButton.setOnMouseExited(e -> exitControlButton.setTextFill(Color.RED));

            root.getChildren().add(imageControlView);
            root.getChildren().add(exitControlButton);

        });
    }

    public void handleExit() {
        exitButton.setOnMouseClicked(event -> Platform.exit());
    }

    public void handleExitControl() {
        exitControlButton.setOnMouseClicked(event -> {
            root.getChildren().remove(imageControlView);
            root.getChildren().remove(exitControlButton);
        });
    }

    public void handleMenuInGame() {
        menuInGame.setOnMouseClicked(event -> {
            handleShow();
        });
    }

    public void handleShow() {
        if (!isShowned) {
            isShowned = true;

            FileInputStream is;
            try {
                is = new FileInputStream(Paths.get("res\\textures\\backgroundGameMenu.png").toFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            Image image = new Image(is);
            imageGameMenuView = new ImageView(image);
            imageGameMenuView.setFitWidth(800);
            imageGameMenuView.setFitHeight(500);
            imageGameMenuView.setTranslateX((double) ConstVar.WIDTH * ConstVar.TILE_SIZE / 2 - 400);
            imageGameMenuView.setTranslateY((double) ConstVar.HEIGHT * ConstVar.TILE_SIZE / 2 - 250);

            root.getChildren().add(imageGameMenuView);
            root.getChildren().add(soundButton);
            root.getChildren().add(resumeButton);
            root.getChildren().add(exitGameButton);
            root.getChildren().add(mainMenuButton);
            running = false;
        } else {
            root.getChildren().remove(soundButton);
            root.getChildren().remove(resumeButton);
            root.getChildren().remove(exitGameButton);
            root.getChildren().remove(imageGameMenuView);
            root.getChildren().remove(mainMenuButton);
            isShowned = false;
            running = true;
        }
    }

    public void clearInGame() {
        root.getChildren().remove(level);
        root.getChildren().remove(bomb);
        root.getChildren().remove(enemy);
        root.getChildren().remove(score);
        root.getChildren().remove(life);
        root.getChildren().remove(flame);
        root.getChildren().remove(speed);
        root.getChildren().remove(menuInGame);
        root.getChildren().remove(blackBackground);

        root.getChildren().remove(soundButton);
        root.getChildren().remove(resumeButton);
        root.getChildren().remove(exitGameButton);
        root.getChildren().remove(imageGameMenuView);
        root.getChildren().remove(mainMenuButton);
        root.getChildren().remove(menuInGame);
        stageTheme.stop();
        scene.setFill(Color.BLACK);
        running = false;

        blackBackground.setFitWidth(1540);
        blackBackground.setFitHeight(720);
        blackBackground.setTranslateX((double) ConstVar.WIDTH * ConstVar.TILE_SIZE / 2 - (1540 / 2));
        blackBackground.setTranslateY((double) ConstVar.HEIGHT * ConstVar.TILE_SIZE / 2 - (720 / 2));
    }

    public void handleMainMenuButton() {
        mainMenuButton.setOnMouseClicked(event -> {
            clearInGame();
            root.getChildren().add(blackBackground);
            root.getChildren().add(titleGame);
            root.getChildren().add(startButton);
            root.getChildren().add(continueButton);
            root.getChildren().add(controlButton);
            root.getChildren().add(exitButton);
        });
    }

    public void handleSoundButton() {
        soundButton.setOnMouseClicked(event -> {
            if (!isRunning) {
                isRunning = true;
                stageTheme.play();
            } else {
                isRunning = false;
                stageTheme.stop();
            }
        });
    }

    public void handleResumeButton() {
        resumeButton.setOnMouseClicked(event -> {
            root.getChildren().remove(soundButton);
            root.getChildren().remove(resumeButton);
            root.getChildren().remove(exitGameButton);
            root.getChildren().remove(imageGameMenuView);
            root.getChildren().remove(mainMenuButton);
            isShowned = false;
            running = true;
        });
    }

    public void handleExitGameButton() {
        exitGameButton.setOnMouseClicked(event -> {
            Platform.exit();
        });
    }

    public void updateSideBar() {
        level.setText("Level: " + BombermanGame.countLevel);
        life.setText("Life: " + bomberman.getLife());
        score.setText("Score: " + scoreNumber);
        bomb.setText("Bomb: " + bomberman.getBOMB_NUMBER());
        speed.setText("Speed: " + bomberman.getBOMBER_SPEED());
        flame.setText("Flame: " + bomberman.getB_radius());
        if(bomberman.isBoosted()) {
            enemy.setText("Boosted: " + (10 - bomberman.getPowerTime().getElapsedTime()/1000));
        }else{
            enemy.setText("");
        }
    }

    public void levelPass() {
        if (countLevel <= 3 && !win) {
            if (inPortal) {
                timeLevelPass.start();
                inPortal = false;
                Media media = Sound.soundLevel;
                soundLevelStart = new MediaPlayer(media);
                soundLevelStart.play();
                clearInGame();
                root.getChildren().add(blackBackground);
                if (countLevel == 2) {
                    stage2 = new TextInGame("STAGE 2", ConstVar.TILE_SIZE * ConstVar.WIDTH / 2 - 70, ConstVar.TILE_SIZE * ConstVar.HEIGHT / 2 + 25);
                    root.getChildren().add(stage2);
                } else if (countLevel == 3) {
                    stage3 = new TextInGame("STAGE 3", ConstVar.TILE_SIZE * ConstVar.WIDTH / 2 - 70, ConstVar.TILE_SIZE * ConstVar.HEIGHT / 2 + 25);
                    root.getChildren().add(stage3);
                }
            }
        } else {
            timeLevelPass = new StopWatch();
        }
    }

    public void nextLevel() {
        root.getChildren().remove(stage2);
        root.getChildren().remove(stage3);
        root.getChildren().remove(blackBackground);
        titleScreen.stop();
        Media media = Sound.soundStageTheme;
        stageTheme = new MediaPlayer(media);
        stageTheme.play();
        scene.setFill(Color.GRAY);

        bomberman.getBomstack().clear();
        stillObjects.clear();
        map.ReadMap();
        map.LoadMap();

        enemyNumber = entities.size() - 1;

        level = new TextInGame("Level: ", 10);

        bomb = new TextInGame("Bombs: ", 210);

        enemy = new TextInGame("Enemy: ", 380);

        score = new TextInGame("Score: ", 620);

        life = new TextInGame("Life: ", 820);

        flame = new TextInGame("Flame: ", 1020);

        speed = new TextInGame("Speed: ", 1220);

        menuInGame = new TextInGame("MENU", 1380);

        menuInGame.setOnMouseEntered(event1 -> menuInGame.setFill(Color.RED));
        menuInGame.setOnMouseExited(event1 -> menuInGame.setFill(Color.ORANGE));

        root.getChildren().add(level);
        root.getChildren().add(bomb);
        root.getChildren().add(enemy);
        root.getChildren().add(score);
        root.getChildren().add(life);
        root.getChildren().add(flame);
        root.getChildren().add(speed);
        root.getChildren().add(menuInGame);

        BombermanGame.running = true;
        root.getChildren().remove(titleGame);
        root.getChildren().remove(startButton);
        root.getChildren().remove(controlButton);
        root.getChildren().remove(exitButton);
        root.getChildren().remove(continueButton);
    }

    public void gameOver() {
        if (hasDied.getElapsedTime() > 3000) {
            hasDied = new StopWatch();
            clearInGame();
            timeGameOver.start();
            blackBackground.setFitWidth(1540);
            blackBackground.setFitHeight(720);
            root.getChildren().add(blackBackground);
            gameOver = new TextInGame("GAME OVER", ConstVar.TILE_SIZE * ConstVar.WIDTH / 2 - 80, ConstVar.TILE_SIZE * ConstVar.HEIGHT / 2 + 40);
            root.getChildren().add(gameOver);
        }
    }

    public void winGame() {
        if (win) {
            clearInGame();
            timeWinGame.start();
            blackBackground.setFitHeight(1540);
            blackBackground.setFitHeight(720);
            root.getChildren().add(blackBackground);
            winGame = new TextInGame("WIN GAME!", ConstVar.TILE_SIZE * ConstVar.WIDTH / 2 - 85, ConstVar.TILE_SIZE * ConstVar.HEIGHT / 2 + 40);
            root.getChildren().add(winGame);
            Media media = Sound.soundWin;
            soundWinGame = new MediaPlayer(media);
            soundWinGame.play();
            win = false;
        }
    }

    public void handleInGame() {
        updateSideBar();
        handleSoundButton();
        handleMenuInGame();
        handleResumeButton();
        handleExitGameButton();
        handleMainMenuButton();
        levelPass();
        gameOver();
        winGame();
    }

}

