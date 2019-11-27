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

        //Si se da click en el botón de nuevo, se abrirá la pantalla para capturar un nuevo
        //medicamentos
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notificarCambios();

    }

    @Override
    protected void onDestroy() {
        Medicamentos.setMedicamentosCargados(false);
        super.onDestroy();
    }
}
