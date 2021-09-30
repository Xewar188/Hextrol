package tiles;

import java.awt.Color;

import backendLogic.Player;
import buttons.AttackerUpgradeButton;
import buttons.TransferButton;
import buttons.MoveUpgraderUpgradeButton;
import buttons.ShrineUpgradeButton;
import buttons.StorageUpgradeButton;
import buttons.WatchTowerUpgradeButton;
import playground.TileMap;
import playground.TileMenu;

public class Tile extends TileBase {

    public Tile(int x, int y, int length, Color color, int i, int j, TileMap map) {
        super(x, y, length, color, i, j, map);
    }

    public void openFriendlyMenu(Player player) {

        super.openFriendlyMenu(player);
        menu.addButton(new ShrineUpgradeButton(this, Color.cyan.darker()));
        menu.addButton(new MoveUpgraderUpgradeButton(this, Color.cyan.darker()));
        menu.addButton(new StorageUpgradeButton(this, Color.cyan.darker()));
        menu.addButton(new WatchTowerUpgradeButton(this, Color.cyan.darker()));
        menu.addButton(new AttackerUpgradeButton(this, Color.cyan.darker()));

    }


    public void nextTurn() {
        if (getOwner() != null) {
            changeValue(5);
        }

    }

}
