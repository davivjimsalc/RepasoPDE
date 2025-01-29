package com.example.repasoexamen.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.repasoexamen.Dao.ResultadoDao;
import com.example.repasoexamen.Database.AppDatabase;
import com.example.repasoexamen.Entities.ResultadoEntity;
import com.example.repasoexamen.R;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    private EditText etNumero, etNuevoNumero;
    private ResultadoDao resultadoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNumero = findViewById(R.id.etNumero);
        etNuevoNumero = findViewById(R.id.etNuevoNumero);


        AppDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "resultados-db"
        ).allowMainThreadQueries().build();
        resultadoDao = database.resultadoDao();

        // Recibir la lista de resultados
        ArrayList<ResultadoEntity> listaResultados = (ArrayList<ResultadoEntity>) getIntent().getSerializableExtra("listaResultados");

        if (listaResultados != null && !listaResultados.isEmpty()) {
            //Si la lista no esta vacia se puede hacer algo aqui, en este caso solo Log
            Log.d("DatabaseActivity", "Resultados recibidos: " + listaResultados.size());
        } else {
            Toast.makeText(this, "No se recibieron resultados", Toast.LENGTH_SHORT).show();
        }
    }

    public void volver(View view){
        finish();
    }

    public void bucarNumero(View view) {
        // Validar que el campo no esté vacío
        if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un número", Toast.LENGTH_SHORT).show();
            return;
        }

        int numero = Integer.parseInt(etNumero.getText().toString());

        // Buscar el resultado en la base de datos
        ResultadoEntity resultado = resultadoDao.buscarResultadoPorNumero(numero);

        if (resultado != null) {
            Toast.makeText(this, "Resultado encontrado: " + resultado.getResultado(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Resultado no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarNumero(View view) {
        // Validar que el campo no esté vacío
        if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un número", Toast.LENGTH_SHORT).show();
            return;
        }

        int numero = Integer.parseInt(etNumero.getText().toString());

        // Buscar el resultado en la base de datos
        ResultadoEntity resultado = resultadoDao.buscarResultadoPorNumero(numero);

        if (resultado != null) {
            // Eliminar el resultado si existe
            resultadoDao.eliminarResultado(resultado);
            Toast.makeText(this, "Resultado eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Resultado no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarNumero(View view) {
        // Validar que los campos no estén vacíos
        if (etNumero.getText().toString().isEmpty() || etNuevoNumero.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa ambos números", Toast.LENGTH_SHORT).show();
            return;
        }

        int numeroActual = Integer.parseInt(etNumero.getText().toString());
        int nuevoNumero = Integer.parseInt(etNuevoNumero.getText().toString());

        // Buscar el resultado en la base de datos
        ResultadoEntity resultado = resultadoDao.buscarResultadoPorNumero(numeroActual);

        if (resultado != null) {
            // Actualizar el resultado
            resultado.setResultado(nuevoNumero);
            resultadoDao.actualizarResultado(resultado);

            Toast.makeText(this, "Resultado actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Resultado no encontrado", Toast.LENGTH_SHORT).show();
        }
    }


}