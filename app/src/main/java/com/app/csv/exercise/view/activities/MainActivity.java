package com.app.csv.exercise.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.csv.exercise.R;
import com.app.csv.exercise.databinding.ActivityMainBinding;
import com.app.csv.exercise.model.APIResponse;
import com.app.csv.exercise.model.Item;
import com.app.csv.exercise.view.adapters.ItemListAdapter;
import com.app.csv.exercise.view.fragments.DetailsFragment;
import com.app.csv.exercise.viewmodel.ListViewModel;
import com.app.csv.exercise.viewmodel.SharedDetailsViewModel;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * This activity class displays the list of data.
 * If the device is tablet, it will have two pane layout, one will display data list and one will display details of selected item.
 * */
public class MainActivity extends AppCompatActivity{
    private List<Item> itemList = new ArrayList<>();
    private ActivityMainBinding binding;
    private ListViewModel viewModel;
    private SharedDetailsViewModel sharedViewModel;
    private boolean isTwoPane;
    private ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Fresco.initialize(this);

        sharedViewModel = ViewModelProviders.of(this).get(SharedDetailsViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            isTwoPane = true;
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.list.getContext(), LinearLayoutManager.VERTICAL);
        binding.list.addItemDecoration(dividerItemDecoration);

        adapter = new ItemListAdapter(this, itemList);
        binding.list.setAdapter(adapter);
        loadData();
    }

    /**
     * This method load the data list from server by call view model method
     * */
    private void loadData(){
        //Display progress bar before starting call to load news data
        binding.progressBar.setVisibility(View.VISIBLE);

        //Set the observer for list live data. So once response is received, update the UI
        viewModel.getLiveDataResponse().observe(this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                binding.progressBar.setVisibility(View.GONE);
                if (apiResponse == null){
                    binding.errorMsg.setVisibility(View.VISIBLE);
                }else{
                    setTitle(apiResponse.getTitle());
                    updateList(apiResponse.getItems());
                }
            }
        });

        //Load data list from web API
        viewModel.loadDataFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            loadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(Item selectedItem){
        sharedViewModel.setSelectedItem(selectedItem);
        if (isTwoPane){
            DetailsFragment fragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }else {
            Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
            intent.putExtra(DetailViewActivity.EXTRA_ITEM, selectedItem);
            startActivity(intent);
        }
    }

    private void updateList(List<Item> list){
        this.itemList = list;
        if (itemList == null || itemList.isEmpty()){//Not able to get data, display error message
            binding.errorMsg.setVisibility(View.VISIBLE);
        }else{//Received data, display the list
            binding.errorMsg.setVisibility(View.GONE);

            for (int i = 0; i < itemList.size(); i++){
                Item item = itemList.get(i);
                if (item.getTitle() == null && item.getDescription() == null && item.getImageHref() == null){//Remove item which does not contain any value
                    itemList.remove(item);
                }
            }

            adapter.updateData(itemList);
        }
    }
}
