package playground;

import java.awt.Graphics2D;
import java.util.Vector;

import buttons.ButtonBase;
import tiles.TileBase;

public class TileMenu {

    private final Vector<ButtonBase> buttons = new Vector<>();
    private final int distance;
    private final int size;
    private final int maxButtons;
    private final TileBase targ;

    public TileMenu(TileBase main) {
        distance = main.getSide() * 2;
        size = main.getSide() / 2;
        targ = main;
        maxButtons = 12;
    }

    public void addButton(ButtonBase b) {
        if (buttons.size() < maxButtons) {
            buttons.add(b);
            setPos();
        }
    }

    public void draw(Graphics2D g) {
        for (ButtonBase button : buttons) {
            button.draw(g);
        }
    }

    public void setPos() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPos((int) (targ.getCenter().x + Math.sin(Math.PI * 2 / (buttons.size()) * i) * distance),
                                    (int) (targ.getCenter().y - Math.cos(Math.PI * 2 / (buttons.size()) * i) * distance));
        }
    }

    public void setSize() {
        for (ButtonBase button : buttons) {
            button.setSize(size);
        }
    }

    public int getQuantity() {
        return buttons.size();
    }

    public ButtonBase getButton(int i) {
        return buttons.get(i);
    }

}
