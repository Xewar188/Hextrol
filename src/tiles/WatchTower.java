package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import backendLogic.Player;
import buttons.TransferButton;
import playground.TileMenu;

public class WatchTower extends TileBase {

    public WatchTower(TileBase t) {
        super(t);
        value = Math.min(t.getValue(), maxValue);
        protection = 2.5f;
        captureMulti = 0;
        attackMulti = 0.8f;
    }

    @Override
    public void openFriendlyMenu(Player player) {
        super.openFriendlyMenu(player);
    }

    public void drawMain(Graphics2D g) {
        super.drawMain(g);
        g.setStroke(new BasicStroke(side / 10f));
        g.setColor(Color.black);
        for (int i = 0; i < 3; i++) {
            g.draw(new Line2D.Float((float) (getCenter().x + (Math.sin(Math.PI * 2 / 3 * ((float) i + 1f / 4f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * 2 / 3 * ((float) i + 1f / 4f)) * (side - side / 20))),
                    (float) (getCenter().x + (Math.sin(Math.PI * 2 / 3 * ((float) i + 5f / 4f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * 2 / 3 * ((float) i + 5f / 4f)) * (side - side / 20)))));
            g.draw(new Line2D.Float((float) (getCenter().x + (Math.sin(Math.PI * 2 / 3 * ((float) i + 7f / 4f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * 2 / 3 * ((float) i + 7f / 4f)) * (side - side / 20))),
                    (float) (getCenter().x + (Math.sin(Math.PI * 2 / 3 * ((float) i + 11f / 4f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * 2 / 3 * ((float) i + 11f / 4f)) * (side - side / 20)))));
        }


        super.drawValue(g);
    }

    @Override
    public void nextTurn() {
    }

}
