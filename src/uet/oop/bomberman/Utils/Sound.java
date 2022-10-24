package uet.oop.bomberman.Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;

import java.io.File;

public class Sound {
    public static final String titleScreenFile = "target\\classes\\sound\\TitleScreen.mp3";
    public static final Media soundTitleScrene = new Media(new File(titleScreenFile).toURI().toString());
    public static final String stageThemeFile = "target\\classes\\sound\\StageTheme.mp3";
    public static final Media soundStageTheme = new Media(new File(stageThemeFile).toURI().toString());
    public static final String placeBombFile = "target\\classes\\sound\\place_bomb.wav";
    public static final Media soundPlaceBomb = new Media(new File(placeBombFile).toURI().toString());
    public static final String bombExplodeFile = "target\\classes\\sound\\bomb_explode.wav";
    public static final Media soundExplode = new Media(new File(bombExplodeFile).toURI().toString());

}
