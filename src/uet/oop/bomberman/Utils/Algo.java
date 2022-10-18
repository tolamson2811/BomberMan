package uet.oop.bomberman.Utils;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;

public class Algo {

    //PATH FINDING WITH BFS
    public static void BFS(int startX,int startY,int [][] dis,int endX,int endY ) {
        ArrayList<Pair<Integer,Integer>> node = new ArrayList<>();
        node.add(new Pair<>(startX,startY));
        while(!node.isEmpty()) {
            int currentx = node.get(0).getKey();
            int currenty = node.get(0).getValue();
            node.remove(0);
            if(currentx == endX && currenty == endY) {
                break;
            }
            if (currentx + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b') && dis[currenty][currentx + 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx + 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx+1,currenty));
            }
            if (currentx - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx - 1] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b') && dis[currenty][currentx - 1] == Integer.MAX_VALUE) {
                dis[currenty][currentx - 1] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx-1,currenty));
            }
            if (currenty - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty - 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b') && dis[currenty - 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty - 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty-1));
            }
            if (currenty + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '#'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != 'I'
                    && BombermanGame.map.getTILE_MAP()[currenty + 1][currentx] != '*'
                    && BombermanGame.map.getTILE_MAP()[currenty][currentx + 1] != 'b') && dis[currenty + 1][currentx] == Integer.MAX_VALUE) {
                dis[currenty + 1][currentx] = dis[currenty][currentx] + 1;
                node.add(new Pair<>(currentx,currenty+1));
            }
        }
    }

    //PATH FINDING WITH DFS
    public static void DFS(int startX,int startY,int [][] dis,int endX,int endY ) {
        if(startX != endX || startY != endY) {

            if (startX + 1 < ConstVar.WIDTH && (BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != 'I' && BombermanGame.map.getTILE_MAP()[startY][startX + 1] != '*') && dis[startY][startX + 1] == Integer.MAX_VALUE) {
                dis[startY][startX + 1] = dis[startY][startX] + 1;
                DFS(startX + 1, startY,dis,endX,endY);
            }
            if (startX - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != 'I' && BombermanGame.map.getTILE_MAP()[startY][startX - 1] != '*') && dis[startY][startX - 1] == Integer.MAX_VALUE) {
                dis[startY][startX - 1] = dis[startY][startX] + 1;
                DFS(startX - 1, startY,dis,endX,endY);
            }
            if (startY - 1 >= 0 && (BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != 'I' && BombermanGame.map.getTILE_MAP()[startY - 1][startX] != '*') && dis[startY - 1][startX] == Integer.MAX_VALUE) {
                dis[startY - 1][startX] = dis[startY][startX] + 1;
                DFS(startX, startY - 1,dis,endX,endY);
            }
            if (startY + 1 < ConstVar.HEIGHT && (BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '#'
                    && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != 'I' && BombermanGame.map.getTILE_MAP()[startY + 1][startX] != '*') && dis[startY + 1][startX] == Integer.MAX_VALUE) {
                dis[startY + 1][startX] = dis[startY][startX] + 1;
                DFS(startX, startY + 1,dis,endX,endY);
            }
        }

    }
}