
/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                      Recibidor de broadcast de detención
:*
:*  Archivo     : ReiniciadorServicio.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Recibidor de Broadcast, su función es capturar los broadcast que emite
:*  el servicio al ser detenido para reiniciarlo.
:*
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReiniciadorServicio extends BroadcastReceiver {

    /*
     * Método que atrapa el broadcast e inicia el servicio nuevamente.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("INTERRUPCION", "Alguien ha detenido el servicio!");
        DeteccionService deteccionService = new DeteccionService(context);
        Intent inicioServicio = new Intent(context, DeteccionService.class);
        context.startService(inicioServicio);
        Log.i("INTERRUPCION", "Servicio reiniciado exitosamente");
        //Toast.makeText(context, "Reiniciando el servicio", Toast.LENGTH_LONG).show();
    }
}
