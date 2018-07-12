package com.prograpy.app1.appdev1.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.SearchResult;

/**
 * Created by Note on 2018-07-12.
 */

public class MainTopItemAsyncTask extends AsyncTask<String, Integer, SearchResult> {
    private MainTopItemAsyncTask.MainTopItemResultHandler handler;


    public interface MainTopItemResultHandler{
        public void onSuccessAppAsyncTask(SearchResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }



    public MainTopItemAsyncTask(MainTopItemAsyncTask.MainTopItemResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected SearchResult doInBackground(String... strings) {

        String path = strings[0];

        SearchResult result  = null;

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "GET", null);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, SearchResult.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(SearchResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);

        }else{
            handler.onFailAppAsysncask();
        }

    }
}

