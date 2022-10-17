package uet.oop.bomberman.entities;

import org.jetbrains.annotations.NotNull;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.Utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

import java.sql.Struct;
import java.util.*;

public class Oneal extends Entity {

    private WALK_TYPE status;

    private int time = 0;

    private char[][] grid = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    List<Character> listWay = new ArrayList<>();
    enum WALK_TYPE {
        LEFT, RIGHT, UP, DOWN, DIE
    }

    public Oneal(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        img = sprite.getFxImage();
        status = WALK_TYPE.RIGHT;
        w = 47;
        h = 47;
    }
    public void moveMent() {

        BFS();
        if (listWay != null) {
            switch (listWay.get(0)) {
                case 'U':
                    moveUp();
                    break;
                case 'D':
                    moveDown();
                    break;
                case 'R':
                    moveRight();
                    break;
                case 'L':
                    moveLeft();
                    break;
                default:
                    break;
            }
            listWay.remove(listWay.get(0));
        }




//        BombermanGame.map.mapCollision(this);

        x += movevalX;
        y += movevalY;

    }

    public boolean valid(String moves) {

        int startX = 0;
        int startY = 0;

        firstLoop:
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 's') {
                    startY = i;
                    startX = j;
                    break firstLoop;
                }
            }
        }


        int x = startX;
        int y = startY;

        for (int z = 0; z < moves.length(); z++) {
            if (moves.charAt(z) == 'L') x -= 1;
            else if (moves.charAt(z) == 'R') x += 1;
            else if (moves.charAt(z) == 'U') y -= 1;
            else if (moves.charAt(z) == 'D') y += 1;

            if (!(0 <= x && x < grid[0].length && 0 <= y && y < grid.length)) {
                return false;
            } else if (grid[y][x] == '0') {
                return false;
            }
        }
        return true;
    }

    public boolean findEnd(String moves) {
            int startX = 0;
            int startY = 0;

            firstLoop:
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 's') {
                        startY = i;
                        startX = j;
                        break firstLoop;
                    }
                }
            }

            int x = startX;
            int y = startY;

            for (int z = 0; z < moves.length(); z++) {
                if (moves.charAt(z) == 'L') x -= 1;
                else if (moves.charAt(z) == 'R') x += 1;
                else if (moves.charAt(z) == 'U') y -= 1;
                else if (moves.charAt(z) == 'D') y += 1;
            }

            if (grid[y][x] == 'd') {
                for (int i = 0; i < listWay.size(); i++) {
                    listWay.remove(listWay.get(i));
                }
                for (int i = 0; i < moves.length(); i++) {
                    listWay.add(moves.charAt(i));
                }

                System.out.println(moves);
                return true;
            }
        return false;
    }

    public void BFS() {
        Queue<String> nums = new LinkedList<>();
        nums.add("");
        String add = "";
        String direct = "LRUD";
        setGrid();
        for (int i = 0; i < listWay.size(); i++) {
            listWay.remove(listWay.get(i));
        }

        while (!findEnd(add)) {
            add = nums.poll();
            boolean check = false;
            for (int j = 0; j < direct.length(); j++) {
                String put = add + direct.charAt(j);
                if (valid(put)) {
                    nums.add(put);
                    check = true;
                }
            }
            if (!check) {
                break;
            }
        }
    }

    public void setGrid() {
        for (int i = 0; i < BombermanGame.WIDTH; i++) {
            for (int j = 0; j < BombermanGame.HEIGHT; j++) {
                if (BombermanGame.map.getTILE_MAP()[j][i] == '#'
                        || BombermanGame.map.getTILE_MAP()[j][i] == '*'
                        || BombermanGame.map.getTILE_MAP()[j][i] == 'b'
                        || BombermanGame.map.getTILE_MAP()[j][i] == 'I') {
                    grid[j][i] = '0';
                } else {
                    grid[j][i] = '*';
                }
            }
        }

        grid[BombermanGame.bomberman.getY() / ConstVar.TILE_SIZE][BombermanGame.bomberman.getX() / ConstVar.TILE_SIZE] = 'd';
        grid[this.y / ConstVar.TILE_SIZE][this.x / ConstVar.TILE_SIZE] = 's';

    }

    public void moveLeft() {
        status = WALK_TYPE.LEFT;
        movevalX = -ConstVar.ONEAL_SPEED;
        movevalY = 0;
    }

    public void moveRight() {
        status = WALK_TYPE.RIGHT;
        movevalX = ConstVar.ONEAL_SPEED;
        movevalY = 0;
    }

    public void moveUp() {
        status = WALK_TYPE.UP;
        movevalY = -ConstVar.ONEAL_SPEED;
        movevalX = 0;
    }

    public void moveDown() {
        status = WALK_TYPE.DOWN;
        movevalY = ConstVar.ONEAL_SPEED;
        movevalX = 0;
    }

    public void animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,9).getFxImage();
            time += 0.5;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3,time,9).getFxImage();
            time += 0.5;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2,Sprite.oneal_left3,time,9).getFxImage();
            time += 0.5;
        }
    }

//    public void isDead() {
//        if(!life) {
//            movevalX = 0;
//            movevalY = 0;
//            img = Sprite.movingSprite(Sprite.oneal_dead,
//                    Sprite.oneal_dead,Sprite.oneal_dead,time,9).getFxImage();
//            time += 0.5;
//            if (timeDead >= 50) {
//                BombermanGame.entities.remove(this);
//                timeDead = 0;
//            } else {
//                timeDead++;
//            }
//        }
//    }

    @Override
    public void update() {
        moveMent();
        animation();
//        isDead();
    }
}