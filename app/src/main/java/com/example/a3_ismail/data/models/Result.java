package com.example.a3_ismail.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results")
public class Result {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String date;
    private int score;

    public Result(String date, int score) {
        this.date = date;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "date='" + date + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }
}
