package com.colourmemory.rank;

import com.colourmemory.R;
import com.colourmemory.util.It;
import com.colourmemory.util.JArrayAdapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author kennetht
 *
 */
public class RankAdapter extends JArrayAdapter {
    private transient ListItem listItem;

    final class ListItem {
	TextView rank;

	TextView username;

	TextView point;

	TextView time;
    }

    /**
     * The high scores table must be displayed as a proper table with three
     * columns: Rank, Name, and Score
     *
     * @param activity
     * @param listArray
     */
    public RankAdapter(final Activity activity, final JSONArray listArray) {
	super(activity, listArray);
    }

    private void initView(final int position) {
	final JSONObject jObject = (JSONObject) getItem(position);

	listItem.rank.setText(String.valueOf(position + 1));
	listItem.username.setText(String.valueOf(jObject.get("username")));
	listItem.point.setText(String.valueOf(jObject.get("point")));
	listItem.time.setText(String.valueOf(jObject.get("time")));
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
	View view = convertView;
	if (It.isNull(view)) {
	    view = getContentView(R.layout.rank_item, parent);
	    listItem = new ListItem();
	    listItem.rank = (TextView) view.findViewById(R.id.rank);
	    listItem.username = (TextView) view.findViewById(R.id.username);
	    listItem.point = (TextView) view.findViewById(R.id.point);
	    listItem.time = (TextView) view.findViewById(R.id.time);

	    view.setTag(listItem);
	} else {
	    listItem = (ListItem) view.getTag();
	}

	initView(position);

	return view;
    }
}