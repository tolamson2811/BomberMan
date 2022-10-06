package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Bomber extends Entity {

//    private double movevalX;
//    private double movevalY;
    static class Input {
        int left;
        int right;
        int up;
        int down;
        boolean bomb;
    }

//    public boolean isInBomb = false;

    public int TILE_SIZE = Sprite.SCALED_SIZE;
    public static final int BOMBER_SPEED = 2;
    public static int BOMB_NUMBER = 5;
    private double time = 0;
    protected Input user_input = new Input();
    private WALKTYPE status;
    private final List<Bomb> bomstack = new ArrayList<>();
    enum WALKTYPE {
        RIGHT, LEFT, UP, DOWN
    }

    public Bomber(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALKTYPE.RIGHT;
        user_input.left = 0;
        user_input.right = 0;
        user_input.down = 0;
        user_input.up = 0;
        img = sprite.getFxImage();
        w = 36;
        h = 47;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

//    public double getMovevalX() {
//        return movevalX;
//    }
//
//    public void setMovevalX(int movevalX) {
//        this.movevalX = movevalX;
//    }
//
//    public double getMovevalY() {
//        return movevalY;
//    }
//
//    public void setMovevalY(int movevalY) {
//        this.movevalY = movevalY;
//    }

    /**
     * MOVE BOMBER.
     */
    public void MoveMent() {
            movevalX = 0;
            movevalY = 0;

            if (user_input.right == 1) {
                movevalX += BOMBER_SPEED;
            } else if (user_input.left == 1) {
                movevalX -= BOMBER_SPEED;
            }
            if (user_input.up == 1) {
                movevalY -= BOMBER_SPEED;
            } else if (user_input.down == 1) {
                movevalY += BOMBER_SPEED;
            }

            BombermanGame.map.mapCollision(this);

            x += movevalX;
            y += movevalY;
    }

    /**
     * CHECK COLLISIONS
     * @param TILE_MAP
     */
//    public void checkCollisions(char[][] TILE_MAP) {
//        int x1 = 0;
//        int x2 = 0;
//
//        int y1 = 0;
//        int y2 = 0;
//
//
//        x1 = (int) (this.x + this.getMovevalX()) / TILE_SIZE;
//        x2 = (int) (this.x + this.getMovevalX() + w - 1) / TILE_SIZE;
//
//        y1 =  this.y / TILE_SIZE;
//        y2 = (this.y + h - 1) / TILE_SIZE;
//
//        if(x1 >= 0 && y1 >= 0) {
//            if(this.getMovevalX() > 0) {
//
//                if(TILE_MAP[y1][x2] == '*' || TILE_MAP[y2][x2] == '*'
//                   || TILE_MAP[y1][x2] == '#' || TILE_MAP[y2][x2] == '#'
//                   || TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b') {
//                    if(TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b') {
//                        if(!isInBomb){
//                            this.x = x2 * TILE_SIZE;
//                            this.x -= w + 1;
//                            this.setMovevalX(0);
//                        }
//                    } else {
//                        this.x = x2 * TILE_SIZE;
//                        this.x -= w + 1;
//                        this.setMovevalX(0);
//                    }
//
//                    if((TILE_MAP[y1][x2] == '*' && TILE_MAP[y2][x2] != '*')
//                        || (TILE_MAP[y1][x2] == '#' && TILE_MAP[y2][x2] != '#')) {
//                        if(y1*TILE_SIZE + TILE_SIZE - this.y < 22) {
//                            this.movevalY++;
//                        }
//                    } else if((TILE_MAP[y1][x2] != '*' && TILE_MAP[y2][x2] == '*')
//                                || (TILE_MAP[y1][x2] != '#' && TILE_MAP[y2][x2] == '#')) {
//                        if(y2 * TILE_SIZE + TILE_SIZE - (this.y + h) > 40) {
//                            this.movevalY--;
//                        }
//                    }
//                }
//            } else if(this.getMovevalX() < 0) {
//                if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y2][x1] == '*')
//                    || TILE_MAP[y1][x1] == '#' || TILE_MAP[y2][x1] == '#'
//                    || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b') {
//                    if(TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b') {
//                        if(!isInBomb){
//                            this.x = (x1 + 1) * TILE_SIZE + 1;
//                            this.setMovevalX(0);
//                        }
//                    } else {
//                        this.x = (x1 + 1) * TILE_SIZE + 1;
//                        this.setMovevalX(0);
//                    }
//
//                }
//                if((TILE_MAP[y1][x1] == '*' && TILE_MAP[y2][x1] != '*')
//                    || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y2][x1] != '#'))  {
//                    if(y1*TILE_SIZE + TILE_SIZE - this.y < 22) {
//                        this.movevalY++;
//                    }
//                } else if((TILE_MAP[y1][x1] != '*' && TILE_MAP[y2][x1] == '*')
//                           || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y2][x1] == '#'))  {
//                    if(y2 * TILE_SIZE + TILE_SIZE - (this.y + h) > 32) {
//                        this.movevalY--;
//                    }
//                }
//            }
//        }
//
//
//        //Check doc
//        x1 = this.x / TILE_SIZE;
//        x2 =  (this.x + w) / TILE_SIZE;
//
//        y1 = (int) (this.y + this.getMovevalY()) / TILE_SIZE;
//        y2 = (int) (this.y + this.getMovevalY() + h - 1) / TILE_SIZE;
//
//        if(x1 >= 0 && y1 >= 0) {
//            if(this.getMovevalY() > 0) {
//                if( (TILE_MAP[y2][x1] == '*' || TILE_MAP[y2][x2] == '*')
//                     || TILE_MAP[y2][x1] == '#' || TILE_MAP[y2][x2] == '#'
//                     || TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b') {
//                    if(TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b') {
//                        if(!isInBomb){
//                            this.y = y2 * TILE_SIZE;
//                            this.y -= (h + 1);
//                            this.setMovevalY(0);
//                        }
//                    } else {
//                        this.y = y2 * TILE_SIZE;
//                        this.y -= (h + 1);
//                        this.setMovevalY(0);
//                    }
//
//                    if((TILE_MAP[y2][x1] == '*' && TILE_MAP[y2][x2] != '*')
//                        || (TILE_MAP[y2][x1] == '#' && TILE_MAP[y2][x2] != '#')) {
//                        if(x1 * TILE_SIZE + TILE_SIZE - this.x < 8) {
//                            this.movevalX++;
//                        }
//                    } else if((TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x2] == '*')
//                               || (TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x2] == '#')) {
//                        if(x2 * TILE_SIZE + TILE_SIZE - (this.x + w) > 40) {
//                            this.movevalX--;
//                        }
//                    }
//                }
//            } else if(this.getMovevalY() < 0) {
//                if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y1][x2] == '*')
//                    || TILE_MAP[y1][x1] == '#' || TILE_MAP[y1][x2] == '#'
//                    || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b') {
//                    if(TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b') {
//                        if(!isInBomb){
//                            this.y = (y1 + 1) * TILE_SIZE;
//                            this.setMovevalY(0);
//                        }
//                    } else {
//                        this.y = (y1 + 1) * TILE_SIZE;
//                        this.setMovevalY(0);
//                    }
//
//                }
//                if((TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x2] == '*')
//                    || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x2] == '#')) {
//                    if(x2 * TILE_SIZE + TILE_SIZE - (this.x + w) > 40) {
//                        this.movevalX--;
//                    }
//                } else if((TILE_MAP[y1][x1] == '*' && TILE_MAP[y1][x2] != '*')
//                          || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y1][x2] != '#')) {
//                    if(x1 * TILE_SIZE + TILE_SIZE - this.x < 8) {
//                        this.movevalX++;
//                    }
//                }
//            }
//        }
//    }

    /**
     * KIEM TRA INPUT.
     */
    public void HandlingInput() {
        //AN NUT
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
                        user_input.right = 1;
                        ;
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
                    case SPACE:
                        if(bomstack.size() < BOMB_NUMBER) {
                            Bomb nbomb = makeBomb();
                            bomstack.add(nbomb);
                        }

                }

            }
        });

        //THA NUT
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

    /**
     * DO HOA NHAN VAT.
     */
    public void Animation() {
        if (user_input.right == 1) {
            img = Sprite.movingSprite(Sprite.player_right,
                    Sprite.player_right_1,Sprite.player_right_2,time,9).getFxImage();
            time += 0.5;
        } else if (user_input.left == 1) {
            img = Sprite.movingSprite(Sprite.player_left,
                    Sprite.player_left_1,Sprite.player_left_2,time,9).getFxImage();
            time += 0.5;
        }else if (user_input.up == 1) {
            img = Sprite.movingSprite(Sprite.player_up,
                    Sprite.player_up_1,Sprite.player_up_2,time,9).getFxImage();
            time += 0.5;
        } else if (user_input.down == 1) {
            img = Sprite.movingSprite(Sprite.player_down,
                    Sprite.player_down_1,Sprite.player_down_2,time,9).getFxImage();
            time += 0.5;
        }
    }

    /**
     * XU LY BOM NO.
     * @param gc
     */
    public void HandleBomb(GraphicsContext gc) {

        for (int i = 0; i < bomstack.size(); i++) {
            Bomb nbomb = bomstack.get(i);
            if (!nbomb.isActive()) {
                if(!bomstack.get(i).Explode(gc)) {
                    bomstack.remove(i);
                }
            } else {
                nbomb.render(gc);
                nbomb.update();
            }
        }
    }

    /**
     * KHOI TAO BOM
     * @return
     */
    public Bomb makeBomb() {
        xblock = x/Sprite.SCALED_SIZE;
        yblock = y/Sprite.SCALED_SIZE;
        if(x%Sprite.SCALED_SIZE >= 32) {
            xblock+=1;
        }
        if(y%Sprite.SCALED_SIZE >=24) {
            yblock +=1;
        }
        Bomb nBomb = new Bomb(xblock,
                yblock,Sprite.bomb.getFxImage());
        nBomb.setActive(true);

        BombermanGame.map.setTILE_MAP(yblock, xblock, 'b');

        return nBomb;
    }


    @Override
    public void update() {
        HandlingInput();
        MoveMent();
        Animation();
    }

}