package backendLogic;

import buttons.ButtonBase;
import playground.TileMap;
import tiles.TileBase;

import java.awt.*;

public class InputLogic {

    public enum LogicState {NEUTRAL, MENU_OPEN, PROVIDE_TILE, MOVE_USED};
    private TileBase curActiveTile = null;
    private LogicState activeState = LogicState.NEUTRAL;
    private  ButtonBase curActiveButton = null;
    private final TileMap map;

    public InputLogic(TileMap m) {
        map = m;
    }

    private void validate() {
        if (activeState == LogicState.MOVE_USED) {
            map.getRound().changeMoves(-1);
            activeState = LogicState.NEUTRAL;
            map.checkForWinner();
        }
        if (curActiveTile == null)
            activeState = LogicState.NEUTRAL;
        if (activeState == LogicState.NEUTRAL) {
            if (curActiveButton != null)
                curActiveButton.turnOff();
            if (curActiveTile != null)
                curActiveTile.closeMenu();
            curActiveTile = null;
            curActiveButton = null;
        }
        if (activeState == LogicState.PROVIDE_TILE && curActiveButton == null) {
            activeState = LogicState.NEUTRAL;
        }

    }


    public void resetActions() {
        activeState = LogicState.NEUTRAL;
        validate();
    }

    public TileBase curActiveTile() {
        return curActiveTile;
    }

    public boolean tryPressingButton(Player player, Point p) {
        if (curActiveTile() != null && curActiveTile().isMenuOpen()) {
           ButtonBase temp = curActiveTile().tryPressingButton(p);
           if (temp == null)
               return false;
           buttonPress(temp, player);
            return true;
        }
        return false;
    }

    public void buttonPress(ButtonBase toPress, Player player) {
        activeState = toPress.press(player);

        if (activeState == LogicState.PROVIDE_TILE) {
            curActiveButton = toPress;
            curActiveTile.closeMenu();
        }
        validate();
    }

    public void pressTile(int i, int j, Player player) {

        switch (activeState) {
            case NEUTRAL:
                map.board(i, j).openMenu(player);
                if (map.board(i, j).isMenuOpen()) {
                    curActiveTile = map.board(i, j);
                    activeState = LogicState.MENU_OPEN;
                }

                break;
            case MENU_OPEN:
                if (curActiveTile != map.board(i, j)) {
                    activeState = LogicState.NEUTRAL;
                }
                break;
            case PROVIDE_TILE:
                activeState = curActiveButton.addTile(map.board(i, j));
                break;
        }
        validate();
    }

    public boolean isMenuOpen() {
        return curActiveTile != null && curActiveTile.isMenuOpen();
    }


}
