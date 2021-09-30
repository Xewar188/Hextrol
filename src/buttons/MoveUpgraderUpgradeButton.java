package buttons;

import java.awt.Color;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.MoveUpgrader;
import tiles.TileBase;

public class MoveUpgraderUpgradeButton extends UpgradeButton {

    public MoveUpgraderUpgradeButton(TileBase targ2, Color in) {
        super(targ2, in);
        cost = 30;
    }

    @Override
    public InputLogic.LogicState press(Player player) {

        return upgrade(MoveUpgrader::new);
    }

}
