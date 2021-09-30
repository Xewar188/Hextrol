package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import playground.TileMap;
import tiles.TileBase;

public class Minimap {

    private int tileWidth = 8, tileHeight = 8;
    private final TileMap map;

    private final Rectangle mapPosition;
    private final Rectangle windowSize;

    Minimap(TileMap m, Rectangle a) {
        tileWidth = 400 / TileMap.columns;
        tileHeight = 400 / TileMap.rows;
        map = m;
        mapPosition = new Rectangle(a.width - tileWidth * TileMap.columns - 10,
                            a.height - tileHeight * TileMap.rows - 10,
                        tileWidth * TileMap.columns + 5,
                        tileHeight * TileMap.rows + 5);
        windowSize = a;
    }

    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);
        g.draw(mapPosition);
        g.setStroke(new BasicStroke(0));
        for (int i = 0; i < TileMap.columns; i++)
            for (int j = 0; j < TileMap.rows; j++) {
                if (map.getRound().getCurPlayer().found(i, j)) {
                    g.setColor(map.board(i, j).getColor());
                } else {
                    g.setColor(TileBase.blankColor);
                }
                g.fillRect(mapPosition.x + i * tileWidth + 3,
                            mapPosition.y + j * tileHeight + 3,
                        tileWidth,
                        tileHeight);
            }
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.black);

        //drawing screen representation on map
        g.drawRect((int) (mapPosition.x + 3 + Math.max(-map.getXoff() / 1.5 / map.getSide() * tileWidth, 0)),
                    (int) (mapPosition.y + 3 + Math.max(-map.getYoff() / Math.sqrt(3) / map.getSide() * tileHeight, 0)),
                    (int) (windowSize.width / 1.5 / map.getSide() * tileWidth),
                    (int) (windowSize.height / Math.sqrt(3) / map.getSide() * tileHeight));
    }

    public boolean press(Point p) {
        if (mapPosition.contains(p)) {
            //conversion between map and global coordinates
            map.setXoff((int) (-((p.x - mapPosition.x) / tileWidth * map.getSide()) * 1.5 + map.getSide() * 1.5));
            map.setYoff((int) (-((p.y - mapPosition.y) / tileHeight * map.getSide()) * Math.sqrt(3) + map.getSide()));
            return true;
        } else {
            return false;
        }

    }
}
