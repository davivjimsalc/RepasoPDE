package com.example.repasoexamen.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repasoexamen.R;

public class ContadorActivity extends AppCompatActivity {

    private TextView tvContador;
    private ContadorAsyncTask contadorAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvContador = findViewById(R.id.tvContador);

        int resultado = getIntent().getIntExtra("Resultado", 0);
        tvContador.setText(String.valueOf(resultado));
    }

    public void volver(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void start(View view) {
        if (contadorAsyncTask == null || contadorAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            int tiempoInicial = Integer.parseInt(tvContador.getText().toString());
            contadorAsyncTask = new ContadorAsyncTask(tiempoInicial);
            contadorAsyncTask.execute();
        }
    }

    public void detener(View view){
        if(contadorAsyncTask != null){
            contadorAsyncTask.detener();
        }
    }

    public void resetear(View view){
        if(contadorAsyncTask != null){
            contadorAsyncTask.resetear();
        }
    }

    private class ContadorAsyncTask extends AsyncTask<Void, Integer, Void>{
        private int tiempoInicial, tiempoActual;
        private boolean detener;

        public ContadorAsyncTask(int tiempoInicial) {
            this.tiempoInicial = tiempoInicial;
            this.tiempoActual = tiempoInicial;
            this.detener = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(tiempoActual > 0 && !detener){
                try{
                    Thread.sleep(1000);
                    tiempoActual--;
                    publishProgress(tiempoActual);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            if (tiempoActual == 0 && !detener) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("mensaje", "El contador ha llegado a 0");
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            tvContador.setText(String.valueOf(values[0]));
        }

        public void detener(){
            detener = true;
        }

        public void resetear() {
            detener = true;
            tiempoActual = tiempoInicial;
            publishProgress(tiempoInicial);
        }
    }
}