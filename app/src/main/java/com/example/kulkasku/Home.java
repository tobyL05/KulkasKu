package com.example.kulkasku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.kulkasku.db.Item;
import com.example.kulkasku.db.ItemDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private Button add_btn;
    private ItemDB db;
    private List<Item> itemsList;
    private ListView lv;
    private ArrayList<String> itemNames, expiryDates;

    @Override
    protected void onResume(){
        super.onResume();
        initData();
        initList();
        checkItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = Room.databaseBuilder(getApplicationContext(), ItemDB.class, "items-list").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initList();
        checkItems();

        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), inputfood.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void initList(){
        lv = findViewById(R.id.foodsList);
        CustomBaseAdapter cba = new CustomBaseAdapter(getApplicationContext(), itemNames, expiryDates);
        lv.setAdapter(cba);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Home.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + itemNames.get(pos));
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Delete", "Removing item: " + itemNames.get(pos) + " " + expiryDates.get(pos));
                        itemNames.remove(pos);
                        expiryDates.remove(pos);
                        db.itemDAO().deleteItems(itemsList.get(pos));
                        for(Item item:db.itemDAO().getAll()){
                            Log.d("Database",item.itemName + " " + item.expiryDate);
                        }
                        cba.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
    }
    private void initData() {
        itemsList = db.itemDAO().getAll();
        itemNames = new ArrayList<>();
        expiryDates = new ArrayList<>();
        for (Item i : itemsList) {
            itemNames.add(i.itemName);
            expiryDates.add(i.expiryDate);
            Log.d("Database", i.itemName + " " + i.expiryDate);
        }
    }

    private void checkItems(){
        Log.d("Notif","Checking item dates");
        for(int i = 0;i < itemNames.size();i++){
            String[] dateArr = expiryDates.get(i).split("/");
            LocalDate expDateObj = LocalDate.of(Integer.parseInt(dateArr[2]),Integer.parseInt(dateArr[1]),Integer.parseInt(dateArr[0]));
            LocalDate currDate = LocalDate.now();
            if(expDateObj.isBefore(currDate)){
                initNotif(itemNames.get(i),expiryDates.get(i));
            }
        }
    }

    private void initNotif(String itemName, String date) {
        Log.d("Notif","Launching notif");
//        Intent ii = new Intent(getApplicationContext(),Home.class);
//        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,ii,0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notification")
//                .setSmallIcon(R.drawable.ic_plus)
//                .setContentTitle("Expiry Reminder")
//                .setContentText(itemName + " will expire on " + date + " !")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pi);
//
//        CharSequence name = getString(R.string.channel_name);
//        String description = getString(R.string.channel_description);
//        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//        NotificationChannel channel = new NotificationChannel("notification", name, importance);
//        channel.setDescription(description);
//        // Register the channel with the system; you can't change the importance
//        // or other notification behaviors after this
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//        notificationManager.notify(0, builder.build());

    }
}