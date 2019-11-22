package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String[] contactos = new String[] {"Jessica", "Monica", "Ivan", "Emergencia - 911"};

    public void btnEmergenciaOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LLAMAR A: ");
        builder.setSingleChoiceItems(contactos, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void btnMedicamentosOnClick(View view) {

    }

    public void btnConfiguracionOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
        startActivity(intent);
    }
}
