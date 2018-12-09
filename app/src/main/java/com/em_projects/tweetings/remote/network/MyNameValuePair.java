package com.em_projects.tweetings.remote.network;

import org.apache.http.NameValuePair;

/**
 * Created by eyalmuchtar on 23/02/2017.
 */

public class MyNameValuePair implements NameValuePair {

    private String name = null;
    private String value = null;

    public MyNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MyNameValuePair(String name, long value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public MyNameValuePair(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public MyNameValuePair(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
