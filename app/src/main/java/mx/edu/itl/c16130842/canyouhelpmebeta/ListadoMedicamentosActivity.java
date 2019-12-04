/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                      Activity Listador de Medicamentos
:*
:*  Archivo     : ListadoMedicamentosActivity.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Activity entre cuyas funciones está el crear el recyclerView para el
:*  despliegue de medicamentos, interactúa con el activity CapturaMedicamentosActivity
:*  mediante un recyclerView
:*
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListadoMedicamentosActivity extends AppCompatActivity {

    //CÓDIGO PARA INDICAR LA CAPTURA DE UN MEDICAMENTO EN EL StartActivityForResults, que llama
    //a CapturaMedicamentosActivity
    private final int CODIGO_CAPTURA = 123;
    private final int OPERACION_CAPTURA = 1;

    //CUESTIONES DEL ADAPTADOR AL RECYCLERVIEW
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adaptador_RecyclerView_Medicamentos adapter;

    /*
     * Sobreescritura del método onCreate. Se crea e inicializa el recyclerView
     * y se configura el onClickListener para capturar nuevos medicamentos. En caso
     * de captura de nuevos medicamentos, se pasa el código de captura en el Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_medicamentos);

        getSupportActionBar().hide();

        //Cargando los medicamentos
        Medicamentos.cargarMedicamentos(ListadoMedicamentosActivity.this);
        Medicamentos.setMedicamentosCargados(true);

        //Creando el RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adaptador_RecyclerView_Medicamentos(ListadoMedicamentosActivity.this);
        recyclerView.setAdapter(adapter);

        // Si se da click en el botón de nuevo, se abrirá la pantalla para capturar un nuevo
        // medicamentos
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListadoMedicamentosActivity.this, CapturaMedicamentosActivity.class);
                i.putExtra("OP", OPERACION_CAPTURA);
                i.putExtra("POS", -1);
                startActivityForResult(i, CODIGO_CAPTURA);
            }
        });

        //Notificamos cambios en los datos
        adapter.notifyDataSetChanged();
    }

    // Al regresar de la captura, notificamos los cambios para que el adaptador se actualice
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notificarCambios();
    }

    // onDestroy, indicamos que no hay medicamentos cargados en memoria
    @Override
    protected void onDestroy() {
        Medicamentos.setMedicamentosCargados(false);
        super.onDestroy();
    }
}
