package uet.oop.bomberman.entities;

import uet.oop.bomberman.StillObjects.Items;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Bombs.Bomb;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {

    static class Input {
        int left;
        int right;
        int up;
        int down;
        boolean bomb;
    }
    private boolean PlaceBom;

    private int BOMBER_SPEED = 2;
    private int BOMB_NUMBER = 1;
    private double time = 0;
    private Input user_input = new Input();
    private WALK_TYPE status;
    private final List<Bomb> bomstack = new ArrayList<>();

    private int B_radius;




    public Bomber(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALK_TYPE.RIGHT;
        user_input.left = 0;
        user_input.right = 0;
        user_input.down = 0;
        user_input.up = 0;
        img = sprite.getFxImage();
        w = 36;
        h = 47;
        PlaceBom = false;
        B_radius = 2;
        boosted = false;
        flame_pass = false;
        bom_pass = false;
        wall_pass = false;
    }



    public boolean isPlaceBom() {
        return PlaceBom;
    }

    public int getBOMBER_SPEED() {
        return BOMBER_SPEED;
    }

    public void plusBOMBER_SPEED() {
        this.BOMBER_SPEED++;
    }

    public int getBOMB_NUMBER() {
        return BOMB_NUMBER;
    }

    public void plusBOMB_NUMBER() {
        this.BOMB_NUMBER++;
    }

    public void plusB_radius() {
        this.B_radius++;
    }
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
     * KIEM TRA INPUT.
     */
    public void HandlingInput() {
        //AN NUT
        BombermanGame.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        status = WALK_TYPE.LEFT;
                        user_input.left = 1;
                        user_input.right = 0;
                        break;
                    case D:
                        status = WALK_TYPE.RIGHT;
                        user_input.left = 0;
                        user_input.right = 1;
                        ;
                        break;
                    case S:
                        status = WALK_TYPE.DOWN;
                        user_input.down = 1;
                        user_input.up = 0;
                        break;
                    case W:
                        status = WALK_TYPE.UP;
                        user_input.up = 1;
                        user_input.down = 0;
                        break;
                    case SPACE:
                        if(bomstack.size() < BOMB_NUMBER) {
                            Bomb nbomb = makeBomb(B_radius);
                            PlaceBom = true;
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
        if(bomstack.isEmpty()) {
            PlaceBom = false;
        }
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
    public Bomb makeBomb(int b_radius) {
        xblock = x/ConstVar.TILE_SIZE;
        yblock = y/ ConstVar.TILE_SIZE;
        if(x%ConstVar.TILE_SIZE >= 32) {
            xblock+=1;
        }
        if(y%ConstVar.TILE_SIZE >=24) {
            yblock +=1;
        }
        Bomb nBomb = new Bomb(xblock,
                yblock,Sprite.bomb.getFxImage());
        nBomb.setActive(true);
        nBomb.setRadius(b_radius);
        BombermanGame.map.setTILE_MAP(yblock, xblock, 'b');

        return nBomb;
    }


    public void ResetTMPBOOST() {

        if(stopWatch.getElapsedTime() > 8000) {
            BOMBER_SPEED = 2;
            bom_pass = false;
            flame_pass = false;
            wall_pass = false;
            boosted = false;
            stopWatch.stop();
        }

    }

    @Override
    public void update() {
        HandlingInput();
        MoveMent();
        Animation();
        if(boosted) {
            ResetTMPBOOST();
        }
    }

}