package window;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import playground.Map;

public class InputControl {

    static void addControls(Window a, Map m) {
        MouseInput mouse = new MouseInput(a, m);
        m.addMouseListener(mouse);
        m.addMouseMotionListener(mouse);
        m.addMouseWheelListener(mouse);
        a.addKeyListener(new KeyInput(a));
    }
}

class MouseInput extends MouseAdapter {
    private final Window targ;
    private final Map map;
    private Point pPos;
    private Point pressedPos;

    MouseInput(Window a, Map m) {
        map = m;
        targ = a;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        pPos = e.getPoint();
        pressedPos = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getPoint().distance(pressedPos) < 3) {
            map.press(e, targ);
            map.repaint();
        }

    }

    public void mouseDragged(MouseEvent e) {
        map.translate(e.getPoint().x - pPos.x, e.getPoint().y - pPos.y);
        pPos = e.getPoint();
        map.repaint();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        float scale = 11.0f / 12.0f;
        if (e.getWheelRotation() == -1) {
            scale = 1 / scale;
        }
        map.resize(e.getPoint().x, e.getPoint().y, scale);
        map.repaint();
    }
}

class KeyInput implements KeyListener {
    private final Window targ;

    KeyInput(Window a) {
        targ = a;
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            targ.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}

