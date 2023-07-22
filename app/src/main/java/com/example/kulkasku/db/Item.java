package com.example.kulkasku.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
   @PrimaryKey(autoGenerate = true)
    public int uid;

   @ColumnInfo(name = "item_name")
    public String itemName;

   @ColumnInfo(name = "expiry_date")
    public String expiryDate;

   public Item(String itemName, String expiryDate){
       this.itemName = itemName;
       this.expiryDate = expiryDate;
   }

}
