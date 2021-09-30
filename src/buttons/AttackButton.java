package buttons;

import java.awt.Color;
import java.util.ArrayList;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.TileBase;

public class AttackButton extends ButtonBase {
    private final ArrayList<TileBase> tiles = new ArrayList<>();
    private Player initiator;

    public AttackButton(TileBase targ2, Color in) {
        super(targ2, targ2.getSide() / 2, in);
    }


    public InputLogic.LogicState press(Player player) {
        targ.activate();
        initiator = player;
        return InputLogic.LogicState.PROVIDE_TILE;
    }


    public InputLogic.LogicState addTile(TileBase tile) {
        if (tile == targ) {
            targ.deactivate();
            if (tiles.size() >= 1) {
                int compoundValue = 0;
                for (TileBase tileBase : tiles) {
                    tileBase.deactivate();
                    compoundValue += tileBase.getValue() * tileBase.getAttackMulti();
                }
                if (compoundValue * (tiles.size() + 4) / 5.0f > targ.getValue() * targ.getProtection()) {
                    targ.changeOwner(initiator);
                    for (int i = 0; i < tiles.size(); i++)
                        tiles.get(i).changeValue((int) Math.ceil((-(float) targ.getValue() / (float) tiles.size())));
                    targ.setValue((int) (targ.getValue() * targ.getCaptureMulti()));

                    return InputLogic.LogicState.MOVE_USED;
                }
            }
            return InputLogic.LogicState.NEUTRAL;
        }
        else if (targ.distance(tile) < targ.getSide() * Math.sqrt(3) + 4) {
            if (tile.getOwner() == initiator) {

                if (tiles.remove(tile)) {
                    tile.deactivate();
                } else {
                    tiles.add(tile);
                    tile.activate();
                }

            }

            return InputLogic.LogicState.PROVIDE_TILE;
        }
        else {
            targ.deactivate();
            for (TileBase tileBase : tiles) {
                tileBase.deactivate();
            }
            return InputLogic.LogicState.NEUTRAL;
        }

    }


    public void turnOff() {
        targ.deactivate();
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).deactivate();
        }
    }
}
