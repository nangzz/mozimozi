package com.prograpy.app1.appdev1.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app1.appdev1.network.HttpRequest;
import com.prograpy.app1.appdev1.network.response.PwSearchResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Note on 2018-07-19.
 */

public class IdPwSearchAsyncTask extends AsyncTask<String, Integer, PwSearchResult> {

    private IdPwSearchHandler handler;

    public interface IdPwSearchHandler{
        public void onSuccessAppAsyncTask(PwSearchResult result);
        public void onFailAppAsysncask();
        public void onCancelAppAsyncTask();
    }

    public IdPwSearchAsyncTask(IdPwSearchHandler handler){this.handler = handler;}

    @Override
    protected void  onPreExecute(){super.onPreExecute();}

    @Override
    protected PwSearchResult doInBackground(String... strings) {
        String path = strings[0];
        String username = strings[1];
        String useremail = strings[2];
        String userid = strings[3];
        PwSearchResult result = null;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("username",username);
        params.put("useremail",useremail);
        params.put("userid",userid);


        HttpRequest request = new HttpRequest();

        try{
            String str = request.callRequestServer(path,"POST",params);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str,PwSearchResult.class);
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return result;
    }


    @Override
    protected  void onPostExecute(PwSearchResult pwSearchResult){
        super.onPostExecute(pwSearchResult);

        if(pwSearchResult != null){
            handler.onSuccessAppAsyncTask(pwSearchResult);
        }
        else{
            handler.onFailAppAsysncask();
        }
    }
}
