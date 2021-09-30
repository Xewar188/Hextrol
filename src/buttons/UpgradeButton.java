package buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.TileBase;

abstract public class UpgradeButton extends ButtonBase {

    private Font font;
    protected int cost = 0, fontSize;

    public UpgradeButton(TileBase targ2, Color in) {
        super(targ2, targ2.getSide() / 2, in);
        fontSize = (int) (side / 0.75);
        font = new Font("TimesRoman", Font.PLAIN, fontSize);

    }

    @Override
    abstract public InputLogic.LogicState press(Player player);

    @Override
    public InputLogic.LogicState addTile(TileBase tile) {

        return InputLogic.LogicState.NEUTRAL;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        g.setFont(font);
        g.setColor(color.darker());
        g.drawString(Integer.toString(cost),
                (int) (getCenter().x - fontSize * 0.28 * Integer.toString(cost).length()),
                getCenter().y + fontSize / 3);
    }

    public InputLogic.LogicState upgrade(J i) {
        if (targ.getValue() >= cost) {
            targ.changeValue(-cost);
            targ.getMap().replaceTile(i.myMethod(targ), targ.i, targ.j);
            return InputLogic.LogicState.MOVE_USED;
        }
        else
            return InputLogic.LogicState.MENU_OPEN;
    }

    public void setSize(int l) {
        side = l;
        fontSize = (int) (side / 0.75);
        font = new Font("TimesRoman", Font.PLAIN, fontSize);
    }

    @Override
    public void turnOff() {}

    public
    interface J {
        TileBase myMethod(TileBase tile);
    }
}
