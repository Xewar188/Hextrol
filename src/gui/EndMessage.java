package gui;

import playground.TileMap;

import java.awt.*;

public class EndMessage {

    private final TileMap target;
    private final Font font;
    public EndMessage(TileMap m, Rectangle a) {
        target = m;
        font = new Font("TimesRoman", Font.PLAIN, (int) (a.getHeight()/16));
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(Color.gray.brighter().getRed(),
                Color.gray.brighter().getGreen(),
                Color.gray.brighter().getBlue(),
                100));
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        g.setColor(Color.gray.brighter());
        g.fillRect(g.getClipBounds().width / 4,
                g.getClipBounds().height / 4,
                g.getClipBounds().width / 2,
                g.getClipBounds().height / 2);
        g.setColor(Color.white);
        g.setFont(font);
        String message = "Player " + target.getWinner() + " wins.";
        g.drawString(message,
                g.getClipBounds().width / 2 - g.getFontMetrics(font).stringWidth(message)/2,
                g.getClipBounds().height / 2 - g.getClipBounds().height / 16 - g.getClipBounds().height / 8 );
        message = "Press ESC to exit";
        g.drawString(message,
                g.getClipBounds().width / 2 - g.getFontMetrics(font).stringWidth(message)/2,
                g.getClipBounds().height / 2 + g.getClipBounds().height / 16 );
        message = "or click to restart";
        g.drawString(message,
                g.getClipBounds().width / 2 - g.getFontMetrics(font).stringWidth(message)/2,
                g.getClipBounds().height / 2 + g.getClipBounds().height / 16 + g.getClipBounds().height / 8 );
    }

    public boolean press(Point p) {
        target.reset();
        return true;
    }

    public boolean didWin() {
        return target.getWinner() != 0;
    }
}
