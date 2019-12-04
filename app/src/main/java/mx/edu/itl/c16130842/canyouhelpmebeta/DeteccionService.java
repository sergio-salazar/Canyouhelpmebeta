/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                      Servicio detector de caídas
:*
:*  Archivo     : CaidasRecibidor.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Servicio en segundo plano detector de caídas. Emite un broadcast cada 
:*  vez que es destruido para que sea posible reiniciarlo. El monitoreo de sensores se 
:*  realiza mediante un Hilo que siempre está ejecutandose y se registra como escuchador
:*  en el SensorManager.
:*
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

/*
 * Clase que hereda de Service y permite detectar caídas. Tiene el hilo que
 * efectúa la lógica como clase anidada.
 */
public class DeteccionService extends Service {
    // Contexto recibido durante su creación, es necesario para emitir los 
    // broadcast. 
    Context context;

    /*
     * Método lanzador de broadcast en caso de que el servicio sea removido.
     * El broadcast es captado por ReiniciadorServicio.java, que es el 
     * encargado de volver a iniciar el servicio.
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.action.SERVICIO_DETENIDO");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);
    }

    // Constructor por defecto requerido por el compilador
    public DeteccionService() {}

    // Constructor que recibe un contexto necesario para el broadcast
    public DeteccionService(Context context) {
        super();
        this.context = context;
    }

    /*
     * Método requerido por el compilador, null indica que el servicio
     * correra de forma independiente
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * Método llamado por el sistema Android para reiniciar el servicio, 
     * crea un nuevo hilo encargado de la detección y lo inicia. 
     * Regresa START-STICKY para indicar que el servicio no debe ser
     * destruido.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread deteccion = new DeteccionCaidas();
        deteccion.start("com.action.SERVICIO_DETENIDO");
        return START_STICKY;
    }


    /*
     * Método llamado por el sistema de Android en caso de que el servicio
     * sea destruido. Lanza el broadcast para reiniciarlo.
     */
    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.action.SERVICIO_DETENIDO");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);
    }

    
    /*
     * Clase interna detectora de caídas, es un hilo que procesa la lógica
     * y se registra como escuchadora en el Sensor Manager.
     */
    class DeteccionCaidas extends Thread implements SensorEventListener {
        // Referencias al sensorManager y al tipo de sensor que usaremos
        SensorManager sensorManager;
        Sensor acelerometro;
        private final String TAG="SENSORES";

        /*
         * Método run del hilo. Obtiene la referencia al acelerómetro y
         * registra al hilo como escuchador de los sensores. 
         * El while(true) lo ejecuta permanentemente.
         */
        @Override
        public void run() {
            //Obtenemos referencias a los sensores
            Log.i(TAG, "Intentando conectarnos con el servicio de sensores");
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            Log.i(TAG, "Registrados exitosamente");
            while (true) {}
        }

        // Constructor por defecto requerido
        public DeteccionCaidas() {}

        /* 
         * Método que es llamado por el sistema Android cada vez que se detecta un 
         * cambio en los sensores. Implementa la fórmula (X^2+Y^2+Z^2)^1/2. Se utiliza
         * precisión de dos digitos y si está en cierto rango se lanza el broadcast
         * de caida.
         */
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //Log.i(TAG, "-----------------------------------------------------");
            //Log.i(TAG, "Nuevo valor de X: "+sensorEvent.values[0]);
            //Log.i(TAG, "Nuevo valor de Y: "+sensorEvent.values[1]);
            //Log.i(TAG, "Nuevo valor de Z: "+sensorEvent.values[2]);
            double loX = sensorEvent.values[0];
            double loY = sensorEvent.values[1];
            double loZ = sensorEvent.values[2];

            double lectorAceleracion = Math.sqrt(Math.pow(loX, 2)
                    + Math.pow(loY, 2)
                    + Math.pow(loZ, 2));

            DecimalFormat precision = new DecimalFormat("0.00");
            double ldAccRound = Double.parseDouble(precision.format(lectorAceleracion));

            if (ldAccRound > 0.3d && ldAccRound < 0.5d) {
                Intent i = new Intent();
                i.setAction("com.action.CAIDA");
                i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(i);
            }
        }

        // Método solicitado por el compilador
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
