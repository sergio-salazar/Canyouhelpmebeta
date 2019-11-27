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

public class DeteccionService extends Service {
    Context context;

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.action.SERVICIO_DETENIDO");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);
    }

    public DeteccionService() {

    }

    public DeteccionService(Context context) {
        super();
        this.context = context;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread deteccion = new DeteccionCaidas();
        deteccion.start();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent(this, ServicioDetenidoBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    class DeteccionCaidas extends Thread implements SensorEventListener {
        SensorManager sensorManager;
        Sensor acelerometro;
        private final String TAG="SENSORES";

        @Override
        public void run() {
            //Obtenemos referencias a los sensores
            Log.i(TAG, "Intentando conectarnos con el servicio de sensores");
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            Log.i(TAG, "Registrados exitosamente");
            while (true) {
            }
        }

        public DeteccionCaidas() {

        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Log.i(TAG, "-----------------------------------------------------");
            Log.i(TAG, "Nuevo valor de X: "+sensorEvent.values[0]);
            Log.i(TAG, "Nuevo valor de Y: "+sensorEvent.values[1]);
            Log.i(TAG, "Nuevo valor de Z: "+sensorEvent.values[2]);
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

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}
