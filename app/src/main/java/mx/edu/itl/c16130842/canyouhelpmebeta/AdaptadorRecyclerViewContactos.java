package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorRecyclerViewContactos extends RecyclerView.Adapter<AdaptadorRecyclerViewContactos.ViewHolder> {

    public ArrayList<Contacto> contactos;
    ContactoSQLiteHelper contacto;
    AlertDialog.Builder builder;

    public AdaptadorRecyclerViewContactos(ArrayList<Contacto> contactos) {
        this.contactos = contactos;
    }

    @Override
    public AdaptadorRecyclerViewContactos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_contacto, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        contacto = new ContactoSQLiteHelper(v.getContext());
        builder = new AlertDialog.Builder(v.getContext());

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerViewContactos.ViewHolder holder, int position) {
        holder.imagen.setImageResource(R.drawable.ic_account_circle_white_24dp);
        holder.nombre.setText(contactos.get(position).getNombre());
        holder.telefono.setText(contactos.get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView telefono;
        public ImageButton cerrar;

        public ViewHolder(final View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_llamada);
            nombre = itemView.findViewById(R.id.nombre_contacto);
            telefono = itemView.findViewById(R.id.telefono_contacto);
            cerrar = itemView.findViewById(R.id.boton_cerrar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), NuevoContactoActivity.class);
                    intent.putExtra("nombre", contactos.get(position).getNombre());
                    intent.putExtra("telefono", contactos.get(position).getTelefono());
                    intent.putExtra("editar", "1");
                    view.getContext().startActivity(intent);
                }
            });

            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    int position = getAdapterPosition();
                    builder.setTitle("ELIMINAR CONTACTO");
                    builder.setMessage("¿Desea eliminar a "+ contactos.get(position).getNombre() +" de sus contactos?");
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            contacto.eliminarContacto(contactos.get(position).getNombre(), contactos.get(position).getTelefono());
                            Toast.makeText(itemView.getContext(), "Se ha eliminado el contacto.", Toast.LENGTH_SHORT);
                        }
                    }).create().show();
                }
            });
        }
    }
}

