package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        getSupportActionBar().hide();
    }

    public void btnConfigurarContactosOnClick(View view) {
        Intent intent = new Intent(ConfiguracionActivity.this, ContactosActivity.class);
        startActivity(intent);
    }

    public void btnConfigurarMensajeOnClick(View view) {

    }

}
