package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import playground.TileMap;

public class SkipButton {


    private final Rectangle r;
    private final TileMap map;

    SkipButton(TileMap m, Rectangle a) {
        map = m;
        int width = 100;
        int height = 50;
        r = new Rectangle(0, a.height - height, width, height);

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.cyan);
        g.fill(r);
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);
        g.draw(r);
    }

    public boolean press(Point p) {
        if (r.contains(p)) {
            map.getRound().setMoves(0);
            map.getInputLogic().resetActions();
            return true;
        }
        else {
            return false;
        }

    }
}
