package uet.oop.bomberman.menus;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Sound;
import uet.oop.bomberman.Utils.ConstVar;
import javafx.scene.text.Font;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static uet.oop.bomberman.BombermanGame.*;


public class BombermanMenu {

    private final InputStream inputFont1 = Files.newInputStream(Paths.get("res\\font\\TitleGame.TTF"));
    private final InputStream inputFont2 = Files.newInputStream(Paths.get("res\\font\\BOMBERMA.TTF"));

    private final InputStream inputFont3 = Files.newInputStream(Paths.get("res\\font\\BOMBERMA.TTF"));

    private final Font font1 = Font.loadFont(inputFont1, 150);
    private final Font font2 = Font.loadFont(inputFont2, 60);
    private final Font font3 = Font.loadFont(inputFont3, 50);

    private final ButtonInGame titleGame = new ButtonInGame("B O M B E R M A N", 0, 50, 1440, 100, font1, Color.ORANGE);

    private final ButtonInGame startButton = new ButtonInGame("NEW GAME", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 300, 500, 80, font2, "-fx-background-color: #000;");
    private final ButtonInGame controlButton = new ButtonInGame("CONTROL",(double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 400,500, 80, font2, "-fx-background-color: #000;" );
    private final ButtonInGame exitButton = new ButtonInGame("EXIT", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 250, 500, 500, 80, font2, "-fx-background-color: #000;");

    private static final ButtonInGame exitControlButton = new ButtonInGame();

    private static Text menuInGame = new Text();

    private static ImageView imageControlView = new ImageView();

    private static ImageView imageGameMenuView = new ImageView();

    private final ButtonInGame soundButton = new ButtonInGame("SOUND",(double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 140, 150, 280, 50, font3, "-fx-background-color: #585858;");
    private final ButtonInGame resumeButton = new ButtonInGame("RESUME", (double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 150, 325, 300, 50, font3, "-fx-background-color: #585858;");
    private final ButtonInGame exitGameButton = new ButtonInGame("EXIT",(double) (ConstVar.WIDTH * ConstVar.TILE_SIZE / 2) - 125, 500, 250, 50, font3,  "-fx-background-color: #585858;");

    private boolean isShowned = false;

    private boolean isRunning = true;

    public BombermanMenu() throws IOException {

    }

    public void generate() throws IOException {

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
    public void handleStart() {
        startButton.setOnMouseClicked(event -> {

            titleScreen.stop();

            Media media = Sound.soundStageTheme;
            stageTheme = new MediaPlayer(media);
            stageTheme.play();

            scene.setFill(Color.GRAY);

            level = new TextInGame("Level: ", 10);

            bomb = new TextInGame("Bombs: ", 210);

            wallPass = new TextInGame("Wall Pass: ", 350);

            score = new TextInGame("Score: ", 620);

            life = new TextInGame("Life: ", 820);

            flame = new TextInGame("Flame: ", 1020);

            speed = new TextInGame("Speed: ", 1220);

            menuInGame = new TextInGame("MENU", 1380);

            menuInGame.setOnMouseEntered(event1 -> menuInGame.setFill(Color.RED));
            menuInGame.setOnMouseExited(event1 -> menuInGame.setFill(Color.ORANGE));

            for (TextInGame textInGame : textArray) {
                root.getChildren().add(textInGame);
            }

            BombermanGame.running = true;
            root.getChildren().remove(titleGame);
            root.getChildren().remove(startButton);
            root.getChildren().remove(controlButton);
            root.getChildren().remove(exitButton);
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
            if (!isShowned) {
                isShowned = true;

                FileInputStream is;
                try {
                    is = new FileInputStream(Paths.get("target\\classes\\textures\\backgroundGameMenu.png").toFile());
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
                running = false;
            } else {
                root.getChildren().remove(soundButton);
                root.getChildren().remove(resumeButton);
                root.getChildren().remove(exitGameButton);
                root.getChildren().remove(imageGameMenuView);
                isShowned = false;
                running = true;
            }
        });
    }

    public void clearInGame() {
        root.getChildren().remove(level);
        root.getChildren().remove(bomb);
        root.getChildren().remove(wallPass);
        root.getChildren().remove(score);
        root.getChildren().remove(life);
        root.getChildren().remove(flame);
        root.getChildren().remove(speed);
        root.getChildren().remove(soundButton);
        root.getChildren().remove(resumeButton);
        root.getChildren().remove(exitGameButton);
        root.getChildren().remove(imageGameMenuView);
        scene.setFill(Color.BLACK);
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
        wallPass.setText("Wall Pass: " + bomberman.isWall_pass());
    }

    public void handleInGame() {
        updateSideBar();
        handleSoundButton();
        handleMenuInGame();
        handleResumeButton();
        handleExitGameButton();
    }

}
