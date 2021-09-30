package buttons;

import java.awt.Color;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.Shrine;
import tiles.TileBase;

public class ShrineUpgradeButton extends UpgradeButton {

    public ShrineUpgradeButton(TileBase targ2, Color in) {
        super(targ2, in);
        cost = 40;
    }

    @Override
    public InputLogic.LogicState press(Player player) {
        return upgrade(Shrine::new);
    }

}
