package buttons;

import java.awt.Color;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.Storage;
import tiles.TileBase;

public class StorageUpgradeButton extends UpgradeButton {

    public StorageUpgradeButton(TileBase targ2, Color in) {
        super(targ2, in);
        cost = 50;
    }

    @Override
    public InputLogic.LogicState press(Player player) {
        return upgrade(Storage::new);

    }

}
