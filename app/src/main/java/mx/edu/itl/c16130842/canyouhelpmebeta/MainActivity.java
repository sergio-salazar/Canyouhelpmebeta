package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ContactoSQLiteHelper contactos = new ContactoSQLiteHelper(MainActivity.this);
    public String[] nombres;

    private static final long START_TIME_IN_MILLIS = 5000;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    private Intent inicioServicio;
    private static int CODIGO_SOLICITUD_PERMISO = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        nombres = new String[contactos.mostrarContactos().size()];
        for (int i = 0; i < contactos.mostrarContactos().size(); i++) {
            nombres[i] = contactos.mostrarContactos().get(i).getNombre();
        }
        //nombres[nombres.length] = "911";

        //DESDE AQUI INICIA EL SERVICIO PASARLO AL MAIN ACTIVITY
        DeteccionService deteccionService = new DeteccionService(getContext());
        inicioServicio = new Intent(getContext(), DeteccionService.class);
        if(!estaCorriendoServicio(deteccionService.getClass())) {
            startService(inicioServicio);
        }

        //Registrando el recibidor de broadcast
        IntentFilter intentFilter = new IntentFilter("com.action.SERVICIO_DETENIDO");
        BroadcastReceiver reiniciadorServicio = new ReiniciadorServicio();
        registerReceiver(reiniciadorServicio, intentFilter);

        //Registrando el recibidor de broadcast de caida
        IntentFilter filterLlamadas = new IntentFilter("com.action.CAIDA");
        BroadcastReceiver caidasRecibidor = new CaidasRecibidor();
        registerReceiver(caidasRecibidor, filterLlamadas);

        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if(permiso != PackageManager.PERMISSION_GRANTED) {
            permisoLlamadas();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nombres = new String[contactos.mostrarContactos().size()];
        for (int i = 0; i < contactos.mostrarContactos().size(); i++) {
            nombres[i] = contactos.mostrarContactos().get(i).getNombre();
        }
    }

    public void btnEmergenciaOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LLAMAR A: ");
        builder.setItems(nombres, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactos.mostrarContactos().get(which).getTelefono()));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                //countDownTimer.cancel();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //countDownTimer.cancel();
                dialog.dismiss();
            }
        }).create().show();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:911"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }
        }, 5000);*/
        //startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                countDownTimer.start();
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:911"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }
        };
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    //COPIAR TODOS LOS DEMAS METODOS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            permisoLlamadas();
        }
    }

    private void permisoLlamadas() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE},
                CODIGO_SOLICITUD_PERMISO);
    }

    private boolean estaCorriendoServicio(Class<?> claseServicio) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo servicio : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (claseServicio.getName().equals(servicio.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public Context getContext() {
        return this;
    }

    public void btnMedicamentosOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ListadoMedicamentosActivity.class);
        startActivity(intent);
    }

    public void btnConfiguracionOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
        startActivity(intent);
    }
}