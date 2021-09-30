package buttons;

import java.awt.Color;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.TileBase;
import tiles.WatchTower;

public class WatchTowerUpgradeButton extends UpgradeButton {

    public WatchTowerUpgradeButton(TileBase targ2, Color in) {
        super(targ2, in);
        cost = 60;
    }

    @Override
    public InputLogic.LogicState press(Player player) {
        return upgrade(WatchTower::new);
    }

}
