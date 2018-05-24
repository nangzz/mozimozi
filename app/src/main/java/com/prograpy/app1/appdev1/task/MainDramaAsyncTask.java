package com.prograpy.app1.appdev1.task;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.MainDramaResult;

import java.util.HashMap;
import java.util.Map;

public class MainDramaAsyncTask extends AsyncTask<String, Integer, MainDramaResult> {
    private MainDramaResultHandler handler;


    public interface MainDramaResultHandler{
    public void onSuccessAppAsyncTask(MainDramaResult result);
    public void onFailAppAsysncask();
    public void onCancelAppAsyncTask();
}



    public MainDramaAsyncTask(MainDramaResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainDramaResult doInBackground(String... strings) {

        String path = strings[0];
        String channelname = strings[1];

        MainDramaResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("channelname", channelname);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "POST", params);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, MainDramaResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(MainDramaResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }

    }
}

//c+r
