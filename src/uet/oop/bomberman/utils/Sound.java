package uet.oop.bomberman.utils;

import javafx.scene.media.Media;


import java.io.File;

public class Sound {
    public static final String titleScreenFile = "res\\sound\\TitleScreen.mp3";
    public static final Media soundTitleScrene = new Media(new File(titleScreenFile).toURI().toString());
    public static final String stageThemeFile = "res\\sound\\StageTheme.mp3";
    public static final Media soundStageTheme = new Media(new File(stageThemeFile).toURI().toString());
    public static final String placeBombFile = "res\\sound\\place_bomb.wav";
    public static final Media soundPlaceBomb = new Media(new File(placeBombFile).toURI().toString());
    public static final String bombExplodeFile = "res\\sound\\bomb_explode.wav";
    public static final Media soundExplode = new Media(new File(bombExplodeFile).toURI().toString());
    public static final String diedFile = "res\\sound\\died.mp3";
    public static final Media soundDied = new Media(new File(diedFile).toURI().toString());
    public static final String soundLevelFile = "res\\sound\\levelStart.mp3";
    public static final Media soundLevel = new Media(new File(soundLevelFile).toURI().toString());
    public static final String soundWinFile = "res\\sound\\winSound.mp3";
    public static final Media soundWin = new Media(new File(soundWinFile).toURI().toString());
    public static final String soundPowerFile = "res\\sound\\power.wav";
    public static final Media soundPower = new Media(new File(soundPowerFile).toURI().toString());

}