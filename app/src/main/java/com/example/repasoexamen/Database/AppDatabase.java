package com.example.repasoexamen.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.repasoexamen.Dao.ResultadoDao;
import com.example.repasoexamen.Entities.ResultadoEntity;

@Database(entities = {ResultadoEntity.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ResultadoDao resultadoDao();
}
