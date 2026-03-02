package com.quickcommerce.thiskostha.dto;

import java.util.List;

import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.entity.Restaurant;

public class SearchResponse {

    private List<Restaurant> restaurants;
    private List<Item> items;

    public SearchResponse(List<Restaurant> restaurants,
                          List<Item> items) {
        this.restaurants = restaurants;
        this.items = items;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public List<Item> getItems() {
        return items;
    }
}
