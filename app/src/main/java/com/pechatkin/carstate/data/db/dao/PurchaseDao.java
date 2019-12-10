package com.pechatkin.carstate.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pechatkin.carstate.data.db.entity.Purchase;

import java.util.List;

@Dao
public interface PurchaseDao {

    @Query("SELECT * FROM purchases_table")
    List<Purchase> getAllPurchases();

    @Insert
    void insert(Purchase purchase);

    @Update
    void update(Purchase purchase);

    @Delete
    void delete(Purchase purchase);

    @Query("DELETE FROM purchases_table")
    void deleteAll();



}
