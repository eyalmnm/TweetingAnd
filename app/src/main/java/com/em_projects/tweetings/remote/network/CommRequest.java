package com.em_projects.tweetings.remote.network;

import java.util.Map;

/**
 * Created by eyal muchtar on 24/11/2016.
 */

public class CommRequest {

    private String serverURL;

    ;
    private Map<String, String> params;
    private CommListener listener;
    private MethodType methodType;

    public CommRequest(String serverURL, Map<String, String> params, CommListener listener) {
        this.serverURL = serverURL;
        this.params = params;
        this.listener = listener;
        this.methodType = MethodType.GET;
    }


    public CommRequest(String serverURL, Map<String, String> params, MethodType methodType, CommListener listener) {
        this.serverURL = serverURL;
        this.params = params;
        this.listener = listener;
        this.methodType = methodType;
    }

    public String getServerURL() {
        return serverURL;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public CommListener getListener() {
        return listener;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public enum MethodType {GET, POST, SOCKET}
}
