package com.prograpy.app1.appdev1.task;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.AppResult;

public class AppAsyncTask extends AsyncTask<String, Integer, AppResult> {
    private AppAsyncTaskResultHandler handler;


    public interface AppAsyncTaskResultHandler{
    public void onSuccessAppAsyncTask(AppResult result);
    public void onFailAppAsysncask();
    public void onCancelAppAsyncTask();
}



    public AppAsyncTask(AppAsyncTaskResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected AppResult doInBackground(String... strings) {

        String path = strings[1];

        AppResult result  = null;


        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "GET",null);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, AppResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(AppResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }

    }
}

