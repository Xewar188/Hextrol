package buttons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.TileBase;

public abstract class ButtonBase {
    protected TileBase targ;
    protected Polygon body;
    protected Color color;
    protected int side;

    public ButtonBase(TileBase targ2, int side, Color in) {
        this.targ = targ2;
        int[] xx = new int[6];
        int[] yy = new int[6];
        this.side = side;
        for (int i = 0; i < 6; i++) {
            xx[i] = (int) ((Math.sin(Math.PI / 3 * i) * side));
            yy[i] = (int) ((Math.cos(Math.PI / 3 * i) * side));
        }
        body = new Polygon(xx, yy, 6);
        color = in;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(body);
    }

    public Point getCenter() {
        return new Point(body.xpoints[0], body.ypoints[0] - side);
    }

    public boolean contains(Point p) {
        return body.contains(p);
    }

    public void setPos(int x, int y) {
        int[] xx = new int[6];
        int[] yy = new int[6];
        for (int i = 0; i < 6; i++) {
            xx[i] = (int) (x + (Math.sin(Math.PI / 3 * i) * side));
            yy[i] = (int) (y + (Math.cos(Math.PI / 3 * i) * side));
        }
        body = new Polygon(xx, yy, 6);
    }

    public void setSize(int l) {
        side = l;
    }

    abstract public InputLogic.LogicState press(Player player);

    abstract public InputLogic.LogicState addTile(TileBase tile);

    abstract public void turnOff();
}
