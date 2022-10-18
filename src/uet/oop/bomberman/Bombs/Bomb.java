package uet.oop.bomberman.Bombs;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.StillObjects.StillObject;
import uet.oop.bomberman.Utils.Collision;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {

    private int radius;
    private double time;
    private boolean active;
    private final List<Flame> explosion = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        w = (int) img.getWidth();
        h = (int) img.getHeight();
        active = false;
        time = 0;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void placeBomb(char[][] TILE_MAP) {
        TILE_MAP[yblock][xblock] = 'b';
        img = Sprite.movingSprite(Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2, time, 30).getFxImage();
        if (time < 70) {
            time += 0.5;
        } else {
            time = 0;
            active = false;
            TILE_MAP[yblock][xblock] = ' ';
            if(StillObject.getBrickAt(xblock, yblock) != null) {
                StillObject.getBrickAt(xblock, yblock).setTerminate(true);
            }
            explosion.add(new Flame(xblock, yblock, Flame.TYPE.CENTER));
            //add bottom
            addFlame(1,0);
            //add top
            addFlame(-1,0);
            //add left
            addFlame(0,-1);
            //add right
            addFlame(0,1);
        }
    }
    public void addFlame(int vertical,int horizontal) {
        int spreadX = horizontal;
        int spreadY = vertical;
        Flame.TYPE type;
        char[][] TILE_MAP = BombermanGame.map.getTILE_MAP();
        int i = (vertical == -1 || horizontal == -1) ? -1:1;
        while (Math.abs(spreadX) <= radius && Math.abs(spreadY) <= radius &&
                TILE_MAP[yblock + spreadY][xblock + spreadX] != '#' ) {
            if(TILE_MAP[yblock + spreadY][xblock+spreadX] == '*' || TILE_MAP[yblock + spreadY][xblock+spreadX] == 'I') {
                StillObject.getBrickAt(xblock+spreadX,yblock + spreadY).setTerminate(true);
                break;
            }
            if(vertical == 0) {
                if (Math.abs(spreadX) == radius) {
                    type = (horizontal == 1) ? Flame.TYPE.RIGHT: Flame.TYPE.LEFT;
                    explosion.add(new Flame(xblock + spreadX, yblock + spreadY, type));
                } else {
                    explosion.add(new Flame(xblock+spreadX, yblock + spreadY, Flame.TYPE.HORIONTAL));
                }
                spreadX+= i;
            }else {
                if (Math.abs(spreadY) == radius) {
                    type = (vertical == 1) ? Flame.TYPE.DOWN: Flame.TYPE.TOP;
                    explosion.add(new Flame(xblock + spreadX, yblock + spreadY, type));
                } else {
                    explosion.add(new Flame(xblock+spreadX, yblock + spreadY, Flame.TYPE.VERTICAL));
                }
                spreadY +=i;
            }


        }
    }
    /**
     * Lửa khi bom phát nổ.
     */
    public boolean Explode(GraphicsContext gc) {
        BombermanGame.map.setTILE_MAP(yblock, xblock, ' ');
        for (Flame flame : explosion) {
            flame.update();
            flame.render(gc);
            if(BombermanGame.map.getTILE_MAP()[flame.getYblock()][flame.getXblock()] == 'i'){
                StillObject a = StillObject.getItemAt(flame.getXblock(), flame.getYblock());
                if(a != null) {
                    BombermanGame.stillObjects.remove(a);
                }
            }
            for(Entity e: BombermanGame.entities) {
                boolean check = Collision.checkCollision(flame,e);
                if (check && !e.isFlame_pass()) {
                    System.out.println("hit");
                    e.setAlive(false);
                    e.getStopWatch().start();
                }
            }
        }
        time++;
        return !(time > 24);
    }

    @Override
    public void update() {
        placeBomb(BombermanGame.map.getTILE_MAP());
    }
}