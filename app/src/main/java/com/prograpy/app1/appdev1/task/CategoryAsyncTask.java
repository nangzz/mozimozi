package com.prograpy.app1.appdev1.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.CategoryResult;


import java.util.HashMap;
import java.util.Map;

public class CategoryAsyncTask extends AsyncTask<String, Integer, CategoryResult> {

    private CategoryResultHandler handler;


    public interface CategoryResultHandler{
        public void onSuccessAppAsyncTask(CategoryResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }



    public CategoryAsyncTask(CategoryResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected CategoryResult doInBackground(String... strings) {

        String url = strings[0];
        String path = strings[1];
        String p_cat = strings[2];

        CategoryResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("p_cat", p_cat);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(url, path,  "POST", params);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, CategoryResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(CategoryResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }

    }
}

//c+r
