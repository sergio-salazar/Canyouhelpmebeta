package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
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
    }

    public void btnEmergenciaOnClick(View view) {
        /*if (timerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LLAMAR A: ");
        builder.setItems(nombres, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactos.mostrarContactos().get(which).getTelefono()));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }
        }).create().show();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timerRunning = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("LLAMAR A: ");
                builder.setItems(nombres, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactos.mostrarContactos().get(which).getTelefono()));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED)
                            return;
                        countDownTimer.cancel();
                        timerRunning = false;
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                        timerRunning = false;
                        dialog.dismiss();
                    }
                }).create().show();
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:911"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }
        }.start();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void btnMedicamentosOnClick(View view) {
        /*Intent intent = new Intent(MainActivity.this, ListadoMedicamentosActivity.class);
        startActivity(intent);*/
    }

    public void btnConfiguracionOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
        startActivity(intent);
    }
}