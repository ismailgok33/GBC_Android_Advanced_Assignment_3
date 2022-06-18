package com.example.a3_ismail.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.a3_ismail.data.models.Result;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ResultDao {

    @Query("Select * from results")
    public List<Result> getResults();

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    public void insert(Result result);
}
