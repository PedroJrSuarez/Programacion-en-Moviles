package com.tecsup.avisoscampus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

const val ACCION_POSPONER = "com.tecsup.avisoscampus.POSPONER"
const val EXTRA_ID_AVISO = "id_aviso"

/** Receptor declarado en el Manifest (no exportado). onReceive es corto. */
class PosponerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACCION_POSPONER) return
        val id = intent.getIntExtra(EXTRA_ID_AVISO, -1)
        if (id != -1) NotificationManagerCompat.from(context).cancel(id)
        AccionesNotificacion.pedirPosponer()
    }
}
