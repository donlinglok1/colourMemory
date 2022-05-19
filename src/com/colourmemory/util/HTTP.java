package com.colourmemory.util;

import java.io.IOException;

import android.util.Log;
import net.minidev.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * api calling basic on okhttp lib
 * 
 * @author kennetht
 *
 */
public class HTTP {
    /**
     *
     * @author kennetht
     *
     */
    public interface RESTFulCallback {
	/**
	 *
	 * @param call
	 * @param result
	 */
	void onSuccess(Call call, String result);

	/**
	 *
	 * @param call
	 * @param result
	 */
	void onFail(Call call, String result);
    }

    /**
     *
     * @param tag
     * @param string
     */
    public static void longlog(final String tag, final String string) {
	final int maxLogSize = 1000;
	for (int i = 0; i <= string.length() / maxLogSize; i++) {
	    final int start = i * maxLogSize;
	    int end = (i + 1) * maxLogSize;
	    end = end > string.length() ? string.length() : end;
	    Log.v(tag, string.substring(start, end));
	}
    }

    /**
     *
     * @param url
     * @param appContext
     * @param jsonObject
     * @param callback
     */
    public static void get(final String url, final RESTFulCallback callback) {
	final Request request = new Request.Builder().header("Content-Type", "application/json")
		.header("Cache-Control", "no-cache").url(url).build();
	OKHttp.enqueue(request, new Callback() {
	    @Override
	    public void onResponse(final Call call, final Response response) throws IOException {
		final String result = response.body().string();
		HTTP.longlog("", "" + result);
		if (It.isEqual(response.code(), 200)) {
		    callback.onSuccess(call, result);
		} else {
		    callback.onFail(call, result);
		}
	    }

	    @Override
	    public void onFailure(final Call call, final IOException exception) {
		callback.onFail(call, exception.getMessage());
	    }
	});
    }

    /**
     *
     * @param url
     * @param appContext
     * @param jsonObject
     * @param callback
     */
    public static void post(final String url, final JSONObject jsonObject, final RESTFulCallback callback) {
	final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
		jsonObject.toJSONString());

	final Request request = new Request.Builder().header("Content-Type", "application/json")
		.header("Cache-Control", "no-cache").url(url).post(body).build();
	OKHttp.enqueue(request, new Callback() {
	    @Override
	    public void onResponse(final Call call, final Response response) throws IOException {
		final String result = response.body().string();
		HTTP.longlog("", "" + result);
		if (It.isEqual(response.code(), 200)) {
		    callback.onSuccess(call, result);
		} else {
		    callback.onFail(call, result);
		}
	    }

	    @Override
	    public void onFailure(final Call call, final IOException exception) {
		callback.onFail(call, exception.getMessage());
	    }
	});
    }
}
