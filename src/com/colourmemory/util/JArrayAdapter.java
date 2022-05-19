package com.colourmemory.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import net.minidev.json.JSONArray;

/**
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class JArrayAdapter extends BaseAdapter {
    private transient Activity activity;
    private transient JSONArray listArray;

    /**
     * init
     *
     * @param activity
     * @param listArray
     */
    public JArrayAdapter(final Activity activity, final JSONArray listArray) {
	super();
	this.activity = activity;
	this.listArray = listArray;
    }

    /**
     * shorter code
     *
     * @param resid
     * @param parent
     * @return
     */
    public View getContentView(final int resid, final ViewGroup parent) {
	return LayoutInflater.from(activity).inflate(resid, parent, false);
    }

    @Override
    public int getCount() {
	return listArray.size();
    }

    @Override
    public Object getItem(final int position) {
	return listArray.get(position);
    }

    @Override
    public long getItemId(final int position) {
	return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
	return null;
    }
}
