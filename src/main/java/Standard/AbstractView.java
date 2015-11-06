package Standard;

import javax.swing.*;

/**
 * Created by rober on 1-11-2015.
 */
public abstract class AbstractView extends JPanel {

    public AbstractView() {

    }

    public abstract void update();
    public abstract void check();

}
