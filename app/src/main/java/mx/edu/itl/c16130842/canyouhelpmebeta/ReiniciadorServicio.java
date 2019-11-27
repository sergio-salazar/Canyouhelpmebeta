package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReiniciadorServicio extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("INTERRUPCION", "Alguien ha detenido el servicio!");
        DeteccionService deteccionService = new DeteccionService(context);
        Intent inicioServicio = new Intent(context, DeteccionService.class);
        context.startService(inicioServicio);
        Log.i("INTERRUPCION", "Servicio reiniciado exitosamente");
        Toast.makeText(context, "Reiniciando el servicio", Toast.LENGTH_LONG).show();
    }
}
