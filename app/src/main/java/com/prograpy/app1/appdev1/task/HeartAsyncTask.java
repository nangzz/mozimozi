package com.prograpy.app1.appdev1.task;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.db.DbController;
import com.prograpy.app1.appdev1.network.ApiValue;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.ServerSuccessCheckResult;

import java.util.HashMap;
import java.util.Map;
import com.prograpy.app1.appdev1.utils.D;

public class HeartAsyncTask extends AsyncTask<String, Integer, ServerSuccessCheckResult> {

    private TaskResultHandler handler;
    private Context context;
    private int p_id = 0;

    private boolean isAddHeart = false;

    public interface TaskResultHandler{
        public void onSuccessAppAsyncTask(ServerSuccessCheckResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }

    public HeartAsyncTask(Context context, TaskResultHandler handler){
        this.context = context;
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
        p_id = Integer.parseInt(strings[2]);

        isAddHeart = path.equals(ApiValue.API_HEART_CHECK);

        ServerSuccessCheckResult result  = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);
        params.put("productid", p_id);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,  "POST", params);

            D.log("http", "str > " + str);


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

            try {

                if(isAddHeart)
                    DbController.addProductId(context, p_id);
                else
                    DbController.deleteProductId(context, p_id);

            }catch (Exception e){
                e.printStackTrace();
            }


            handler.onSuccessAppAsyncTask(AppAsyncTaskResult);
        }else{
            handler.onFailAppAsysncask();
        }

    }
}

//c+r
