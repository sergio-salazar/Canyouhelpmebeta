/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Activity encargado del despliegue de los contactos
:*
:*  Archivo     : ContactosActivity.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Archivo JAVA encargado de manejar las interacciones con el 
:*  layout contactos: muestra contactos, permite capturar nuevos y eliminar
:*  contactos (máximo de tres).
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactosActivity extends AppCompatActivity {

    // Referencias necesarias para el RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorRecyclerViewContactos adapter;
    // Referencia a la clase manejadora de base de datos
    ContactoSQLiteHelper contacto;
    // Referencias a los TextView Nombre y Teléfono
    public TextView nombre;
    public TextView telefono;

    /*
     * Método onCreate: Inicializa la vista y da la opción de capturar más contactos
     * siempre y cuando el número de los actuales no sea superior de tres. Para ello
     * carga los contactos de la base de datos.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        recyclerView = findViewById(R.id.recycler_view_llamada);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContactoSQLiteHelper contactos = new ContactoSQLiteHelper(getApplicationContext());
        adapter = new AdaptadorRecyclerViewContactos(contactos.mostrarContactos());
        recyclerView.setAdapter(adapter);

        nombre = (TextView) findViewById(R.id.nombre_contacto);
        telefono = (TextView) findViewById(R.id.telefono_contacto);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        TextView txvContactos;
        txvContactos = (TextView) findViewById(R.id.txvContactos);

        if (adapter.contactos.size() == 0) {
            txvContactos.setText("No existen contactos registrados.");
        }
        if (adapter.contactos.size() >= 3) {
            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactosActivity.this, NuevoContactoActivity.class);
                intent.putExtra("nombre", "");
                intent.putExtra("telefono", "");
                intent.putExtra("editar", "0");
                startActivity(intent);
            }
        });
    }

    /*
     * Método para eliminar un contacto, es un botón de la aplicación. Avisa al usuario
     * antes de lo que está a punto de hacer. En caso de que se proceda con aceptar,
     * se llama al método correspondiente de eliminación en la BD.
     */
    public void btnCerrarOnClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ELIMINAR CONTACTO");
        builder.setMessage("¿Desea eliminar el contacto?");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contacto.eliminarContacto(nombre.getText().toString(), telefono.getText().toString());
                Toast.makeText(view.getContext(), "Se ha eliminado el contacto.", Toast.LENGTH_SHORT);

            }
        }).create().show();
    }

    /*
     * Método onResume: realiza las mismas tareas del OnCreate: carga los contactos
     * y da la oportunidad de capturar nuevos.
     */
    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.recycler_view_llamada);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContactoSQLiteHelper contactos = new ContactoSQLiteHelper(getApplicationContext());
        adapter = new AdaptadorRecyclerViewContactos(contactos.mostrarContactos());
        recyclerView.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (adapter.contactos.size() >= 3) {
            fab.hide();
        }

        TextView txvContactos;
        txvContactos = (TextView) findViewById(R.id.txvContactos);

        if (adapter.contactos.size() > 0) {
            txvContactos.setText("");
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactosActivity.this, NuevoContactoActivity.class);
                intent.putExtra("nombre", "");
                intent.putExtra("telefono", "");
                intent.putExtra("editar", "0");
                startActivity(intent);
            }
        });
    }
}
