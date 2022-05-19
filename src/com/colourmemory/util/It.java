package com.colourmemory.util;

/**
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class It {
    private It() {
    }

    /**
     * handle null objec and empty string
     *
     * @param object
     * @return
     */
    public static boolean isNull(final Object object) {
	return null == object || String.valueOf(object).length() == 0;
    }

    /**
     * check equal with handle null
     *
     * @param objA
     * @param objB
     * @return
     */
    public static boolean isEqual(final Object objA, final Object objB) {
	if (isNull(objA)) {
	    return objB.equals(objA);
	} else {
	    return objA.equals(objB);
	}
    }
}
