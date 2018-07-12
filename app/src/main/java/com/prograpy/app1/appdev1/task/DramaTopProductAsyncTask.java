package com.prograpy.app1.appdev1.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.DramaItemListResult;

import java.util.HashMap;
import java.util.Map;

public class DramaTopProductAsyncTask extends AsyncTask<String, Integer, DramaItemListResult> {

    private TaskResultHandler handler;


    public interface TaskResultHandler{
        public void onSuccessAppAsyncTask(DramaItemListResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }



    public DramaTopProductAsyncTask(TaskResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DramaItemListResult doInBackground(String... strings) {

        String path = strings[0];
        int dramaid = Integer.valueOf(strings[1]);

        DramaItemListResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dramaid", dramaid);


        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "POST", params);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, DramaItemListResult.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(DramaItemListResult dramaItemListResult) {
        super.onPostExecute(dramaItemListResult);


        if(dramaItemListResult != null){
            handler.onSuccessAppAsyncTask(dramaItemListResult);

        }else{
            handler.onFailAppAsysncask();
        }
    }
}
