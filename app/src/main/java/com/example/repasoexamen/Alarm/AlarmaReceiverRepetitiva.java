package com.example.repasoexamen.Alarm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.repasoexamen.R;

public class AlarmaReceiverRepetitiva extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmaReceiverRep", "La alarma repetitiva se ha activado");

        // Verificar el permiso POST_NOTIFICATIONS (para Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("AlarmaReceiverRep", "El permiso POST_NOTIFICATIONS no está concedido");
            return; // Salir si el permiso no está concedido
        }

        // Crear el canal de notificación si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    "mi_canal_repetitiva",
                    "Canal de Alarmas Repetitivas",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal.setDescription("Canal para notificaciones de alarmas repetitivas");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(canal);
            }
        }

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "mi_canal_repetitiva")
                .setSmallIcon(R.mipmap.ic_launcher) // Usa un ícono válido
                .setContentTitle("Alarma Repetitiva Activada")
                .setContentText("La alarma repetitiva programada se ha activado.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}



