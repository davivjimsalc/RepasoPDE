package com.example.repasoexamen.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repasoexamen.R;

public class MainActivity extends AppCompatActivity {

    private TextView n1, n2, Resultado;
    private CheckBox cbSumar, cbRestar;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        Resultado = findViewById(R.id.Resultado);
        cbSumar = findViewById(R.id.cbSumar);
        cbRestar = findViewById(R.id.cbRestar);
        imagen = findViewById(R.id.imagen);
    }

    public void operacion(View view){
        int resultado = 0;
        if(cbSumar.isChecked() && !cbRestar.isChecked()){
            resultado = Integer.parseInt(n1.getText().toString()) + Integer.parseInt(n2.getText().toString());
        }else if(cbRestar.isChecked() && !cbSumar.isChecked()){
            resultado = Integer.parseInt(n1.getText().toString()) - Integer.parseInt(n2.getText().toString());
        }else if(cbSumar.isChecked() && cbRestar.isChecked()){
            String mensaje1 = "Ambos checkbox no pueden estar marcados";
            Toast.makeText(this, mensaje1, Toast.LENGTH_LONG).show();
        }else{
            String mensaje2 = "No hay ninguna opcion marcada";
            Toast.makeText(this, mensaje2, Toast.LENGTH_SHORT).show();
        }
        Resultado.setText(String.valueOf(resultado));
        Log.d("Operacion", "Operacion hecha");
    }

    public void imagen(View view){
        if(imagen.getVisibility() == View.VISIBLE){
            imagen.setVisibility(View.GONE);
        }else{
            imagen.setVisibility(View.VISIBLE);
        }
    }

    public void irActivity2 (View view){
        Intent intent = new Intent(MainActivity.this, Activity2.class);

        int resultadoEnviar = Integer.parseInt(Resultado.getText().toString());

        intent.putExtra("Resultado", resultadoEnviar);
        startActivity(intent);
    }
}