package com.colourmemory;

import com.colourmemory.game.GameFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Home page for start a game or enter ranking page.
 *
 * @author kennetht
 *
 */
public class HomeFragment extends Fragment {
    private transient FragmentActivity activity;

    private transient ImageView gamesize; // choose model image
    private transient int size = 2; // game size

    @Override
    public void onAttach(final Context context) {
	super.onAttach(context);

	if (context instanceof Activity) {
	    // connect to parents
	    this.activity = (FragmentActivity) context;
	} else {
	    // only call me by activity
	    throw new NullPointerException();
	}
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstaceState) {
	final View view = inflater.inflate(R.layout.home_fragment, container, false);
	// the last parameter false is importent on replace fragment

	gamesize = (ImageView) view.findViewById(R.id.gamesize);

	final SeekBar sizeselect = (SeekBar) view.findViewById(R.id.sizeselect);
	sizeselect.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	    @Override
	    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
		setGameSize(progress);
	    }

	    @Override
	    public void onStartTrackingTouch(final SeekBar arg0) {
		// not listening
	    }

	    @Override
	    public void onStopTrackingTouch(final SeekBar arg0) {
		// not listening
	    }
	});
	// The goal of this work sample is to construct a sample memory game
	// called ”Colour Memory”. The game board consists of a 4x4 grid with 8
	// pairs of color cards.
	sizeselect.setProgress(1);

	final Button btnStart = (Button) view.findViewById(R.id.btnstart);
	btnStart.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(final View arg0) {
		// go to the game page
		((HomeActivity) activity).frgReplace(new GameFragment(size));
	    }
	});

	return view;
    }

    private void setGameSize(final int progress) {
	switch (progress) {
	case 0:
	    size = 2;
	    gamesize.setImageResource(R.drawable.size2x2);
	    break;
	case 1:
	    size = 4;
	    gamesize.setImageResource(R.drawable.size4x4);
	    break;
	default:
	    break;
	}
    }
}
