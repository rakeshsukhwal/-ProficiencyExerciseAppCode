package com.app.csv.exercise.viewmodel;



import com.app.csv.exercise.model.Item;

import androidx.lifecycle.ViewModel;

/**
 * This is view model class used to display the details of selected row. It contains the selected row data from the list
 * */
public class SharedDetailsViewModel extends ViewModel {
    /**
     * Selected Item
     * */
    private Item selectedItem;

    /**
     * This method returns the selected news entity
     * @return NewsEntity if selectedItem is available, otherwise returns null
     * */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * This method set the selected news entity
     * @param  selectedItem to set the selected news entity
     * */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
}
