package com.tecsup.avisoscampus.notificacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.MainActivity
import com.tecsup.avisoscampus.R
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import com.tecsup.avisoscampus.receiver.ACCION_POSPONER
import com.tecsup.avisoscampus.receiver.EXTRA_ID_AVISO
import com.tecsup.avisoscampus.receiver.PosponerReceiver

object Notificador {
    const val CANAL = "avisos"
    const val CANAL_URGENTE = "urgentes" // Fase 2

    /** Crea los canales (Android 8+): Avisos (normal) y Urgentes (emergente). */
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            val canalNormal = NotificationChannel(CANAL, "Avisos", NotificationManager.IMPORTANCE_DEFAULT)
            val canalUrgente = NotificationChannel(CANAL_URGENTE, "Urgentes", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Notificaciones urgentes y recordatorios emergentes"
                enableVibration(true)
                enableLights(true)
            }
            manager.createNotificationChannel(canalNormal)
            manager.createNotificationChannel(canalUrgente)
        }
    }

    fun tienePermiso(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < 33) return true
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /** Muestra la notificación. Devuelve false si no hay permiso. */
    @SuppressLint("MissingPermission")
    fun mostrar(context: Context, aviso: Aviso): Boolean {
        if (!tienePermiso(context)) return false

        val esRecordatorio = aviso.origen == Origen.RECORDATORIO
        val canal = if (esRecordatorio) CANAL_URGENTE else CANAL

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendiente = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, canal)
            .setSmallIcon(R.drawable.ic_notificacion)
            .setContentTitle(aviso.titulo)
            .setContentText(aviso.texto)
            .setPriority(
                if (esRecordatorio) NotificationCompat.PRIORITY_HIGH
                else NotificationCompat.PRIORITY_DEFAULT
            )
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            // Fase 2: texto largo al expandir
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("${aviso.texto}\n\nOrigen: ${aviso.origen.texto} · ${aviso.hora}")
            )
            .setContentIntent(pendiente)
            .setAutoCancel(true)

        // Fase 2: botón "Posponer 10 s" (solo en recordatorios)
        if (esRecordatorio) {
            val posponer = Intent(context, PosponerReceiver::class.java).apply {
                action = ACCION_POSPONER
                putExtra(EXTRA_ID_AVISO, aviso.id)
            }
            val pendientePosponer = PendingIntent.getBroadcast(
                context,
                aviso.id, // requestCode distinto por aviso
                posponer,
                PendingIntent.FLAG_IMMUTABLE // inmutable: no hace falta modificarlo
            )
            builder.addAction(R.drawable.ic_notificacion, "Posponer 10 s", pendientePosponer)
        }

        NotificationManagerCompat.from(context).notify(aviso.id, builder.build())
        return true
    }
}
