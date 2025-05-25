package view.amministratore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import view.api.Scene;
import view.api.View;

public class ViewAmm implements View{

    private static final double FRAME_SIZE_FACTOR = 0.8;
    private static final String FRAME_ICON_PATH = "table/hat.png";

    private final JFrame frame;
    private final Dimension screenSize;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    /**
     * Constructor for the MainView.
     * It initializes the frame and size of the window.
     * It also sets the CardLayout for switching between scenes.
     * @param visible if the window should be visible
     */
    public ViewAmm(final boolean visible) {
        this.frame = new JFrame("Poker Texas Hold'em");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final var initWidth = (int) (screenSize.width * FRAME_SIZE_FACTOR);
        final var initHeight = (int) (screenSize.height * FRAME_SIZE_FACTOR);
        this.frame.setMinimumSize(new Dimension(initWidth, initHeight));
        this.frame.setSize(this.frame.getMinimumSize());

        // Set the icon of the frame
        final var iconImage = new ImageIcon(ClassLoader.getSystemResource(FRAME_ICON_PATH)).getImage();
        this.frame.setIconImage(iconImage);

        // CardLayout for switching between scenes
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.frame.setContentPane(this.mainPanel);

        // Always start with the start scene
        //this.changeScene(new StartScene(new StartControllerImpl(this)));

        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(visible);
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


}
