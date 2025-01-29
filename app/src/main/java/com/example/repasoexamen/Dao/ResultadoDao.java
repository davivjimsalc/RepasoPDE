package com.example.repasoexamen.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.repasoexamen.Entities.ResultadoEntity;

import java.util.List;

@Dao
public interface ResultadoDao {
    @Insert
    void insertarResultado(ResultadoEntity resultado);

    @Update
    void actualizarResultado(ResultadoEntity resultado);

    @Delete
    void eliminarResultado(ResultadoEntity resultado);

    @Query("SELECT * FROM ResultadoEntity")
    List<ResultadoEntity> obtenerTodosLosResultados();

    @Query("SELECT * FROM ResultadoEntity WHERE resultado = :resultado LIMIT 1")
    ResultadoEntity buscarResultadoPorNumero(int resultado);
}
