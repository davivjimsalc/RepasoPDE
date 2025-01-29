package com.example.repasoexamen.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class ResultadoEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int resultado;

    public ResultadoEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}


