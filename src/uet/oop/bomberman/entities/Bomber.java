package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

class Input {
    int left;
    int right;
    int up;
    int down;
}
public class Bomber extends Entity {

    private int movevalX;
    private int movevalY;

    public static final int SPEED = 6;
    protected  Input user_input = new Input();
    private WALKTYPE status ;

    enum WALKTYPE
    {
        RIGHT , LEFT , UP, DOWN
    }
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        movevalX = 0;
        movevalY = 0;
        status = WALKTYPE.RIGHT;
        user_input.left = 0;
        user_input.right = 0;
        user_input.down = 0;
        user_input.up = 0;

    }

    public int getMovevalX() {
        return movevalX;
    }

    public void setMovevalX(int movevalX) {
        this.movevalX = movevalX;
    }

    public int getMovevalY() {
        return movevalY;
    }

    public void setMovevalY(int movevalY) {
        this.movevalY = movevalY;
    }

    public void MoveMent() {
        movevalX = 0;
        movevalY = 0;

        if(user_input.right == 1) {
            movevalX += SPEED;
        }else if(user_input.left == 1) {
            movevalX -= SPEED;
        }
        if(user_input.up == 1){
            movevalY-= SPEED;
        }else if(user_input.down == 1) {
            movevalY +=SPEED;
        }

        x+=movevalX;
        y+=movevalY;
    }
    public void HandlingInput() {
        BombermanGame.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        status = WALKTYPE.LEFT;
                        user_input.left = 1;
                        user_input.right = 0;
                        break;
                    case D:
                        status = WALKTYPE.RIGHT;
                        user_input.left = 0;
                        user_input.right = 1;;
                        break;
                    case S:
                        status = WALKTYPE.DOWN;
                        user_input.down = 1;
                        user_input.up = 0;
                        break;
                    case W:
                        status = WALKTYPE.UP;
                        user_input.up = 1;
                        user_input.down = 0;
                        break;
                }

            }
        });

        BombermanGame.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        user_input.left = 0;
                        break;
                    case D:
                        user_input.right = 0;
                        break;
                    case S:
                        user_input.down = 0;
                        break;
                    case W:
                        user_input.up = 0;
                        break;
                }

            }
        });
    }
    @Override
    public void update() {
        HandlingInput();
        MoveMent();
    }

//    public void moveUp(){
//        y-=speed;
//    }
//
//    public void moveDown(){
//        y+=speed;
//    }
//
//    public void moveRight(){
//        x+=speed;
//    }
//
//    public void  moveLeft(){
//        x-=speed;
//    }
}
