package com.app.csv.exercise.view.activities;

import android.os.Bundle;
import android.view.MenuItem;


import com.app.csv.exercise.R;
import com.app.csv.exercise.model.Item;
import com.app.csv.exercise.view.fragments.DetailsFragment;
import com.app.csv.exercise.viewmodel.SharedDetailsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * This activity displays the item details in a fragment
 */
public class DetailViewActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "extra_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getString(R.string.details));
        Item item = getIntent().getParcelableExtra(EXTRA_ITEM);
        SharedDetailsViewModel sharedViewModel = ViewModelProviders.of(this).get(SharedDetailsViewModel.class);
        if (item != null) {
            sharedViewModel.setSelectedItem(item);
        }

        DetailsFragment fragment = new DetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, fragment)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
