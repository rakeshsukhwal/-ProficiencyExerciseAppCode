package com.app.csv.exercise.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.csv.exercise.R;
import com.app.csv.exercise.databinding.DetailsFragmentBinding;
import com.app.csv.exercise.model.Item;
import com.app.csv.exercise.viewmodel.SharedDetailsViewModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class DetailsFragment extends Fragment {

    private DetailsFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        displayData();
    }

    private void displayData(){
        SharedDetailsViewModel sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedDetailsViewModel.class);
        Item selectedItem = sharedViewModel.getSelectedItem();
        if (selectedItem != null){
            binding.title.setText(selectedItem.getTitle());
            binding.description.setText(selectedItem.getDescription());

            if (selectedItem.getImageHref() != null) {//Image URL is available, display image
                binding.newsImage.setVisibility(View.VISIBLE);
                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequest.fromUri(Uri.parse(selectedItem.getImageHref())))
                        .setOldController(binding.newsImage.getController()).build();
                binding.newsImage.setController(draweeController);
            }else{//Image URL is not available, hide image view
                binding.newsImage.setVisibility(View.GONE);
            }
        }
    }
}
