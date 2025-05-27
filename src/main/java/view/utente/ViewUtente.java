package view.utente;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.utente.ControllerUtente;
import view.api.Scene;
import view.api.View;

public class ViewUtente implements View{

    private static final double FRAME_SIZE_FACTOR = 0.6;

    private final JFrame frame;
    private final Dimension screenSize;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

     private ControllerUtente controller;

    /**
     * Constructor for the MainView.
     * It initializes the frame and size of the window.
     * It also sets the CardLayout for switching between scenes.
     * @param visible if the window should be visible
     */
    public ViewUtente(final Runnable onClose) {
        this.frame = new JFrame("Utente");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var initWidth = (int) (screenSize.width * FRAME_SIZE_FACTOR);
        final var initHeight = (int) (screenSize.height * FRAME_SIZE_FACTOR);
        this.frame.setMinimumSize(new Dimension(initWidth, initHeight));
        this.frame.setSize(this.frame.getMinimumSize());

        // CardLayout for switching between scenes
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.frame.setContentPane(this.mainPanel);

        this.frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void changeScene(final Scene scene) {
        this.mainPanel.add(scene.getPanel(), scene.getSceneName());
        this.cardLayout.show(this.mainPanel, scene.getSceneName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScreenWidth() {
        return this.screenSize.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScreenHeight() {
        return this.screenSize.height;
    }

    public void setController(final ControllerUtente controller) {
        this.controller = controller;
        this.changeScene(new HomeScene(this.controller));
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }
}
