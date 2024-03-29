package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.bombs.Bomb;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.utils.Sound;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utils.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.menus.BombermanMenu.isRunning;

public class Bomber extends Entity {



    static class Input {
        int left;
        int right;
        int up;
        int down;
    }
    private boolean PlaceBom;

    private int BOMBER_SPEED = 2;
    private int BOMB_NUMBER = 1;
    private double time = 0;
    private Input user_input = new Input();
    private final List<Bomb> bomstack = new ArrayList<>();
    private int B_radius;

    public boolean isAutopilot() {
        return autopilot;
    }

    private boolean autopilot;
    private int[][] dis = new int[ConstVar.HEIGHT][ConstVar.WIDTH];
    private boolean isStuck;
    StopWatch PowerTime = new StopWatch();
    private int aimX;
    private int aimY;
    private char targetDir;
    private Pair<Integer,Integer> Target;
    private Pair<Integer,Integer> tmpPos;
    private char priority;
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
        life = 5;
        w = 36;
        h = 46;
        PlaceBom = false;
        B_radius = 1;
        boosted = false;
        flame_pass = false;
        bom_pass = false;
        wall_pass = false;
        autopilot = false;
        isStuck  = false;
        priority = 'R';
    }

    public int getBOMBER_SPEED() {
        return BOMBER_SPEED;
    }

    public int getB_radius() {
        return B_radius;
    }


    public boolean isPlaceBom() {
        return PlaceBom;
    }
    public List<Bomb> getBomstack() {
        return bomstack;
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

    public StopWatch getPowerTime() {
        return PowerTime;
    }

    @Override
    public int getYblock() {
        if(this.y /ConstVar.TILE_SIZE > yblock || ((yblock+1)*ConstVar.TILE_SIZE - this.y <= 2
                && (yblock+1)*ConstVar.TILE_SIZE - this.y >= 0)) {
            return yblock+1;
        } else if ((this.y + this.h) /ConstVar.TILE_SIZE < yblock ||( (this.y + this.h) - (yblock-1)*ConstVar.TILE_SIZE <=2
                && (this.y + this.h) - (yblock-1)*ConstVar.TILE_SIZE >= 0)) {
            return  yblock-1;
        }
        return yblock;
    }

    @Override
    public int getXblock() {
        if(this.x /ConstVar.TILE_SIZE > xblock || ((xblock+1)*ConstVar.TILE_SIZE - this.x <= 2
                && (xblock+1)*ConstVar.TILE_SIZE - this.x >= 0)  ) {
            return xblock+1;
        } else if ((this.x + this.w) /ConstVar.TILE_SIZE < xblock ||( (this.x + this.w) - (xblock-1)*ConstVar.TILE_SIZE <=2
                && (this.x + this.w) - (xblock-1)*ConstVar.TILE_SIZE >= 0)) {
            return  xblock-1;
        }
        return xblock;
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
                            if (isRunning) {
                                Media media = Sound.soundPlaceBomb;
                                BombermanGame.placeBomb = new MediaPlayer(media);
                                BombermanGame.placeBomb.play();
                                BombermanGame.placeBomb.setVolume(0.4);
                            }
                            Bomb nbomb = makeBomb(B_radius);
                            PlaceBom = true;
                            bomstack.add(nbomb);
                        }
                        break;
                    case K:
                        if(!autopilot) {
                            autopilot = true;
                            x = getXblock()*ConstVar.TILE_SIZE;
                            y = getYblock()*ConstVar.TILE_SIZE;
                            aimX = x;
                            aimY = y;
                        }else{
                            autopilot = false;
                            user_input.left = 0;
                            user_input.right = 0;
                            user_input.up = 0;
                            user_input.down = 0;
                        }

                        break;
                    case ESCAPE:
                        BombermanGame.menu.handleShow();
                        break;
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
     * Move Manually AND AI SUPPORT.
     */
    public void MoveMent() {
        if(alive) {
            movevalX = 0;
            movevalY = 0;
            xblock = this.getXblock();
            yblock = this.getYblock();
            if(autopilot) {
                if((Math.abs(aimX - x)  == 0 && Math.abs(aimY - y) == 0 ) || isStuck) {
                    if(!PlaceBom) {
                        Pair<Integer, Integer> a = GetNearest();
                        if(a != null) {
                            PathFinding(a.getKey(),a.getValue());
                            for (int i = 0; i < ConstVar.HEIGHT; i++) {
                                StringBuilder h = new StringBuilder();
                                for (int j = 0; j < ConstVar.WIDTH; j++) {
//                                    if(dis[i][j] == Integer.MAX_VALUE) {
//                                        h.append("# ");
//                                    }else{
//                                        h.append(dis[i][j]).append(" ");
//                                    }
                                    h.append(BombermanGame.map.getTILE_MAP()[i][j]).append(' ');
                                }
                                System.out.println(h);
                            }
                            System.out.println(' ');
                            Target = a;
                            System.out.println("spot: " + BombermanGame.map.getTILE_MAP()[a.getValue()][a.getKey()] + a.getKey() + " " + a.getValue());
                        }else{
                            System.out.println("null");
                            ArrayList<Character> move = new ArrayList<Character>(Arrays.asList('L','R','U','D'));
                            if(yblock <= 3) {
                                priority = 'D';
                            }else if(yblock >= 11){
                                priority = 'U';
                            }
                            if(yblock >= 6 && yblock <=8){
                                if(xblock >= 26) {
                                    priority = 'L';
                                } else if(xblock <=5 ){
                                    priority = 'R';
                                }else{
                                    priority = 'R';
                                }
                            }
                            targetDir = AvailablePath(move,priority);
                            Target = null;
                        }
                    }


                    if(Target != null) {
                        if (BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] == 'I'
                                || BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] == 'X'
                                ) {
                            if ((Math.abs(Target.getKey() - xblock) < 2 && Math.abs(Target.getValue() - yblock) == 0)
                                    || (Math.abs(Target.getKey() - xblock) == 0 && Math.abs(Target.getValue() - yblock) < 2)
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'i'
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'x') {
                                if (bomstack.size() < 1) {
                                    if (isRunning) {
                                        Media media = Sound.soundPlaceBomb;
                                        BombermanGame.placeBomb = new MediaPlayer(media);
                                        BombermanGame.placeBomb.play();
                                    }
                                    Bomb nbomb = makeBomb(B_radius);
                                    PlaceBom = true;
                                    bomstack.add(nbomb);
                                    tmpPos = new Pair<>(xblock, yblock);
                                }
                            }
                        } else if(BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] == 'B'
                                || BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] == 'D'
                                ||BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] == 'M') {
                            if ((Math.abs(Target.getKey() - xblock) < 3 && Math.abs(Target.getValue() - yblock) == 0)
                                    || (Math.abs(Target.getKey() - xblock) == 0 && Math.abs(Target.getValue() - yblock) < 3)
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'i'
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'x') {
                                if (bomstack.size() < 1) {
                                    if (isRunning) {
                                        Media media = Sound.soundPlaceBomb;
                                        BombermanGame.placeBomb = new MediaPlayer(media);
                                        BombermanGame.placeBomb.play();
                                    }
                                    Bomb nbomb = makeBomb(B_radius);
                                    PlaceBom = true;
                                    bomstack.add(nbomb);
                                    tmpPos = new Pair<>(xblock, yblock);
                                }
                            }
                        }else{
                            if ((Math.abs(Target.getKey() - xblock) <= 5 && Math.abs(Target.getValue() - yblock) <= 5)
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'i'
                                    && BombermanGame.map.getTILE_MAP()[Target.getValue()][Target.getKey()] != 'x') {
                                if (bomstack.size() < 1) {
                                    if (isRunning) {
                                        Media media = Sound.soundPlaceBomb;
                                        BombermanGame.placeBomb = new MediaPlayer(media);
                                        BombermanGame.placeBomb.play();
                                    }
                                    Bomb nbomb = makeBomb(B_radius);
                                    PlaceBom = true;
                                    bomstack.add(nbomb);
                                    tmpPos = new Pair<>(xblock, yblock);
                                }
                            }
                        }
                        if (!PlaceBom) {
                            if (dis[yblock][xblock] - 1 == dis[yblock - 1][xblock]) {
                                targetDir = 'U';
                            } else if (dis[yblock][xblock] - 1 == dis[yblock + 1][xblock]) {
                                targetDir = 'D';
                            } else if (dis[yblock][xblock] - 1 == dis[yblock][xblock - 1]) {
                                targetDir = 'L';
                            } else if (dis[yblock][xblock] - 1 == dis[yblock][xblock + 1]) {
                                targetDir = 'R';
                            }
                        } else {
                            if (Target.getKey() > tmpPos.getKey()) {
                                //System.out.println("1");
                                ArrayList<Character> move = new ArrayList<Character>(Arrays.asList('L','U','D'));
                                targetDir = AvailablePath(move,'L');
                            } else if (Target.getKey() < tmpPos.getKey()) {
                                //System.out.println("2");
                                ArrayList<Character> move = new ArrayList<Character>(Arrays.asList('R','U','D'));
                                targetDir = AvailablePath(move,'R');
                            } else if (Target.getValue() > tmpPos.getValue()) {
                                // System.out.println("3");
                                ArrayList<Character> move = new ArrayList<Character>(Arrays.asList('L','R','U'));
                                targetDir = AvailablePath(move,'U');
                            } else if (Target.getValue() < tmpPos.getValue()) {
                                // System.out.println("4");
                                ArrayList<Character> move = new ArrayList<Character>(Arrays.asList('L','R','D'));
                                targetDir = AvailablePath(move,'D');
                            }
                        }
                    }
                    if(targetDir == 'L') {
                        aimX = (xblock-1) * ConstVar.TILE_SIZE;
                        aimY = yblock*ConstVar.TILE_SIZE;
                    } else if (targetDir == 'R') {
                        aimX = (xblock+1) * ConstVar.TILE_SIZE;
                        aimY = yblock*ConstVar.TILE_SIZE;
                    } else if (targetDir == 'U') {
                        aimY = (yblock-1)* ConstVar.TILE_SIZE;
                        aimX = xblock*ConstVar.TILE_SIZE;
                    }else if(targetDir == 'D'){
                        aimY = (yblock+1)* ConstVar.TILE_SIZE;
                        aimX = xblock*ConstVar.TILE_SIZE;
                    }
                    System.out.println(aimX + " " + aimY);
                    System.out.println(x + " " + y);
                    System.out.println(xblock + " " + yblock);
                    //System.out.println(this.getXblock() + " " + this.getYblock()) ;
                }
                user_input.up = 0;
                user_input.down = 0;
                user_input.left = 0;
                user_input.right = 0;
                switch (targetDir) {
                    case 'U':
                        status = WALK_TYPE.UP;
                        user_input.up = 1;
                        movevalY-= BOMBER_SPEED;
                        break;
                    case 'D':
                        status = WALK_TYPE.DOWN;
                        user_input.down = 1;
                        movevalY += BOMBER_SPEED;
                        break;
                    case 'R':
                        status = WALK_TYPE.RIGHT;
                        user_input.right = 1;
                        movevalX += BOMBER_SPEED;
                        break;
                    case 'L':
                        status = WALK_TYPE.LEFT;
                        user_input.left = 1;
                        movevalX -= BOMBER_SPEED;
                        break;
                }

                BombermanGame.map.mapCollision(this);

                if(movevalX == 0 && movevalY == 0 && targetDir != ' ' && !PlaceBom ) {
                    System.out.println("hello234");
                    if(bomstack.size() < 1) {
                        if (isRunning) {
                            Media media = Sound.soundPlaceBomb;
                            BombermanGame.placeBomb = new MediaPlayer(media);
                            BombermanGame.placeBomb.play();
                        }
                        Bomb nbomb = makeBomb(B_radius);
                        PlaceBom = true;
                        bomstack.add(nbomb);
                        tmpPos = new Pair<>(xblock, yblock);
                    }
                    isStuck = true;

                }else{
                    isStuck = false;
                }

            }else{
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
            }

            if(life <= 0) {
                alive = false;
                stopWatch.start();
                BombermanGame.hasDied.start();
                Media media = Sound.soundDied;
                BombermanGame.soundDied = new MediaPlayer(media);
                BombermanGame.soundDied.play();
            }

            x += movevalX;
            y += movevalY;
        }

    }
    public void PathFinding(int startX,int startY) {
        for (int i = 0; i < ConstVar.HEIGHT; i++) {
            for (int j = 0; j < ConstVar.WIDTH; j++) {
                if (i == startY && j == startX) {
                    dis[i][j] = 0;
                } else {
                    dis[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        ArrayList<Pair<Integer,Integer>> node = new ArrayList<>();
        node.add(new Pair<>(startX,startY));
        while(!node.isEmpty()) {
            int currentx = node.get(0).getKey();
            int currenty = node.get(0).getValue();
            node.remove(0);
            if(currentx == xblock && currenty == yblock) {
                break;
            }
            if (currentx + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#'
            ) && dis[currenty][currentx + 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx + 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx+1,currenty));
            }
            if (currentx - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#'
            ) && dis[currenty][currentx - 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx - 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx-1,currenty));
            }
            if (currenty - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#'
            ) && dis[currenty - 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty - 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty-1));
            }
            if (currenty + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#'
            ) && dis[currenty + 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty + 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty+1));
            }
        }
    }

    public char AvailablePath(ArrayList<Character> move,char priority) {
        double randomDirection;

        while(!move.isEmpty()) {
            if(move.contains(priority)) {
                randomDirection = move.indexOf(priority);
            }else{
                randomDirection = Math.random()*move.size();
            }
            if(move.get((int) randomDirection) == 'L') {
                if(BombermanGame.map.getTILE_MAP()[yblock][xblock-1] == ' '
                        || BombermanGame.map.getTILE_MAP()[yblock][xblock-1] =='i'
                ||BombermanGame.map.getTILE_MAP()[yblock][xblock-1] =='x') {
                    return 'L';
                }else{
                    move.remove((int) randomDirection);
                    continue;
                }
            }
            if(move.get((int) randomDirection) == 'R') {
                if(BombermanGame.map.getTILE_MAP()[yblock][xblock+1] == ' '
                        || BombermanGame.map.getTILE_MAP()[yblock][xblock+1] =='i'
                        || BombermanGame.map.getTILE_MAP()[yblock][xblock+1] =='x') {
                    return 'R';
                }else{
                    move.remove((int) randomDirection);
                    continue;
                }
            }
            if(move.get((int) randomDirection) == 'U') {
                if(BombermanGame.map.getTILE_MAP()[yblock-1][xblock] == ' '
                        || BombermanGame.map.getTILE_MAP()[yblock-1][xblock] =='i'
                ||BombermanGame.map.getTILE_MAP()[yblock-1][xblock] =='x') {
                    return 'U';
                }else{
                    move.remove((int) randomDirection);
                    continue;
                }
            }
            if(move.get((int) randomDirection) == 'D') {
                if(BombermanGame.map.getTILE_MAP()[yblock+1][xblock] == ' '
                        || BombermanGame.map.getTILE_MAP()[yblock+1][xblock] =='i'
                || BombermanGame.map.getTILE_MAP()[yblock+1][xblock] =='i') {
                    return 'D';
                }else{
                    move.remove((int) randomDirection);
                }
            }
        }
        return ' ';
    }

    public Pair<Integer,Integer> GetNearest() {
        ArrayList<Pair<Integer,Integer>> node = new ArrayList<>();
        node.add(new Pair<>(this.getXblock(),this.getYblock()));
        while(!node.isEmpty()) {
            int currentx = node.get(0).getKey();
            int currenty = node.get(0).getValue();
            if(Math.abs(currentx - this.getXblock()) >= 10 || Math.abs(currenty - this.getYblock()) >=10) {
                return null;
            }
            node.remove(0);
            if (currentx + 1 < ConstVar.WIDTH && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b') {
                if(BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != ' ' &&  BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'X'
                        &&  BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'x') {
                    return new Pair<>(currentx+1,currenty);

                }else{
                    if(BombermanGame.entities.size() == 1 && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != ' '
                            &&(BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] == 'X'
                            ||  BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'x')){
                        return new Pair<>(currentx+1,currenty);
                    }
                    node.add(new Pair<>(currentx+1,currenty));

                }
            }else{
                if(currentx + 1 < ConstVar.WIDTH && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#') {
                    node.add(new Pair<>(currentx+1,currenty));
                }

            }
            if (currentx - 1 >= 0 && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'b') {
                if(BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != ' ' &&  BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'X'
                        &&  BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'x') {
                    return new Pair<>(currentx-1,currenty);

                }else{
                    if(BombermanGame.entities.size() == 1 && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != ' '
                            && (BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] == 'X'
                            ||  BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] == 'x')){
                        return new Pair<>(currentx-1,currenty);
                    }
                    node.add(new Pair<>(currentx-1,currenty));

                }
            }else{
                if(currentx - 1 >= 0 && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#') {
                    node.add(new Pair<>(currentx-1,currenty));
                }

            }
            if (currenty - 1 >= 0 && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty-1][currentx ] != 'b') {
                if(BombermanGame.map.getTILE_MAP()[currenty-1][currentx] != ' ' && BombermanGame.map.getTILE_MAP()[currenty-1][currentx] != 'X'
                        && BombermanGame.map.getTILE_MAP()[currenty-1][currentx] != 'x') {
                    return new Pair<>(currentx,currenty-1);
                }else{
                    if(BombermanGame.entities.size() == 1 && BombermanGame.map.getTILE_MAP()[currenty-1][currentx] != ' '
                            && (BombermanGame.map.getTILE_MAP()[currenty-1][currentx] == 'X'
                            || BombermanGame.map.getTILE_MAP()[currenty-1][currentx] == 'x')){
                        return new Pair<>(currentx,currenty-1);
                    }
                    node.add(new Pair<>(currentx,currenty-1));

                }
            }else{
                if(currenty - 1 >= 0 && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#') {
                    node.add(new Pair<>(currentx,currenty-1));
                }

            }
            if (currenty + 1 < ConstVar.HEIGHT && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty+1][currentx] != 'b' ) {
                if(BombermanGame.map.getTILE_MAP()[currenty+1][currentx] != ' ' && BombermanGame.map.getTILE_MAP()[currenty+1][currentx] != 'X'
                        && BombermanGame.map.getTILE_MAP()[currenty+1][currentx] != 'x') {
                    return new Pair<>(currentx,currenty+1);
                }else{
                    if(BombermanGame.entities.size() == 1 && BombermanGame.map.getTILE_MAP()[currenty+1][currentx] != ' '
                            && ( BombermanGame.map.getTILE_MAP()[currenty+1][currentx] == 'X'
                            || BombermanGame.map.getTILE_MAP()[currenty+1][currentx] == 'x')){
                        return new Pair<>(currentx,currenty+1);
                    }
                    node.add(new Pair<>(currentx,currenty+1));

                }
            }else{
                if(currenty + 1 < ConstVar.HEIGHT && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#') {
                    node.add(new Pair<>(currentx,currenty+1));
                }

            }
        }
        return null;
    }

    /**
     * DO HOA NHAN VAT.
     */
    public void Animation() {

        if(alive) {
            if(hit) {
                if(stopWatch.getElapsedTime() >=2000) {
                    hit = false;
                    stopWatch.stop();
                }
            }
            if(!hit || stopWatch.getElapsedTime() %200 >=100 ) {
                if (user_input.right == 1) {
                    img = Sprite.movingSprite(Sprite.player_right,
                            Sprite.player_right_1, Sprite.player_right_2, time, 9).getFxImage();
                    time += 0.5;
                } else if (user_input.left == 1) {
                    img = Sprite.movingSprite(Sprite.player_left,
                            Sprite.player_left_1, Sprite.player_left_2, time, 9).getFxImage();
                    time += 0.5;
                } else if (user_input.up == 1) {
                    img = Sprite.movingSprite(Sprite.player_up,
                            Sprite.player_up_1, Sprite.player_up_2, time, 9).getFxImage();
                    time += 0.5;
                } else if (user_input.down == 1) {
                    img = Sprite.movingSprite(Sprite.player_down,
                            Sprite.player_down_1, Sprite.player_down_2, time, 9).getFxImage();
                    time += 0.5;
                }else{
                    if(status == WALK_TYPE.RIGHT) {
                        img = Sprite.player_right.getFxImage();
                    }else if(status == WALK_TYPE.LEFT) {
                        img = Sprite.player_left.getFxImage();
                    } else if (status == WALK_TYPE.DOWN) {
                        img = Sprite.player_down.getFxImage();
                    }else{
                        img = Sprite.player_up.getFxImage();
                    }

                }
            }else{
                img = null;
            }
        }else{
            img = Sprite.movingSprite(Sprite.player_dead1,
                    Sprite.player_dead2,Sprite.player_dead3,time,120).getFxImage();
            if(time %120 <119) {
                time+=1;
            }else{
                img = null;
            }
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
                }else{
                    if(!nbomb.isPlaySound()) {
                        if (isRunning) {
                            Media media = Sound.soundExplode;
                            BombermanGame.bombExplode = new MediaPlayer(media);
                            BombermanGame.bombExplode.play();
                            BombermanGame.bombExplode.setVolume(0.4);
                        }
                        nbomb.setPlaySound(true);
                    }
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
        Bomb nBomb = new Bomb(super.getXblock(),
                super.getYblock(),Sprite.bomb.getFxImage());
        nBomb.setActive(true);
        nBomb.setRadius(b_radius);

        return nBomb;
    }


    public void ResetTMPBOOST() {

        if(PowerTime.getElapsedTime() > 10000) {
            BOMBER_SPEED = 2;
            bom_pass = false;
            flame_pass = false;
            wall_pass = false;
            boosted = false;
            PowerTime.stop();
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