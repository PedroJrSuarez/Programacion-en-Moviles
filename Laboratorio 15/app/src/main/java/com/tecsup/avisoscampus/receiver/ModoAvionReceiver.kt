package com.tecsup.avisoscampus.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Escucha el modo avión. onReceive es corto: solo avisa por el callback. */
class ModoAvionReceiver(
    private val alCambiar: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            alCambiar(intent.getBooleanExtra("state", false))
        }
    }
}
