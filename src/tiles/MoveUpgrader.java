package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import backendLogic.Player;
import buttons.TransferButton;
import buttons.ShrineUpgradeButton;
import playground.TileMenu;

public class MoveUpgrader extends TileBase {

    public MoveUpgrader(TileBase base) {
        super(base);
        maxValue = 40;
        value = Math.min(base.getValue(), maxValue);
    }

    @Override
    public void openFriendlyMenu(Player player) {
        super.openFriendlyMenu(player);
        menu.addButton(new ShrineUpgradeButton(this, Color.cyan.darker()));
    }

    @Override
    public void nextTurn() {
        changeValue(1);

    }

    public void drawMain(Graphics2D g) {
        super.drawMain(g);
        g.setStroke(new BasicStroke(side / 10f));
        g.setColor(Color.black);
        for (int i = 0; i < 3; i++) {
            g.draw(new Line2D.Float((float) (getCenter().x + (Math.sin(Math.PI / 3 * 2 * i) * side * 0.35)),
                    (float) (getCenter().y + (Math.cos(Math.PI / 3 * 2 * i) * side * 0.35)),
                    (float) (getCenter().x + (Math.sin(Math.PI / 3 * 2 * i) * side * 0.75)),
                    (float) (getCenter().y + (Math.cos(Math.PI / 3 * 2 * i) * side * 0.75))));
        }


    }

    public void changeOwner(Player p) {
        if (getOwner() != null)
            getOwner().decreaseMaxMoves(1);
        p.increaseMaxMoves(1);
        super.changeOwner(p);
    }

    @Override
    public void prepareToDelete() {
        getOwner().decreaseMaxMoves(1);
    }
}
