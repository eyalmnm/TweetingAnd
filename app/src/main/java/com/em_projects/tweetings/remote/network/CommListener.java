package com.em_projects.tweetings.remote.network;

/**
 * Created by eyalmuchtar on 24/11/2016.
 */

public interface CommListener {
    public void newDataArrived(String response);

    public void exceptionThrown(Throwable throwable);
}
