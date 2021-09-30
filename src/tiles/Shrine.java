package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import backendLogic.Player;
import buttons.TransferButton;
import playground.TileMap;
import playground.TileMenu;

public class Shrine extends TileBase {

    public Shrine(int x, int y, int length, Color color, int i, int j, TileMap map) {
        super(x, y, length, color, i, j, map);
        maxValue = 50;


    }

    public Shrine(TileBase base) {
        super(base);
        maxValue = 50;
        value = Math.min(base.getValue(), maxValue);

    }

    public void openFriendlyMenu(Player player) {

        super.openFriendlyMenu(player);

    }

    public void drawMain(Graphics2D g) {
        super.drawMain(g);
        int[] xx = new int[6];
        int[] yy = new int[6];
        g.setColor(Color.black);
        for (int i = 0; i < 6; i++) {
            xx[i] = (int) (getCenter().x + (Math.cos(Math.PI / 3 * i) * side * 0.75));
            yy[i] = (int) (getCenter().y + (Math.sin(Math.PI / 3 * i) * side * 0.75));
        }
        g.setStroke(new BasicStroke(side / 10f));
        g.draw(new Polygon(xx, yy, 6));
    }

    public void nextTurn() {
        applyToSurroundings(this::bless);
    }

    public void bless(TileBase base) {

        base.changeValue(10 * value / maxValue);
    }
}
