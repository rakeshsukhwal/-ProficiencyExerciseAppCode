package com.app.csv.exercise.viewmodel;

import android.app.Application;

import com.app.csv.exercise.model.APIResponse;
import com.app.csv.exercise.repository.NetworkRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * This is view model class for Main Activity. It contains data list.
 * It will load the data list from Network Repository and Local Repository
 * */
public class ListViewModel extends AndroidViewModel {
    private NetworkRepository networkRepository;

    /**
     * This is mutable live data containing list.
     * */
    private MutableLiveData<APIResponse> liveDataResponse;

    public ListViewModel(@NonNull Application application) {
        super(application);
        liveDataResponse = new MutableLiveData<>();
        networkRepository = new NetworkRepository(getApplication().getApplicationContext());
    }

    /**
     * This method return mutable live data object containing response
     * @return It returns MutableLiveData<APIResponse>
     * */
    public MutableLiveData<APIResponse> getLiveDataResponse() {
        return liveDataResponse;
    }

    /**
     * Loads data from network repository(Server API) and update the response in live data
     * */
    public void loadDataFromServer(){
        networkRepository.loadListFromServer(liveDataResponse);
    }

}
