package window;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowInputControl implements WindowListener {
    private final Window t;

    WindowInputControl(Window t) {
        this.t = t;
    }

    @Override
    public void windowActivated(WindowEvent e) {


    }

    @Override
    public void windowClosed(WindowEvent e) {
        e.getWindow().dispose();
        t.stop();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
        t.stop();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {


    }

    @Override
    public void windowDeiconified(WindowEvent e) {


    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

        t.PostInit();
    }

}
 


