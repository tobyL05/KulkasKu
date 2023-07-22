package com.example.kulkasku.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class},version=2)
public abstract class ItemDB extends RoomDatabase {
    public abstract ItemDAO itemDAO();
}
