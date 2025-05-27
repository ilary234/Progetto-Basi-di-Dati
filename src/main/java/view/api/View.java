package view.api;

import javax.swing.JFrame;

public interface View {

    /**
     * Changes the scene of the view.
     * @param scene the {@link Scene} to change to
     */
    void changeScene(Scene scene);

    /**
     * Gets the width of the screen.
     * @return the width of the screen
     */
    int getScreenWidth();

    /**
     * Gets the height of the screen.
     * @return the height of the screen
     */
    int getScreenHeight();

    /**
     * Gets the main frame.
     * @return the main frame
     */
    JFrame getFrame();

}
