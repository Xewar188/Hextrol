package gui;

import java.awt.*;

import playground.TileMap;

public class MoveCounter {
    private final int height = 50;
    private final int width = 50;
    private final Rectangle r;
    private final TileMap map;
    private final Font font;

    MoveCounter(TileMap m, Rectangle a) {
        map = m;
        r = new Rectangle(a.width - width, 0, width, height);
        font = new Font("TimesRoman", Font.PLAIN, height * 2 / 5);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.cyan);
        g.fill(r);
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.black);
        g.draw(r);
        g.setColor(Color.cyan.darker());
        g.setFont(font);
        g.drawString(Integer.toString(map.getRound().getMoves()),
                (int) (r.x + width / 2 - height / 1.5 * 0.1 * Integer.toString(map.getRound().getMoves()).length()),
                (int) (height / 1.5));
    }
}
