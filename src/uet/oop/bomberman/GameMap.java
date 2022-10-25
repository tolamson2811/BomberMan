package uet.oop.bomberman;
import uet.oop.bomberman.StillObjects.*;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class GameMap {
    private char[][] TILE_MAP = new char[ConstVar.HEIGHT][ConstVar.WIDTH];
    private int TILE_SIZE = ConstVar.TILE_SIZE;

    public char[][] getTILE_MAP() {
        return TILE_MAP;
    }

    public void setTILE_MAP(int i, int j, char c) {
        TILE_MAP[i][j] = c;
    }

    public void ReadMap() {
        try {
            File maptxt = new File("res/levels/" + BombermanGame.countLevel + ".txt");
            Scanner myReader = new Scanner(maptxt);
            int j = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++) {
                    TILE_MAP[j][i] = data.charAt(i);
                }
                j++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void LoadMap() {
        for (int i = 0; i < ConstVar.WIDTH; i++) {
            for (int j = 0; j < ConstVar.HEIGHT; j++) {
                StillObject object;
                double a = Math.random();
                if (TILE_MAP[j][i] == '#') {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else if (TILE_MAP[j][i] == 'I') {
                    BombermanGame.stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    if (a <= 0.3) {
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.SPEED));
                    } else if (a <= 0.4) {
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.BOMB));
                    } else if(a<= 0.7){
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.FLAME));
                    } else if (a<= 0.9) {
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.WALLPASS));
                    }else if (a<= 0.95) {
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.BOMBPASS));
                    }else{
                        BombermanGame.stillObjects.add(new Items(i, j, Items.TYPE.FLAMEPASS));
                    }
                    object = new Wall(i, j, Sprite.brick.getFxImage());
                } else if (TILE_MAP[j][i] == 'B') {
                    BombermanGame.entities.add(new Ballom(i, j, Sprite.balloom_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                } else if (TILE_MAP[j][i] == '*') {
                    BombermanGame.stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                    object = new Wall(i, j, Sprite.brick.getFxImage());
                } else if(TILE_MAP[j][i] == 'O'){
                    BombermanGame.entities.add(new Oneal(i,j,Sprite.oneal_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }else if(TILE_MAP[j][i] == 'D'){
                    BombermanGame.entities.add(new Doll(i,j,Sprite.doll_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                } else if (TILE_MAP[j][i] == 'K') {
                    BombermanGame.entities.add(new Kondoria(i,j,Sprite.kondoria_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                } else if (TILE_MAP[j][i] == 'V') {
                    BombermanGame.entities.add(new Ovapi(i,j,Sprite.ovapi_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                } else if (TILE_MAP[j][i] == 'M') {
                    BombermanGame.entities.add(new Minvo(i,j,Sprite.minvo_right1));
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                } else if(TILE_MAP[j][i] == 'X'){
                    BombermanGame.stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));

                    BombermanGame.stillObjects.add(new Portal(i,j,Sprite.portal.getFxImage()));
                    object = new Wall(i,j,Sprite.brick.getFxImage());
                }else{
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                BombermanGame.stillObjects.add(object);
            }
        }
    }

    public void mapCollision(Entity entity) {
        int x1;
        int x2;
        int y1;
        int y2;
        int Cx1 = entity.getCurrentX1();
        int Cx2 = entity.getCurrentX2();
        int Cy1 = entity.getCurrentY1();
        int Cy2 = entity.getCurrentY2();
        if (entity instanceof Bomber) {

            Cx1 = (int) (entity.getX() + 1) / TILE_SIZE;
            Cx2 = (int) (entity.getX() + entity.getW() - 1) / TILE_SIZE;
            Cy1 = (entity.getY()+1) / TILE_SIZE;
            Cy2 = (entity.getY() + entity.getH() - 1) / TILE_SIZE;
            entity.setInBomb(BombermanGame.map.getTILE_MAP()[Cy1][Cx1] == 'b'
                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx1] == 'b'
                    || BombermanGame.map.getTILE_MAP()[Cy1][Cx2] == 'b'
                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx2] == 'b');

        }
        x1 = (int) (entity.getX() + entity.getMovevalX()) / TILE_SIZE;
        x2 = (int) (entity.getX() + entity.getMovevalX() + entity.getW() - 1) / TILE_SIZE;
        y1 = entity.getY() / TILE_SIZE;
        y2 = (entity.getY() + entity.getH() - 1) / TILE_SIZE;

        //chech ngang
        if (entity.getMovevalX() > 0) {
            if(TILE_MAP[y1][x2] == '*' || TILE_MAP[y2][x2] == '*' || TILE_MAP[y1][x2] == 'I' || TILE_MAP[y2][x2] == 'I') {
                if(!entity.isWall_pass()) {
                    entity.setMovevalX(0);
                }else{
                    if (TILE_MAP[y1][x2] == '#' && TILE_MAP[y2][x2] != '#' ) {
                        if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < 26) {
                            entity.plusMoveValY();
                            entity.plusMoveValX();
                        }
                    } else if (TILE_MAP[y1][x2] != '#' && TILE_MAP[y2][x2] == '#') {
                        if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > 32) {
                            entity.minusMoveValY();
                            entity.plusMoveValX();
                        }
                    }
                }

            }
            if (TILE_MAP[y1][x2] == '#' || TILE_MAP[y2][x2] == '#'
                    || TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b'
                    ) {
                if (TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b'  ) {
                    if (entity instanceof Bomber) {
                        if (!entity.isInBomb() && !entity.isBom_pass()) {
                            entity.setMovevalX(0);
                        } else {
                            if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1] == 'b'
                                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx1] == 'b') {
                                if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1 + 1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx1 + 1] == 'b') {
                                    entity.setMovevalX(0);
                                }
                            } else if (BombermanGame.map.getTILE_MAP()[Cy1][Cx2] == 'b'
                                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx2] == 'b') {
                                if (BombermanGame.map.getTILE_MAP()[Cy1][Cx2 + 1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx2 + 1] == 'b') {
                                    entity.setMovevalX(0);
                                }
                            }
                        }
                    } else {
                        if(!entity.isBom_pass()) {
                            entity.setMovevalX(0);
                        }
                    }
                } else {

                    entity.setMovevalX(0);
                }

            }
            //SUpport moevment
            if (entity instanceof Bomber) {

                if ((TILE_MAP[y1][x2] == '*' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != 'I')
                        || (TILE_MAP[y1][x2] == '#' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != 'I')
                        || (TILE_MAP[y1][x2] == 'I' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != 'I')) {
                    if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < 26) {
                        entity.plusMoveValY();
                        entity.plusMoveValX();
                    }
                } else if ((TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != '#' && TILE_MAP[y2][x2] == '*' && TILE_MAP[y1][x2] != 'I')
                        || (TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y2][x2] == '#' && TILE_MAP[y1][x2] != 'I')
                        || (TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y2][x2] == 'I' && TILE_MAP[y1][x2] != 'I')) {
                    if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > 32) {
                        entity.minusMoveValY();
                        entity.plusMoveValX();
                    }
                }
            }

        } else if (entity.getMovevalX() < 0) {
            if(TILE_MAP[y1][x1] == '*' || TILE_MAP[y2][x1] == '*' || TILE_MAP[y1][x1] == 'I' || TILE_MAP[y2][x1] == 'I') {
                if(!entity.isWall_pass()) {
                    entity.setMovevalX(0);

                }else{
                    if (TILE_MAP[y1][x1] == '#' && TILE_MAP[y2][x1] != '#') {
                        if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < 26) {
                            entity.plusMoveValY();
                            entity.minusMoveValX();
                        }
                    } else if (TILE_MAP[y1][x1] != '#' && TILE_MAP[y2][x1] == '#') {
                        if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > 32) {
                            entity.minusMoveValY();
                            entity.minusMoveValX();
                        }
                    }
                }
            }
            if (TILE_MAP[y1][x1] == '#' || TILE_MAP[y2][x1] == '#'
                    || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b'
                    ) {
                if (TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b') {
                    if (entity instanceof Bomber) {
                        if (!entity.isInBomb() && !entity.isBom_pass()) {
                            entity.setMovevalX(0);
                        } else {
                            if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1] == 'b'
                                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx1] == 'b') {
                                if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1 - 1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx1 - 1] == 'b') {
                                    entity.setMovevalX(0);
                                }
                            } else if (BombermanGame.map.getTILE_MAP()[Cy1][Cx2] == 'b'
                                    || BombermanGame.map.getTILE_MAP()[Cy2][Cx2] == 'b') {
                                if (BombermanGame.map.getTILE_MAP()[Cy1][Cx2 - 1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx2 - 1] == 'b') {
                                    entity.setMovevalX(0);
                                }
                            }
                        }
                    } else {
                        if(!entity.isBom_pass()) {
                            entity.setMovevalX(0);
                        }
                    }
                } else {
                    entity.setMovevalX(0);
                }
            }
            if (entity instanceof Bomber){
                if ((TILE_MAP[y1][x1] == '*' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != 'I')
                        || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != 'I')
                        || (TILE_MAP[y1][x1] == 'I' && TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != 'I')) {
                    if (y1 * TILE_SIZE + TILE_SIZE - entity.getY() < 26) {
                        entity.plusMoveValY();
                        entity.minusMoveValX();
                    }
                } else if ((TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x1] != '#' && TILE_MAP[y2][x1] == '*'  && TILE_MAP[y1][x1] != 'I')
                        || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y2][x1] == '#' && TILE_MAP[y1][x1] != 'I')
                        || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y2][x1] == 'I' && TILE_MAP[y1][x1] != 'I')) {
                    if (y2 * TILE_SIZE + TILE_SIZE - (entity.getY() + entity.getH()) > 32) {
                        entity.minusMoveValY();
                        entity.minusMoveValX();
                    }
                }
            }

        }


            //Check doc
        x1 = entity.getX() / TILE_SIZE;
        x2 = (entity.getX() + entity.getW()) / TILE_SIZE;

        y1 = (int) (entity.getY() + entity.getMovevalY()) / TILE_SIZE;
        y2 = (int) (entity.getY() + entity.getMovevalY() + entity.getH() - 1) / TILE_SIZE;

        if (x1 >= 0 && y1 >= 0) {
            if (entity.getMovevalY() > 0) {
                if(TILE_MAP[y2][x1] == '*' || TILE_MAP[y2][x2] == '*' || TILE_MAP[y2][x1] == 'I' || TILE_MAP[y2][x2] == 'I') {
                    if(!entity.isWall_pass()) {
                        entity.setMovevalY(0);
                    }else {
                        if (TILE_MAP[y2][x1] == '#' && TILE_MAP[y2][x2] != '#') {
                            if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < 26) {
                                entity.plusMoveValX();
                                entity.plusMoveValY();
                            }
                        } else if (TILE_MAP[y2][x1] != '#'  && TILE_MAP[y2][x2] == '#') {
                            if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > 32) {
                                entity.minusMoveValX();
                                entity.plusMoveValY();
                            }
                        }
                    }
                }
                if (TILE_MAP[y2][x1] == '#' || TILE_MAP[y2][x2] == '#'
                        || TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b'
                        ) {
                    if (TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b') {
                        if (entity instanceof Bomber) {
                            if (!entity.isInBomb() && !entity.isBom_pass()) {
                                entity.setMovevalY(0);
                            } else {
                                if (BombermanGame.map.getTILE_MAP()[Cy2][Cx1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx2] == 'b') {
                                    if (BombermanGame.map.getTILE_MAP()[Cy2 + 1][Cx1] == 'b'
                                            || BombermanGame.map.getTILE_MAP()[Cy2 + 1][Cx2] == 'b') {
                                        entity.setMovevalY(0);
                                    }
                                } else if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy1][Cx2] == 'b') {
                                    if (BombermanGame.map.getTILE_MAP()[Cy1 + 1][Cx1] == 'b'
                                            || BombermanGame.map.getTILE_MAP()[Cy1 + 1][Cx1] == 'b') {
                                        entity.setMovevalY(0);
                                    }
                                }
                            }
                        } else {
                            if(!entity.isBom_pass()) {
                                entity.setMovevalY(0);
                            }
                        }
                    } else {
                            entity.setMovevalY(0);
                    }

                }
                if (entity instanceof Bomber){

                    if ((TILE_MAP[y2][x1] == '*' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != 'I')
                            || (TILE_MAP[y2][x1] == '#' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != 'I')
                            || (TILE_MAP[y2][x1] == 'I' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != 'I')) {
                        if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < 26) {
                            entity.plusMoveValX();
                            entity.plusMoveValY();
                        }
                    } else if ((TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x2] == '*' && TILE_MAP[y2][x1] != 'I')
                            || (TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x2] == '#' && TILE_MAP[y2][x1] != 'I')
                            || (TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x2] == 'I' && TILE_MAP[y2][x1] != 'I')) {
                        if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > 32) {
                            entity.minusMoveValX();
                            entity.plusMoveValY();
                        }
                    }
                }
            } else if (entity.getMovevalY() < 0) {
                if(TILE_MAP[y1][x1] == '*' || TILE_MAP[y1][x2] == '*'|| TILE_MAP[y1][x1] == 'I' || TILE_MAP[y1][x2] == 'I') {
                    if(!entity.isWall_pass()) {
                        entity.setMovevalY(0);
                    }else{
                        if (TILE_MAP[y1][x1] != '#'&& TILE_MAP[y1][x2] == '#') {
                            if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > 32) {
                                entity.minusMoveValX();
                                entity.minusMoveValY();
                            }
                        } else if (TILE_MAP[y1][x1] == '#' && TILE_MAP[y1][x2] != '#') {
                            if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < 26) {
                                entity.plusMoveValX();
                                entity.minusMoveValY();
                            }
                        }
                    }
                }
                if (TILE_MAP[y1][x1] == '#' || TILE_MAP[y1][x2] == '#'
                        || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b'
                        ) {
                    if (TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b') {
                        if (entity instanceof Bomber) {
                            if (!entity.isInBomb() && !entity.isBom_pass()) {
                                entity.setMovevalY(0);
                            } else {
                                if (BombermanGame.map.getTILE_MAP()[Cy1][Cx1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy1][Cx2] == 'b') {
                                    if (BombermanGame.map.getTILE_MAP()[Cy1 - 1][Cx1] == 'b'
                                            || BombermanGame.map.getTILE_MAP()[Cy1 - 1][Cx2] == 'b') {
                                        entity.setMovevalY(0);
                                    }
                                } else if (BombermanGame.map.getTILE_MAP()[Cy2][Cx1] == 'b'
                                        || BombermanGame.map.getTILE_MAP()[Cy2][Cx2] == 'b') {
                                    if (BombermanGame.map.getTILE_MAP()[Cy2 - 1][Cx1] == 'b'
                                            || BombermanGame.map.getTILE_MAP()[Cy2 - 1][Cx2] == 'b') {
                                        entity.setMovevalY(0);
                                    }
                                }
                            }
                        } else {
                            if(!entity.isBom_pass()) {
                                entity.setMovevalY(0);
                            }

                        }
                    } else {
                            entity.setMovevalY(0);
                    }

                }
                if (entity instanceof Bomber){
                    if ((TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x2] == '*' && TILE_MAP[y1][x1] != 'I')
                            || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x2] == '#' && TILE_MAP[y1][x1] != 'I')
                            || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x2] == 'I' && TILE_MAP[y1][x1] != 'I')) {
                        if (x2 * TILE_SIZE + TILE_SIZE - (entity.getX() + entity.getW()) > 32) {
                            entity.minusMoveValX();
                            entity.minusMoveValY();
                        }
                    } else if ((TILE_MAP[y1][x1] == '*' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != 'I')
                            || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != 'I')
                            || (TILE_MAP[y1][x1] == 'I' && TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != 'I')) {
                        if (x1 * TILE_SIZE + TILE_SIZE - entity.getX() < 26) {
                            entity.plusMoveValX();
                            entity.minusMoveValY();
                        }
                    }
                }

            }
        }
    }
}

