package buttons;

import java.awt.Color;

import backendLogic.InputLogic;
import backendLogic.Player;
import tiles.Attacker;
import tiles.TileBase;

public class AttackerUpgradeButton extends UpgradeButton {

    public AttackerUpgradeButton(TileBase targ2, Color in) {
        super(targ2, in);
        cost = 65;
    }

    @Override
    public InputLogic.LogicState press(Player player) {
        return upgrade(Attacker::new);
    }

}
