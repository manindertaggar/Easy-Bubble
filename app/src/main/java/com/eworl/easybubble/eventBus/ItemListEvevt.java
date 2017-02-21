package com.eworl.easybubble.eventBus;

import com.eworl.easybubble.utils.ItemObject;

import java.util.List;

/**
 * Created by Dhankher on 2/19/2017.
 */
public class ItemListEvevt {
    List<ItemObject> allItems;
    public ItemListEvevt(List<ItemObject> allItems) {
        this.allItems = allItems;
        getAllItems();
    }

    public List<ItemObject> getAllItems() {
        return allItems;
    }
}
