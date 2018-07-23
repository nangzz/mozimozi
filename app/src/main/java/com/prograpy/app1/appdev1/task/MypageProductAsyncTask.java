package com.prograpy.app1.appdev1.task;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.CategoryResult;
import com.prograpy.app1.appdev1.network.response.MyPageProductResult;

import java.util.HashMap;
import java.util.Map;
import com.prograpy.app1.appdev1.utils.D;

public class MypageProductAsyncTask extends AsyncTask<String, Integer, MyPageProductResult> {
    private TaskResultHandler handler;


    public interface TaskResultHandler{
        public void onSuccessAppAsyncTask(MyPageProductResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }

    public MypageProductAsyncTask(TaskResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MyPageProductResult doInBackground(String... strings) {

        String path = strings[0];
        String userid = strings[1];

        MyPageProductResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "POST", params);

            D.log("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, MyPageProductResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(MyPageProductResult AppAsyncTaskResult) {
        super.onPostExecute(AppAsyncTaskResult);

        if(AppAsyncTaskResult != null){
            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }else{
            handler.onFailAppAsysncask();
        }

    }
}

//c+r
