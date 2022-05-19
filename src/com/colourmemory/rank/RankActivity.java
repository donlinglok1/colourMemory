package com.colourmemory.rank;

import com.colourmemory.R;
import com.colourmemory.util.SQLiteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Ranking Activitya
 *
 * @author donlinglok
 *
 */
public class RankActivity extends Activity {
    /**
     * get data from db and show on list by adapter
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rank_activity);

	final JSONArray listArray = new JSONArray();
	final SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
	final Cursor cursor = sqLiteHelper.select();

	try {
	    while (cursor.moveToNext()) {
		final JSONObject jObject = new JSONObject();
		jObject.put("username", cursor.getString(1));
		jObject.put("point", cursor.getString(2));
		jObject.put("time", cursor.getString(3));
		listArray.add(jObject);
	    }
	    // A score is considered a high score
	    // if the score is higher than a current
	    // high score OR there is spacein the table.
	    final ListView listView = (ListView) this.findViewById(R.id.listview);
	    listView.setAdapter(new RankAdapter(this, listArray));
	} catch (final Exception exception) {
	    Log.e(exception.getClass().getName(), exception.getMessage(), exception);
	} finally {
	    cursor.close();
	}

    }
}
