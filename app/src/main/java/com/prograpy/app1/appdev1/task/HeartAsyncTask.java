package com.prograpy.app1.appdev1.task;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;

import java.util.HashMap;
import java.util.Map;

public class HeartAsyncTask extends AsyncTask<String, Integer, ServerSuccessCheckResult> {
    private TaskResultHandler handler;


    public interface TaskResultHandler{
        public void onSuccessAppAsyncTask(ServerSuccessCheckResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }

    public HeartAsyncTask(TaskResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ServerSuccessCheckResult doInBackground(String... strings) {

        String path = strings[0];
        String userid = strings[1];
        int productid = Integer.parseInt(strings[2]);

        ServerSuccessCheckResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);
        params.put("productid", productid);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "POST", params);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, ServerSuccessCheckResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(ServerSuccessCheckResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }else{
            handler.onFailAppAsysncask();
        }

    }
}

//c+r
