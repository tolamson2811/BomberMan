package uet.oop.bomberman.entities;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class GameMap {
    public char[][] TILE_MAP = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    public boolean[][] MAP_COLLISION = new boolean[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    private int startx;
    private int starty;

    public int TILE_SIZE = Sprite.SCALED_SIZE;
    
    public GameMap() {

    }
    public GameMap(int x, int y) {
        startx =x;
        starty = y;
    }

    public boolean[][] getMAP_COLLISION() {
        return MAP_COLLISION;
    }

    public void setMAP_COLLISION(int j, int i, boolean val) {
        MAP_COLLISION[j][i] = val;
    }

    public char[][] getTILE_MAP() {
        return TILE_MAP;
    }

    public void setTILE_MAP(int i, int j, char c) {
        TILE_MAP[i][j] = c;
    }
    public void ReadMap() {
        try {
            File maptxt = new File("target/classes/levels/map.txt");
            Scanner myReader = new Scanner(maptxt);
            int j = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                for(int i = 0;i < data.length();i++ ) {
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
        for (int i = 0; i < BombermanGame.WIDTH; i++) {
            for (int j = 0; j < BombermanGame.HEIGHT; j++) {
                Entity object;
                if(TILE_MAP[j][i] == '#') {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    MAP_COLLISION[j][i] = true;
                }else if(TILE_MAP[j][i] == '*') {
                    object = new Wall(i, j, Sprite.brick.getFxImage());
                    MAP_COLLISION[j][i] = true;
                }else{
                    object = new Grass(i,j,Sprite.grass.getFxImage());
                    MAP_COLLISION[j][i] = false;
                }

                BombermanGame.stillObjects.add(object);
            }
        }
    }

    public void mapCollision(Entity entity) {

        entity.isInBomb = BombermanGame.map.getTILE_MAP()[(int) entity.y / TILE_SIZE][(int) entity.x / TILE_SIZE] == 'b'
                || BombermanGame.map.getTILE_MAP()[(int) entity.y / TILE_SIZE][(int) (entity.x + entity.w) / TILE_SIZE] == 'b'
                || BombermanGame.map.getTILE_MAP()[(int) (entity.y + entity.h) / TILE_SIZE][(int) entity.x / TILE_SIZE] == 'b'
                || BombermanGame.map.getTILE_MAP()[(int) (entity.y + entity.h) / TILE_SIZE][(int) (entity.x + entity.w) / TILE_SIZE] == 'b';
        
        int x1 = 0;
        int x2 = 0;

        int y1 = 0;
        int y2 = 0;


        x1 = (int) (entity.x + entity.getMovevalX()) / TILE_SIZE;
        x2 = (int) (entity.x + entity.getMovevalX() + entity.w - 1) / TILE_SIZE;

        y1 = (int) entity.y / TILE_SIZE;
        y2 = (int) (entity.y + entity.h - 1) / TILE_SIZE;

        if(x1 >= 0 && y1 >= 0) {
            if(entity.getMovevalX() > 0) {

                if(TILE_MAP[y1][x2] == '*' || TILE_MAP[y2][x2] == '*'
                        || TILE_MAP[y1][x2] == '#' || TILE_MAP[y2][x2] == '#'
                        || TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b') {
                    if((TILE_MAP[y1][x2] == 'b' || TILE_MAP[y2][x2] == 'b')) {
                        if(!entity.isInBomb){
                            entity.x = x2 * TILE_SIZE;
                            entity.x -= entity.w + 1;
                            entity.setMovevalX(0);
                        } else if(TILE_MAP[y1][x2] == '*' || TILE_MAP[y2][x2] == '*'
                                || TILE_MAP[y1][x2] == '#' || TILE_MAP[y2][x2] == '#') {
                            entity.x = x2 * TILE_SIZE;
                            entity.x -= entity.w + 1;
                            entity.setMovevalX(0);
                        }
                    } else {
                        entity.x = x2 * TILE_SIZE;
                        entity.x -= entity.w + 1;
                        entity.setMovevalX(0);
                    }

                    if((TILE_MAP[y1][x2] == '*' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != '#')
                            || (TILE_MAP[y1][x2] == '#' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*')) {
                        if(y1*TILE_SIZE + TILE_SIZE - entity.y < 22) {
                            entity.movevalY++;
                        }
                    } else if((TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != '#' &&TILE_MAP[y2][x2] == '*')
                            || (TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*' &&TILE_MAP[y2][x2] == '#')) {
                        if(y2 * TILE_SIZE + TILE_SIZE - (entity.y + entity.h) > 40) {
                            entity.movevalY--;
                        }
                    }
                }
            } else if(entity.getMovevalX() < 0) {
                if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y2][x1] == '*')
                        || TILE_MAP[y1][x1] == '#' || TILE_MAP[y2][x1] == '#'
                        || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b') {
                    if(TILE_MAP[y1][x1] == 'b' || TILE_MAP[y2][x1] == 'b') {
                        if(!entity.isInBomb){
                            entity.x = (x1 + 1) * TILE_SIZE + 1;
                            entity.setMovevalX(0);
                        } else if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y2][x1] == '*')
                                || TILE_MAP[y1][x1] == '#' || TILE_MAP[y2][x1] == '#') {
                            entity.x = (x1 + 1) * TILE_SIZE + 1;
                            entity.setMovevalX(0);
                        }
                    } else {
                        entity.x = (x1 + 1) * TILE_SIZE + 1;
                        entity.setMovevalX(0);
                    }

                }
                if((TILE_MAP[y1][x1] == '*' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != '#')
                        || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*'))  {
                    if(y1*TILE_SIZE + TILE_SIZE - entity.y < 22) {
                        entity.movevalY++;
                    }
                } else if((TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x1] != '#' && TILE_MAP[y2][x1] == '*')
                        || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y2][x1] == '#'))  {
                    if(y2 * TILE_SIZE + TILE_SIZE - (entity.y + entity.h) > 32) {
                        entity.movevalY--;
                    }
                }
            }
        }


        //Check doc
        x1 = (int) entity.x / TILE_SIZE;
        x2 =  (int) (entity.x + entity.w) / TILE_SIZE;

        y1 = (int) (entity.y + entity.getMovevalY()) / TILE_SIZE;
        y2 = (int) (entity.y + entity.getMovevalY() + entity.h - 1) / TILE_SIZE;

        if(x1 >= 0 && y1 >= 0) {
            if(entity.getMovevalY() > 0) {
                if( (TILE_MAP[y2][x1] == '*' || TILE_MAP[y2][x2] == '*')
                        || TILE_MAP[y2][x1] == '#' || TILE_MAP[y2][x2] == '#'
                        || TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b') {
                    if(TILE_MAP[y2][x1] == 'b' || TILE_MAP[y2][x2] == 'b') {
                        if(!entity.isInBomb){
                            entity.y = y2 * TILE_SIZE;
                            entity.y -= (entity.h + 1);
                            entity.setMovevalY(0);
                        } else if((TILE_MAP[y2][x1] == '*' || TILE_MAP[y2][x2] == '*')
                                || TILE_MAP[y2][x1] == '#' || TILE_MAP[y2][x2] == '#') {
                            entity.y = y2 * TILE_SIZE;
                            entity.y -= (entity.h + 1);
                            entity.setMovevalY(0);
                        }
                    } else {
                        entity.y = y2 * TILE_SIZE;
                        entity.y -= (entity.h + 1);
                        entity.setMovevalY(0);
                    }

                    if((TILE_MAP[y2][x1] == '*' && TILE_MAP[y2][x2] != '*' && TILE_MAP[y2][x2] != '#')
                            || (TILE_MAP[y2][x1] == '#' && TILE_MAP[y2][x2] != '#' && TILE_MAP[y2][x2] != '*')) {
                        if(x1 * TILE_SIZE + TILE_SIZE - entity.x < 8) {
                            entity.movevalX++;
                        }
                    } else if((TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x1] != '#' &&TILE_MAP[y2][x2] == '*')
                            || (TILE_MAP[y2][x1] != '#' && TILE_MAP[y2][x1] != '*' && TILE_MAP[y2][x2] == '#')) {
                        if(x2 * TILE_SIZE + TILE_SIZE - (entity.x + entity.w) > 40) {
                            entity.movevalX--;
                        }
                    }
                }
            } else if(entity.getMovevalY() < 0) {
                if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y1][x2] == '*')
                        || TILE_MAP[y1][x1] == '#' || TILE_MAP[y1][x2] == '#'
                        || TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b') {
                    if(TILE_MAP[y1][x1] == 'b' || TILE_MAP[y1][x2] == 'b') {
                        if(!entity.isInBomb){
                            entity.y = (y1 + 1) * TILE_SIZE;
                            entity.setMovevalY(0);
                        } else if((TILE_MAP[y1][x1] == '*' || TILE_MAP[y1][x2] == '*')
                                || TILE_MAP[y1][x1] == '#' || TILE_MAP[y1][x2] == '#') {
                            entity.y = (y1 + 1) * TILE_SIZE;
                            entity.setMovevalY(0);
                        }
                    } else {
                        entity.y = (y1 + 1) * TILE_SIZE;
                        entity.setMovevalY(0);
                    }

                }
                if((TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x2] == '*')
                        || (TILE_MAP[y1][x1] != '#' && TILE_MAP[y1][x1] != '*' && TILE_MAP[y1][x2] == '#')) {
                    if(x2 * TILE_SIZE + TILE_SIZE - (entity.x + entity.w) > 40) {
                        entity.movevalX--;
                    }
                } else if((TILE_MAP[y1][x1] == '*' && TILE_MAP[y1][x2] != '*' && TILE_MAP[y1][x2] != '#')
                        || (TILE_MAP[y1][x1] == '#' && TILE_MAP[y1][x2] != '#' && TILE_MAP[y1][x2] != '*')) {
                    if(x1 * TILE_SIZE + TILE_SIZE - entity.x < 8) {
                        entity.movevalX++;
                    }
                }
            }
        }
    }
}