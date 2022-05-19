package com.colourmemory.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.colourmemory.R;
import com.colourmemory.util.GameCard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Presenter is a layer that provides View with data from Model. Presenter also
 * handles background tasks.
 *
 * @author kennetht
 *
 */
public class GamePresener implements GameContract.Presenter {
    private final transient Context appContext; // Application Context

    private final transient GameContract.ViewController view; // MVP-View

    private transient int point; // user takes times for flip
    private transient int[][] cards; // card init array
    private transient GameCard card1; // first card to click
    private transient GameCard card2; // second card to click
    private transient int cardCount; // how many cards on board
    private transient int rowCount; // how many row
    private transient int colCount; // how many column

    private final transient Handler handler; // using android handler to async
					     // UI
    private static Object lock = new Object();// lock to only update card UI
					      // after click

    private final transient ButtonListener buttonListener; // cards object
							   // onClick
    // listener

    private Drawable cardbackImage; // card back
    private List<Drawable> cardImages; // card image

    /**
     * init
     *
     * @param appContext
     * @param view
     */
    public GamePresener(final Context appContext, final GameContract.ViewController view) {
	this.appContext = appContext;
	this.view = view;

	handler = new Handler(new Handler.Callback() {
	    @Override
	    public boolean handleMessage(final Message msg) {
		synchronized (lock) {
		    checkCards();
		}
		return false;
	    }
	});

	// init once for reuse this in a loop
	buttonListener = new ButtonListener();

	// init Resources
	loadImages();
    }

    @Override
    public int getPoint() {
	return point;
    }

    @Override
    public void newGame(final int column, final int rows) {
	rowCount = rows;
	colCount = column;

	cards = new int[colCount][rowCount];
	card1 = null;
	card2 = null;
	loadCards();

	point = 0;
	view.setTitle(appContext.getString(R.string.rank_point) + ": " + point);
    }

    /**
     * preload image and support old version of android
     */
    @SuppressWarnings("deprecation")
    private void loadImages() {
	cardbackImage = appContext.getResources().getDrawable(R.drawable.card_bg);

	cardImages = new ArrayList<Drawable>();
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour1));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour2));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour3));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour4));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour5));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour6));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour7));
	cardImages.add(appContext.getResources().getDrawable(R.drawable.colour8));
    }

    private void loadCards() {
	try {
	    final int size = rowCount * colCount;
	    cardCount = size;

	    // create a array with size we have to pick the cards out of the box
	    final ArrayList<Integer> list = new ArrayList<Integer>();
	    for (int i = 0; i < size; i++) {
		list.add(i);
	    }

	    // random assign card markers
	    final Random random = new Random();
	    for (int i = size - 1; i >= 0; i--) {
		int pickedNum = 0;

		if (i > 0) {
		    pickedNum = random.nextInt(i);
		}

		pickedNum = list.remove(pickedNum).intValue();
		cards[i % colCount][i / colCount] = pickedNum % (size / 2);
	    }
	} catch (final Exception exception) {
	    Log.e(exception.getClass().getName(), exception.getMessage(), exception);
	}
    }

    /**
     * support old android for background
     */
    @SuppressWarnings("deprecation")
    @Override
    public Button createImageButton(final Activity activity, final int x, final int y) {
	final Button button = new Button(activity);
	// define button with id
	button.setId(100 * x + y);
	button.setBackgroundDrawable(cardbackImage);
	button.setOnClickListener(buttonListener);

	return button;
    }

    class ButtonListener implements OnClickListener {
	@Override
	public void onClick(final View v) {
	    synchronized (lock) {
		if (card1 != null && card2 != null) {
		    return;
		}
		final int id = v.getId();
		final int x = id / 100;
		final int y = id % 100;
		turnCard((Button) v, x, y);
	    }

	}

	@SuppressWarnings("deprecation")
	private void turnCard(final Button button, final int x, final int y) {
	    button.setBackgroundDrawable(cardImages.get(cards[x][y]));

	    if (card1 == null) {
		// pick first card
		card1 = new GameCard(button, x, y);
	    } else {
		// pick sec card and matching
		if (card1.xPosition == x && card1.yPosition == y) {
		    return; // the user pressed the first card
		}

		card2 = new GameCard(button, x, y);

		// After each round, a brief one(1) second pause
		// should be implemented befor escoring to allow
		// the player to see what the second selected card is.
		final Timer t = new Timer(false);
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {
			try {
			    synchronized (lock) {
				handler.sendEmptyMessage(0);
			    }
			} catch (final Exception exception) {
			    Log.e(exception.getClass().getName(), exception.getMessage(), exception);

			}
		    }
		}, 1000);
	    }
	}
    }

    /**
     * The game starts initially with all cards facing down. The player is to
     * then flip two cards each round, trying to find a match. If the flipped
     * pair is a match, the player receives two (2) points, and the cards may be
     * removed from the game board. Otherwise, the cards are turned face­ down
     * again and the player loses one (1) point. This continues until all pairs
     * have been found.
     */
    @SuppressWarnings("deprecation")
    public void checkCards() {
	// check if its same card object
	if (cards[card2.xPosition][card2.yPosition] == cards[card1.xPosition][card1.yPosition]) {
	    // remove two card from view
	    card1.button.setVisibility(View.INVISIBLE);
	    card2.button.setVisibility(View.INVISIBLE);
	    cardCount = cardCount - 2;

	    point++;
	    point++;
	    view.setTitle("Point: " + point);
	} else {
	    // flip cards back
	    card2.button.setBackgroundDrawable(cardbackImage);
	    card1.button.setBackgroundDrawable(cardbackImage);

	    point--;
	    view.setTitle("Point: " + point);
	}

	card1 = null;
	card2 = null;

	if (cardCount == 0) {
	    // game finish
	    view.gameOver();
	}
    }
}
