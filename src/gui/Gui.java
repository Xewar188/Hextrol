package gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import playground.TileMap;

public class Gui {

    private final SkipButton button;
    private final Minimap mini;
    private final MoveCounter moves;
    private final EndMessage end;

    public Gui(TileMap m, Rectangle a) {
        button = new SkipButton(m, a);
        mini = new Minimap(m, a);
        moves = new MoveCounter(m, a);
        end = new EndMessage(m, a);
    }

    public void draw(Graphics2D g) {
        button.draw(g);
        mini.draw(g);
        moves.draw(g);
        if (end.didWin()) {
            end.draw(g);
        }
    }

    public boolean press(Point p) {
        if (end.didWin())
            return end.press(p);
        return button.press(p) || mini.press(p);
    }
}
