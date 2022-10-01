package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomb extends Entity{
    private double movevalX;
    private double movecalY;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }
}
