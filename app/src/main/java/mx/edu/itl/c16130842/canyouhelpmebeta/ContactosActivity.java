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

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorRecyclerViewContactos adapter;
    ContactoSQLiteHelper contacto;
    public TextView nombre;
    public TextView telefono;

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
