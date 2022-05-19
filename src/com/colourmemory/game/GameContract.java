package com.colourmemory.game;

import android.app.Activity;
import android.widget.Button;

/**
 * Android MVP interface
 *
 * @author kennetht
 *
 */
public class GameContract {

    /**
     * what method we have in presenter
     *
     * @author kennetht
     *
     */
    public interface Presenter {
	/**
	 * start a new game
	 *
	 * @param size
	 * @param size2
	 */
	void newGame(int size, int size2);

	/**
	 * get cards button on view
	 *
	 * @param activity
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	Button createImageButton(Activity activity, int xPosition, int yPosition);

	/**
	 * get point
	 *
	 * @return
	 */
	int getPoint();

    }

    /**
     * what method we have in View
     *
     * @author kennetht
     *
     */
    public interface ViewController {
	/**
	 * connect to presenter
	 *
	 * @param presenter
	 */
	void setPresenter(Presenter presenter);

	/**
	 * finish game and change page
	 */
	void gameOver();

	/**
	 * set title view text
	 *
	 * @param string
	 */
	void setTitle(String string);
    }
}
