package com.prograpy.app1.appdev1.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.DramaListResult;

import java.util.HashMap;
import java.util.Map;

public class MainDListAsyncTask extends AsyncTask<String, Integer, DramaListResult> {

    private MainDListResultHandler handler;


    public interface MainDListResultHandler{
        public void onSuccessAppAsyncTask(DramaListResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }



    public MainDListAsyncTask(MainDListResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DramaListResult doInBackground(String... strings) {

        String path = strings[0];

        DramaListResult result  = null;

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "GET", null);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, DramaListResult.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(DramaListResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);

        }else{
            handler.onFailAppAsysncask();
        }

    }
}

//c+r

