/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Activity de configuración
:*
:*  Archivo     : ConfiguracionActivity.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Activity que solamente actúa como "intermediario" entre nuestras pantallas de
:*  configuración
:*  de teléfono. 
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfiguracionActivity extends AppCompatActivity {

    /*
     * Método estándar que se tiene que sobreescribir, infla el layout y oculta
     * la ActioBar para hacer más fácil la vista.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        getSupportActionBar().hide();
    }
    
    // En caso de click en este método, iniciamos el Activity de selección de contactos
    public void btnConfigurarContactosOnClick(View view) {
        Intent intent = new Intent(ConfiguracionActivity.this, ContactosActivity.class);
        startActivity(intent);
    }

    // En caso de click en el otro botón, abrimos el activity para personalziar el SMS
    public void btnConfigurarMensajeOnClick(View view) {
        Intent intent = new Intent(ConfiguracionActivity.this, SMSActivity.class);
        startActivity(intent);
    }

}
