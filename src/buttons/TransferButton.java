package buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.TileBase;

public class TransferButton extends ButtonBase {

    private Player initiator;
    private final float multiplicator;
    private Font font;
    private int fontSize;

    public TransferButton(TileBase targ2, Color in, float f) {
        super(targ2, targ2.getSide() / 2, in);
        multiplicator = f;
        fontSize = side;
        font = new Font("TimesRoman", Font.PLAIN, fontSize);
    }


    public InputLogic.LogicState press(Player player) {

        if (targ.getValue() == 0) {
            return InputLogic.LogicState.MENU_OPEN;
        }
        initiator = player;
        targ.activate();
        return InputLogic.LogicState.PROVIDE_TILE;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        g.setFont(font);
        g.setColor(color.brighter());
        g.drawString(Float.toString(multiplicator),
                    (int) (getCenter().x - fontSize * 0.23 * Float.toString(multiplicator).length()),
                    getCenter().y + fontSize / 3);
    }

    public InputLogic.LogicState addTile(TileBase tile) {

        if (tile.getOwner() == initiator) {
            if (tile == targ) {
                return InputLogic.LogicState.NEUTRAL;
            } else {
                if (tile.getValue() == tile.getMaxValue())
                    return InputLogic.LogicState.NEUTRAL;
                if (!tile.getMap().areTilesConnected(tile, targ))
                    return InputLogic.LogicState.PROVIDE_TILE;
                int pval = tile.getValue();
                tile.setValue((int) (targ.getValue() * multiplicator + tile.getValue()));
                targ.setValue(targ.getValue() - tile.getValue() + pval);
                return InputLogic.LogicState.MOVE_USED;
            }

        }
        return InputLogic.LogicState.PROVIDE_TILE;
    }

    public void setSize(int l) {
        side = l;
        fontSize = l;
        font = new Font("TimesRoman", Font.PLAIN, fontSize);
    }

    public void turnOff() {
        targ.deactivate();

    }

}
