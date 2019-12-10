package com.pechatkin.carstate.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pechatkin.carstate.data.db.dao.PurchaseDao;
import com.pechatkin.carstate.data.db.entity.Purchase;

@Database(entities = {Purchase.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PurchaseDao mPurchaseDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.class.getName())
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
