package com.colourmemory.game;

import com.colourmemory.HomeActivity;
import com.colourmemory.HomeFragment;
import com.colourmemory.R;
import com.colourmemory.game.GameContract.Presenter;
import com.colourmemory.rank.RankActivity;
import com.colourmemory.util.SQLiteHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * gameboard page write with android MVP structure
 *
 * @author kennetht
 *
 */
public class GameFragment extends Fragment implements GameContract.ViewController {
    private final transient int size;

    private transient FragmentActivity activity;
    private transient Context appContext;

    private transient TextView titleText;
    private transient Chronometer chronometer;
    private transient TableRow cardArea;

    private transient SQLiteHelper helper;// to insert scroll

    private transient GameContract.Presenter presenter;

    /**
     * start game with a gameboard size
     *
     * @param size
     */
    public GameFragment(final int size) {
	super();
	this.size = size;
    }

    @Override
    public void onAttach(final Context context) {
	super.onAttach(context);

	if (context instanceof Activity) {
	    // connect to parents
	    this.activity = (FragmentActivity) context;
	    // shorter the code
	    appContext = activity.getApplicationContext();
	} else {
	    // only call me by activity
	    throw new NullPointerException();
	}
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstaceState) {
	final View view = inflater.inflate(R.layout.game_fragment, container, false);
	// the last parameter false is importent on replace fragment

	helper = new SQLiteHelper(activity);

	titleText = (TextView) view.findViewById(R.id.titleText);
	chronometer = (Chronometer) view.findViewById(R.id.chronometer1);
	chronometer.setVisibility(View.INVISIBLE);
	cardArea = (TableRow) view.findViewById(R.id.cardArea);

	// Clicking the High Scores button will take the user to the
	// table of highscores.
	final Button btnMore = (Button) view.findViewById(R.id.btnmore);
	btnMore.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(final View arg0) {
		((HomeActivity) activity).frgReplace(new HomeFragment());

		startActivity(new Intent(appContext, RankActivity.class));
	    }
	});

	return view;
    }

    @Override
    public void setPresenter(final Presenter presenter) {
	this.presenter = presenter;
    }

    @Override
    public void onResume() {
	super.onResume();
	setPresenter(new GamePresener(appContext, this));

	final TableLayout mainTable = new TableLayout(activity);
	for (int column = 0; column < size; column++) {
	    mainTable.addView(createRow(column));
	}

	cardArea.removeAllViews();
	cardArea.addView(mainTable);

	chronometer.setVisibility(View.VISIBLE);
	chronometer.start();

	presenter.newGame(size, size);
    }

    private TableRow createRow(final int column) {
	final TableRow rowView = new TableRow(activity);
	rowView.setHorizontalGravity(Gravity.CENTER);

	for (int row = 0; row < size; row++) {
	    rowView.addView(presenter.createImageButton(activity, row, column));
	}
	return rowView;
    }

    @Override
    public void setTitle(final String string) {
	titleText.setText(string);
    }

    /**
     * After the game is finished, the user should be prompted to input his
     * name. The user's name and the score would then be stored in a database,
     * and the user should be notified of his score and the current rankings.
     */
    @Override
    public void gameOver() {
	chronometer.stop();

	// Set up the name input
	final EditText input = new EditText(activity);
	input.setInputType(InputType.TYPE_CLASS_TEXT);
	// The user input may be implemented as a separate screen or as a popup

	final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	builder.setTitle("Congratulation! Please enter the name of the ranking.");
	builder.setView(input);
	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(final DialogInterface dialog, final int which) {
		// When a player submits his name, the entered value must pass
		// basic validation(no empty strings) in order to continue.
		if (input.length() == 0) {
		    input.setText("User");
		}

		helper.insert(input.getText().toString(), String.valueOf(presenter.getPoint()),
			chronometer.getText().toString());

		((HomeActivity) activity).frgReplace(new HomeFragment());

		startActivity(new Intent(appContext, RankActivity.class));
	    }

	});
	builder.setNegativeButton("No, thanks.", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(final DialogInterface dialog, final int which) {
		((HomeActivity) activity).frgReplace(new HomeFragment());

		dialog.cancel();
	    }
	});
	builder.show();
    }
}
