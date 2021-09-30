package backendLogic;

import java.awt.Color;

import playground.TileMap;

public class Player {

    private static int count = 0;
    private  int tilesNumber = 0;
    private  final int id;
    private final Color color;
    private int maxMoves = 5;
    private final boolean[][] foundTiles = new boolean[TileMap.columns][TileMap.rows];
    private int lastXoff = 0, lastYoff = 0, lastSideLength = 70;

    public Player(Color a) {
        id = count;
        count++;
        color = a;
        for (int i = 0; i < TileMap.rows; i++)
            for (int j = 0; j < TileMap.columns; j++)
                foundTiles[j][i] = false;
    }

    public void find(int i, int j) {
        foundTiles[i][j] = true;
    }

    public void setParameters(int lastX, int lastY, int lastSide) {
        lastXoff = lastX;
        lastYoff = lastY;
        lastSideLength = lastSide;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public void increaseMaxMoves(int i) {
        maxMoves += i;
    }

    public void decreaseMaxMoves(int i) {
        maxMoves -= i;
    }

    public void loseSight(int i, int j) {
        foundTiles[i][j] = false;
    }

    public boolean found(int i, int j) {
        return foundTiles[i][j];
    }

    public int getTilesNumber() {
        return tilesNumber;
    }

    public void changeTilesNumber(int diff) {
        tilesNumber += diff;
    }

    public Color getColor() {
        return color;
    }

    public int getLastSideLength() {
        return lastSideLength;
    }

    public int getLastY() {
        return lastYoff;
    }

    public int getLastX() {
        return lastXoff;
    }
}
