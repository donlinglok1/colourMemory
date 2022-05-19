package com.colourmemory.util;

import android.widget.Button;

/**
 * Card Object
 *
 * @author donlinglok
 *
 */
public class GameCard {
    /**
     * cards x position
     */
    public final int xPosition;

    /**
     * cards y position
     */
    public final int yPosition;

    /**
     * clickable object
     */
    public final Button button;

    /**
     * init cards.
     *
     * @param button
     * @param xPosition
     * @param yPosition
     */
    public GameCard(final Button button, final int xPosition, final int yPosition) {
	this.xPosition = xPosition;
	this.yPosition = yPosition;
	this.button = button;
    }
}
