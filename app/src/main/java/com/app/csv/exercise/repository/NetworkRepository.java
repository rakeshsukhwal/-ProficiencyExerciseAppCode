package com.app.csv.exercise.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.csv.exercise.model.APIResponse;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * This is repository class to get list from server API
 * */
public class NetworkRepository {
    private static final String TAG = NetworkRepository.class.getSimpleName();
    private static final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";

    private Context context;

    public NetworkRepository(@NonNull Context context) {
        this.context = context;
    }

    public void loadListFromServer(final MutableLiveData<APIResponse> mutableLiveData){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: response received");
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response, APIResponse.class);
                        mutableLiveData.postValue(apiResponse);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e(TAG, "onErrorResponse: error=>"+error.getMessage());
                        mutableLiveData.postValue(null);
                    }
                });

        queue.add(stringRequest);
    }
}
