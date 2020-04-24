package com.app.csv.exercise.view.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.csv.exercise.R;
import com.app.csv.exercise.databinding.ListItemRowBinding;
import com.app.csv.exercise.model.Item;
import com.app.csv.exercise.view.activities.MainActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is adapter class to display the item list.
 * */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private List<Item> itemList;
    private MainActivity parentActivity;

    public ItemListAdapter(MainActivity activity, List<Item> itemList) {
        this.itemList = itemList;
        this.parentActivity = activity;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.list_item_row, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Item item = itemList.get(position);
        viewHolder.binding.title.setText(item.getTitle());
        viewHolder.binding.description.setText(item.getDescription());

        if(item.getImageHref() != null) {
            viewHolder.binding.image.setVisibility(View.VISIBLE);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                    (Uri.parse(item.getImageHref()))).setOldController(viewHolder.binding.image.getController()).build();
            viewHolder.binding.image.setController(draweeController);
        }else{
            viewHolder.binding.image.setVisibility(View.GONE);
        }

        viewHolder.binding.itemView.setTag(itemList.get(position));
        viewHolder.binding.itemView.setOnClickListener(mOnClickListener);
    }

    public void updateData(List<Item> updatedList){
        this.itemList = updatedList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ListItemRowBinding binding;

        ViewHolder(ListItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Item item = (Item) view.getTag();
            parentActivity.onItemSelected(item);

        }
    };
}
