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
}