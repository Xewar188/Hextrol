package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import backendLogic.Player;
import buttons.TransferButton;
import playground.TileMenu;

public class Storage extends TileBase {

    public Storage(TileBase t) {
        super(t);
        maxValue = 499;
        value = Math.min(t.getValue(), maxValue);
        protection = 0.2f;
        captureMulti = 0.8f;
        attackMulti = 0.1f;
    }

    @Override
    public void openFriendlyMenu(Player player) {
        super.openFriendlyMenu(player);
    }

    @Override
    public void nextTurn() {
        if (getOwner() != null) {
            changeValue(1);
        }

    }

    public void drawMain(Graphics2D g) {
        super.drawMain(g);
        g.setStroke(new BasicStroke(side / 10f));
        g.setColor(Color.black);
        for (int i = 0; i < 2; i++)
            g.draw(new Line2D.Float((float) (getCenter().x + (Math.sin(Math.PI * ((float) i + 1f / 6f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * ((float) i + 1f / 6f)) * (side - side / 20))),
                    (float) (getCenter().x + (Math.sin(Math.PI * ((float) i + 5f / 6f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * ((float) i + 5f / 6f)) * (side - side / 20)))));

        super.drawValue(g);
    }
}
