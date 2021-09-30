package playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.Serial;

import javax.swing.JPanel;

import gui.Gui;
import window.Window;

public class Map extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private TileMap map;
    private Gui gui = null;

    public Map() {}


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.fillRect(0, 0, g2.getClipBounds().width, g2.getClipBounds().height);

        if (gui != null) {
            map.draw(g2);
            gui.draw(g2);
        }

    }

    public void translate(int x, int y) {
        map.translate(x, y);
    }

    public void resize(int x, int y, float scale) {
        map.resize(x, y, scale);
    }

    public void press(MouseEvent e, Window m) {

        if (gui.press(e.getPoint()))
            map.getRound().update();
        else
            map.activateTile(e, m);
    }

    public void setSize(Dimension size) {
        map = new TileMap(new Rectangle(size.width, size.height));
        gui = new Gui(map, new Rectangle(size.width, size.height));
    }
}
