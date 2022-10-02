package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.GameMap;

import java.util.Timer;
import java.util.TimerTask;

class Input {
    int left;
    int right;
    int up;
    int down;
}
public class Bomber extends Entity {

    private int movevalX;
    private int movevalY;

    GameMap temp = new GameMap();

    public void init() {
        temp.ReadMap();
        temp.LoadMap();
    }
    public static final int SPEED = 2;
    protected  Input user_input = new Input();

    public int width_frame = 30;

    public int height_frame = 42;

    public int TILE_SIZE = 48;
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

    public double time = 0;

    public void MoveMent() {
        movevalX = 0;
        movevalY = 0;

        if(user_input.right == 1) {
            movevalX += SPEED;
            if(time % 9 == 0) {
                img = Sprite.player_right_1.getFxImage();
            } else if(time % 9 == 4) {
                img = Sprite.player_right_2.getFxImage();
            } else if(time % 9 == 8) {
                img = Sprite.player_right.getFxImage();
            }
            time += 0.5;

        } else if(user_input.left == 1) {
            movevalX -= SPEED;

            if(time % 9 == 0) {
                img = Sprite.player_left_1.getFxImage();
            } else if(time % 9 == 4) {
                img = Sprite.player_left_2.getFxImage();
            } else if(time % 9 == 8) {
                img = Sprite.player_left.getFxImage();
            }

            time += 0.5;
        }
        if(user_input.up == 1){
            movevalY -= SPEED;
            if(time % 9 == 0) {
                img = Sprite.player_up_1.getFxImage();
            } else if(time % 9 == 4) {
                img = Sprite.player_up_2.getFxImage();
            } else if(time % 9 == 8) {
                img = Sprite.player_up.getFxImage();
            }

            time += 0.5;
        }else if(user_input.down == 1) {
            movevalY += SPEED;


            if(time % 9 == 0) {
                img = Sprite.player_down_1.getFxImage();
            } else if(time % 9 == 4) {
                img = Sprite.player_down_2.getFxImage();
            } else if(time % 9 == 8) {
                img = Sprite.player_down.getFxImage();
            }

            time += 0.5;
        }

        checkCollisions(temp.getMAP_COLLISION());

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
                        time = 0;
                        img = Sprite.player_left.getFxImage();
                        break;
                    case D:
                        user_input.right = 0;
                        time = 0;
                        img = Sprite.player_right.getFxImage();
                        break;
                    case S:
                        user_input.down = 0;
                        time = 0;
                        img = Sprite.player_down.getFxImage();
                        break;
                    case W:
                        user_input.up = 0;
                        time = 0;
                        img = Sprite.player_up.getFxImage();
                        break;
                }

            }
        });
    }

    public void checkCollisions(boolean[][] MAP_COLISION ) {
        int x1 = 0;
        int x2 = 0;

        int y1 = 0;
        int y2 = 0;

        x1 = (this.x + this.getMovevalX()) / TILE_SIZE;
        x2 = (this.x + this.getMovevalX() + width_frame - 1) / TILE_SIZE;

        y1 = this.y / TILE_SIZE;
        y2 = (this.y + height_frame - 1) / TILE_SIZE;

        if(x1 >= 0 && y1 >= 0) {
            if(this.getMovevalX() > 0) {
                if(MAP_COLISION[y1][x2] || MAP_COLISION[y2][x2]) {
                    this.x = x2 * TILE_SIZE;
                    this.x -= width_frame + 1;
                    this.setMovevalX(0);
                    if(MAP_COLISION[y1][x2] && !MAP_COLISION[y2][x2]) {
                        this.movevalY++;
                    } else if(!MAP_COLISION[y1][x2] && MAP_COLISION[y2][x2]) {
                        this.movevalY--;
                    }
                }
            } else if(this.getMovevalX() < 0) {
                if(MAP_COLISION[y1][x1] || MAP_COLISION[y2][x1]) {
                    this.x = (x1 + 1) * TILE_SIZE + 1;
                    this.setMovevalX(0);
                }
                if(MAP_COLISION[y1][x1] && !MAP_COLISION[y2][x1]) {
                    this.movevalY++;
                } else if(!MAP_COLISION[y1][x1] && MAP_COLISION[y2][x1]) {
                    this.movevalY--;
                }
            }
        }


        //Check doc
        x1 = this.x / TILE_SIZE;
        x2 = (this.x + width_frame) / TILE_SIZE;

        y1 = (this.y + this.getMovevalY()) / TILE_SIZE;
        y2 = (this.y + this.getMovevalY() + height_frame - 1) / TILE_SIZE;

        if(x1 >= 0 && y1 >= 0) {
            if(this.getMovevalY() > 0) {
                if(MAP_COLISION[y2][x1] || MAP_COLISION[y2][x2]) {
                    this.y = y2 * TILE_SIZE;
                    this.y -= (height_frame + 1);
                    this.setMovevalY(0);
                    if(MAP_COLISION[y2][x1] && !MAP_COLISION[y2][x2]) {
                        this.movevalX++;
                    } else if(!MAP_COLISION[y2][x2] && MAP_COLISION[y2][x2]) {
                        this.movevalX--;
                    }
                }
            } else if(this.getMovevalY() < 0) {
                if(MAP_COLISION[y1][x1] || MAP_COLISION[y1][x2]) {
                    this.y = (y1 + 1) * TILE_SIZE;
                    this.setMovevalY(0);
                }
                if(!MAP_COLISION[y1][x1] && MAP_COLISION[y1][x2]) {
                    this.movevalX--;
                } else if(MAP_COLISION[y1][x1] && !MAP_COLISION[y1][x2]) {
                    this.movevalX++;
                }
            }
        }
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
