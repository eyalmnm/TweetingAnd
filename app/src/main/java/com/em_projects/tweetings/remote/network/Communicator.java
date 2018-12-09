package com.em_projects.tweetings.remote.network;

import android.util.Log;

import com.em_projects.tweetings.BuildConfig;
import com.em_projects.tweetings.utils.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.Thread.sleep;


public final class Communicator implements Runnable {
    private static final String serverURL = BuildConfig.SERVER_URL;
    private static final String appToken = BuildConfig.APP_TOKEN;
    private static String TAG = "Communicator";
    private static Communicator instance = null;
    private ArrayList<CommRequest> queue = new ArrayList(10);
    private Thread runner = null;
    private boolean isRunning = false;
    private Object monitor = new Object();
    private HttpClient client = null;

    private Communicator() {
        client = new DefaultHttpClient();

        isRunning = true;
        runner = new Thread(this);
        runner.start();
    }

    public static Communicator getInstance() {
        if (null == instance) {
            instance = new Communicator();
        }
        return instance;
    }

    public void getRegions(CommListener listener) {
        Log.d(TAG, "getRegions");
        String serverUrl = serverURL + "/api/regions.php";
        HashMap params = new HashMap();
        params.put("token", appToken);

        post(serverUrl, params, listener);
    }


    @Override
    public void run() {
        while (isRunning) {
            if (queue.isEmpty()) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            CommRequest requestHolder = queue.remove(0);
            handleRequest(requestHolder);
            // Stop running between the threads' creation.
            try {
                sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }


    private void handleRequest(final CommRequest requestHolder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (requestHolder != null) {
                        String response = transmitData(requestHolder);
                        if (requestHolder.getListener() != null) {
                            if (StringUtils.Companion.isNullOrEmpty(response)) {
                                requestHolder.getListener().exceptionThrown(new Exception());
                            } else {
                                requestHolder.getListener().newDataArrived(response);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "handleRequest", ex);
                    if (requestHolder != null && requestHolder.getListener() != null) {
                        requestHolder.getListener().exceptionThrown(ex);
                    }
                }
            }
        }).start();
    }

    private synchronized String transmitData(CommRequest commRequest) throws IOException {
        Map<String, String> params = commRequest.getParams();
        CommRequest.MethodType method = commRequest.getMethodType();
        String serverUrl = commRequest.getServerURL();
        HttpResponse httpResponse = null;
        HttpClient client = new DefaultHttpClient();

        if (method == CommRequest.MethodType.GET) {
            String body = encodeParams(params);
            String urlString = serverUrl + "?" + body;
            Log.d(TAG, "transmitData urlString: " + urlString);
            HttpGet request = new HttpGet(urlString);
            httpResponse = client.execute(request);
        } else if (method == CommRequest.MethodType.POST) {
            HttpPost httpPost = new HttpPost(serverUrl);
            ArrayList<NameValuePair> nameValuePairs = convertMapToNameValuePairs(params);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            Log.d(TAG, "transmitData urlString: " + serverUrl);
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = client.execute(httpPost);
        }

        // Check if server response is valid
        StatusLine status = httpResponse.getStatusLine();
        if (status.getStatusCode() != 200) {
            throw new IOException("Invalid response from server: " + status.toString());
        }
        // Return result from buffered stream
        String answer = handleHttpResponse(httpResponse);
        return answer;
    }

    // constructs the GET body using the parameters
    private String encodeParams(Map<String, String> params) {
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            if (param.getValue() != null) {
                try {
                    bodyBuilder.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "encodeParams", e);
                }
                if (iterator.hasNext()) {
                    bodyBuilder.append('&');
                }
            }
        }
        return bodyBuilder.toString();
    }

    // constructs the POST body using the parameters
    private ArrayList<NameValuePair> convertMapToNameValuePairs(Map<String, String> params) {
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>(params.size());
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            String val = param.getValue();
            if (val != null) {
//                try {
                nameValuePairs.add(new MyNameValuePair(param.getKey(), val));//URLEncoder.encode(val,"UTF-8")));
//                } catch (UnsupportedEncodingException e) {
//                    nameValuePairs.add(new MyNameValuePair(param.getKey(), param.getValue()));
//                }
            }
        }
        return nameValuePairs;
    }

    private String handleHttpResponse(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream is = httpResponse.getEntity().getContent();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuffer stringBuffer = new StringBuffer("");
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
        return stringBuffer.toString();
    }


    private void post(final String serverURL, final Map<String, String> params, CommListener listener) {

        queue.add(new CommRequest(serverURL, params, listener));
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        isRunning = false;
        if (null != runner) {
            runner.join();
            runner = null;
        }
        if (null != queue) {
            queue.clear();
            queue = null;
        }
        super.finalize();
    }
}
