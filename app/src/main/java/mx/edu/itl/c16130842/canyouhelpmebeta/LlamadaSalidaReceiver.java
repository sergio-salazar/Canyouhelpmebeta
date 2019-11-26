package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class LlamadaSalidaReceiver extends BroadcastReceiver {
    static long tiempoInicio, tiempoFin, duracionLlamada;
    String numero;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "intent get data : " + intent.getData(), Toast.LENGTH_LONG).show();
        String action = intent.getAction();
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Toast.makeText(context, "Phone State : " + state, Toast.LENGTH_LONG).show();

        if (state == null) {
            numero = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Toast.makeText(context, "Out going Phone number : " + numero, Toast.LENGTH_LONG).show();
        }

        if (action.equalsIgnoreCase("android.intent.action.PHONE_STATE")) {
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                tiempoInicio = System.currentTimeMillis();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                tiempoFin = System.currentTimeMillis();
            }
            duracionLlamada = tiempoFin - tiempoInicio;
        }
    }
}