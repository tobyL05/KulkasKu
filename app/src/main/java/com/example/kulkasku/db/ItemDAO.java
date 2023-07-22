package com.example.kulkasku.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;

import java.util.List;

@Dao
public interface ItemDAO {
    @Insert
    void insertAll(Item... items);

    @Query("SELECT * FROM item WHERE expiry_date > :TodaysDate")
    public List<Item> GetTodaysItem(String TodaysDate);

    @Query("SELECT * FROM item WHERE expiry_date == :expInp AND item_name == :itemInp")
    Item getSpecificItem(String itemInp,String expInp);

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Delete
    void deleteItems(Item... items);
}
