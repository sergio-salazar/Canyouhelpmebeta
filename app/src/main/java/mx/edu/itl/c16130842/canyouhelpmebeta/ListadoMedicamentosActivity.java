package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListadoMedicamentosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //CÓDIGO PARA INICIAR LA CAPTURA DE MEDICAMENTO (REQUEST CODE)
    private final int codCaptura = 123;
    private final int operacion=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_medicamentos);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListadoMedicamentosActivity.this, CapturaMedicamentosActivity.class);
                i.putExtra("ACT", operacion);
                i.putExtra("POS", 0);
                startActivityForResult(i, codCaptura);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorRecyclerView(ListadoMedicamentosActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(codCaptura==requestCode && resultCode==RESULT_OK) {
            adapter.notifyDataSetChanged();
            //Snackbar.make(R.layout.)
            //Snackbar.make(this, "Medicamento guardado", Snackbar.LENGTH_LONG).show();
        }
    }
}
