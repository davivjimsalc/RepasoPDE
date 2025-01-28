package com.example.repasoexamen.Alarm;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.repasoexamen.R;

public class AlarmaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmaReceiver", "La alarma se ha activado");

        // Verificar el permiso POST_NOTIFICATIONS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("AlarmaReceiver", "El permiso POST_NOTIFICATIONS no está concedido");
            return; // Salir si el permiso no está concedido
        }

        // Crear el canal de notificación si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    "mi_canal",
                    "Canal de Alarmas",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal.setDescription("Canal para notificaciones de alarmas");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }
        }

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "mi_canal")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarma Activada")
                .setContentText("La alarma programada se ha activado.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100, builder.build());
    }
}





