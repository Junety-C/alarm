package cn.junety.alarm.sender.smartqq.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caijt on 2017/3/28.
 */
public class Category {
    private int index;

    private int sort;

    private String name;

    private List<Friend> friends = new ArrayList<>();

    public void addFriend(Friend friend) {
        this.friends.add(friend);
    }

    @Override
    public String toString() {
        return "Category{"
                + "index=" + index
                + ", sort=" + sort
                + ", name='" + name + '\''
                + ", friends=" + friends
                + '}';
    }

    public static Category defaultCategory() {
        Category category = new Category();
        category.setIndex(0);
        category.setSort(0);
        category.setName("我的好友");
        return category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
