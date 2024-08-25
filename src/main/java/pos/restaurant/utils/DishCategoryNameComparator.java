package pos.restaurant.utils;

import pos.restaurant.models.Dish;

import java.util.Comparator;

public class DishCategoryNameComparator implements Comparator<Dish> {
    @Override
    public int compare(Dish o1, Dish o2) {
        return o1.getDishCategory().getName().compareTo(o2.getDishCategory().getName());
    }
}
