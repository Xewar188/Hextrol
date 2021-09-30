package window;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

import playground.Map;

public class Window extends JFrame {


    @Serial
    private static final long serialVersionUID = 1L;
    private final Map m;
    private final Timer frameLoader;
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            m.repaint();
        }
    };

    public Window() {

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setUndecorated(true);
        this.setResizable(false);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        m = new Map();
        this.add(m);
        InputControl.addControls(this, m);

        float frames = 75;
        frameLoader = new Timer((int) (1000f / frames * 2f), taskPerformer);
        frameLoader.start();

        this.addWindowListener(new WindowInputControl(this));
        this.setVisible(true);

    }

    void PostInit() {
        m.setSize(this.getContentPane().getSize());
    }

    void stop() {
        frameLoader.stop();
    }
}
