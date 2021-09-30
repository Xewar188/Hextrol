package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import backendLogic.Player;
import buttons.TransferButton;
import playground.TileMenu;

public class Attacker extends TileBase {

    public Attacker(TileBase t) {
        super(t);
        maxValue = 149;
        value = Math.min(t.getValue(), maxValue);
        protection = 0.75f;
        captureMulti = 1f;
        attackMulti = 1.5f;
    }

    @Override
    public void openFriendlyMenu(Player player) {
        super.openFriendlyMenu(player);

    }

    @Override
    public void nextTurn() {


    }

    public void drawMain(Graphics2D g) {
        super.drawMain(g);
        g.setStroke(new BasicStroke(side / 10f));
        g.setColor(Color.black);
        for (int i = 0; i < 2; i++)
            g.draw(new Line2D.Float((float) (getCenter().x + (Math.sin(Math.PI * ((float) i * 2 / 3 + 1f / 6f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * ((float) i * 2 / 3 + 1f / 6f)) * (side - side / 20))),
                    (float) (getCenter().x + (Math.sin(Math.PI * ((float) i * 2 / 3 + 7f / 6f)) * (side - side / 20))),
                    (float) (getCenter().y + (Math.cos(Math.PI * ((float) i * 2 / 3 + 7f / 6f)) * (side - side / 20)))));

        super.drawValue(g);
    }

}
