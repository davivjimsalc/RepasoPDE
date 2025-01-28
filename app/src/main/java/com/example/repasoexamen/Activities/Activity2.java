package com.example.repasoexamen.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repasoexamen.Alarm.AlarmaReceiver;
import com.example.repasoexamen.Alarm.AlarmaReceiverRepetitiva;
import com.example.repasoexamen.R;

public class Activity2 extends AppCompatActivity {

    public TextView tvResultado;
    private static final String CHANNEL_ID = "mi_canal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvResultado = findViewById(R.id.tvResultado);

        int resultado = getIntent().getIntExtra("Resultado", 0);
        tvResultado.setText(String.valueOf(resultado));

        crearCanalNotificacion();
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Item1){
            Toast.makeText(this, "Opcion 1", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContadorActivity.class);
            intent.putExtra("Resultado", Integer.parseInt(tvResultado.getText().toString()));
            startActivityForResult(intent,1);
            return true;
        }else if(id ==  R.id.Item2){
            Toast.makeText(this, "Opcion 2", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Acerca de")
                    .setMessage("MiAplicación - Inventario de Productos\n" +
                            "Versión: 1.0.0\n\n" +
                            "Aplicación para gestionar productos, permitiendo añadir, editar y eliminar información.\n\n" +
                            "Desarrollado por: David Jimenez\n" +
                            "Contacto: dsalcjim@myuax.com\n")
                    .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }else if(id == R.id.Item3){
            Toast.makeText(this, "Opcion 3", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String mensaje = data.getStringExtra("mensaje");
            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    public void terminar(View view){
        finish();
    }

    private void crearCanalNotificacion(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence nombre = "Canal ejemplo";
            String descripcion = "Canal de ejemplo para notificaicones";
            int importacia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID,nombre, importacia);
            canal.setDescription(descripcion);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }

    public void mostrarNotificacion(View view) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Verificar si el permiso está otorgado (solo necesario en Android 13 o superior)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Si no se tiene el permiso, solicitarlo al usuario
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                return; // Salir hasta que el usuario conceda el permiso
            }
        }

        // Crear un intent para abrir la actividad al pulsar la notificación
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Asegúrate de usar un icono válido
                .setContentTitle("Mi Notificación")
                .setContentText("Este es el contenido de la notificación.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) { // Verifica el código de solicitud
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes mostrar la notificación
                Toast.makeText(this, "Permiso concedido para notificaciones", Toast.LENGTH_SHORT).show();
            } else {
                // Permiso denegado, muestra un mensaje al usuario
                Toast.makeText(this, "Permiso denegado para notificaciones", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void programarAlarma(View view) {
        // Verificar si se pueden programar alarmas exactas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 o superior
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "No se pueden programar alarmas exactas. Concede el permiso en la configuración.", Toast.LENGTH_LONG).show();
                // Redirigir al usuario a la configuración
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return; // Salir hasta que el permiso sea concedido
            }
        }

        // Crear un intent para el BroadcastReceiver
        Intent intent = new Intent(this, AlarmaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Configurar el AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Configurar la hora para que la alarma se dispare en 10 segundos
        long tiempoEnMillis = System.currentTimeMillis() + 10000; // 10 segundos desde ahora

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoEnMillis, pendingIntent);
            Toast.makeText(this, "Alarma programada para dentro de 10 segundos", Toast.LENGTH_SHORT).show();
        }
    }


    public void programarAlarmaRepetitiva(View view) {
        // Crear un intent para el BroadcastReceiver
        Intent intent = new Intent(this, AlarmaReceiverRepetitiva.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Configurar el AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Configurar la hora para que la alarma se dispare por primera vez
        long tiempoInicial = System.currentTimeMillis() + 1000; // 10 segundos desde ahora
        long intervalo = AlarmManager.INTERVAL_FIFTEEN_MINUTES; // Se repetirá cada 15 minutos

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiempoInicial, intervalo, pendingIntent);
            Toast.makeText(this, "Alarma repetitiva programada", Toast.LENGTH_SHORT).show();
        }
    }


    public void cancelarAlarmaRepetitiva(View view) {
        Intent intent = new Intent(this, AlarmaReceiverRepetitiva.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarma repetitiva cancelada", Toast.LENGTH_SHORT).show();
        }
    }



}