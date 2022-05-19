package com.colourmemory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Activity to switch page
 *
 * @author kennetht
 *
 */
@SuppressLint("Override") // safe check for library code
public class HomeActivity extends FragmentActivity {
    private transient Toast toast; // only display one toast object at a time
    private transient long lastBackPressTime;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.home_activity);

	// show home page
	frgReplace(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
	// safe check of only double press in 3 sec to back.
	if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
	    toast = Toast.makeText(this, "Press again to back", Toast.LENGTH_SHORT);
	    toast.show();
	    this.lastBackPressTime = System.currentTimeMillis();
	} else {
	    if (toast != null) {
		toast.cancel();
	    }
	    // show home page
	    frgReplace(new HomeFragment());
	}
    }

    /**
     * switch page using fragment replace
     *
     * @param fragment
     */
    public void frgReplace(final Fragment fragment) {
	try {
	    getSupportFragmentManager().beginTransaction().replace(R.id.layoutFragment, fragment).commit();
	} catch (final Exception exception) {
	    // safe check for library code
	    Log.e(exception.getClass().getName(), exception.getMessage(), exception);
	}
    }
}
