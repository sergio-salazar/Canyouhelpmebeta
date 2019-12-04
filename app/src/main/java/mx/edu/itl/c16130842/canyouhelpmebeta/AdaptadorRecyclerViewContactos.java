
/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Clase encargada de la interacción con la base de datos
:*
:*  Archivo     : AdapterRecyclerView.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase que nos permite utilizar un recyclerView para el despliegue de 
:*  contactos. Consta del recyclerView y su clase anidada interna ViewHolder, que recibe
:*  los clicks hechos en las vistas reutilizadas. Necesita un arreglo de contactos, que viene
:*  del activity ContactosActivity.java
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
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
    // Arreglo en memoria de los contactos existentes
    public ArrayList<Contacto> contactos;
    // Referencia al manejador de BD de contactos
    ContactoSQLiteHelper contacto;
    // Referencia al constructor de diálogos de alerta
    AlertDialog.Builder builder;

    // Constructor por defecto, recibe un arreglo de contactos
    public AdaptadorRecyclerViewContactos(ArrayList<Contacto> contactos) {
        this.contactos = contactos;
    }

    /*
     * Método que crea todas las viewHolder necesarias, es necesaria su implementación.
     */
    @Override
    public AdaptadorRecyclerViewContactos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_contacto, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        contacto = new ContactoSQLiteHelper(v.getContext());
        builder = new AlertDialog.Builder(v.getContext());

        return viewHolder;
    }

    /*
     * Método para rellenar los campos necesarios de la vista que estaremos reciclando
     */
    @Override
    public void onBindViewHolder(AdaptadorRecyclerViewContactos.ViewHolder holder, int position) {
        holder.imagen.setImageResource(R.drawable.ic_account_circle_white_24dp);
        holder.nombre.setText(contactos.get(position).getNombre());
        holder.telefono.setText(contactos.get(position).getTelefono());
    }

    // Método que regresa la cantidad de contactos que tenemos
    @Override
    public int getItemCount() {
        return contactos.size();
    }

    /*
     * Clase ViewHolder anidada que nos sirve para que el adaptador recicle una vista
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias a los objetos necesarios
        public ImageView imagen;
        public TextView nombre;
        public TextView telefono;
        public ImageButton cerrar;

        public ViewHolder(final View itemView) {
            super(itemView);
            // Obtenemos las referencias a las vistas
            imagen = itemView.findViewById(R.id.imagen_llamada);
            nombre = itemView.findViewById(R.id.nombre_contacto);
            telefono = itemView.findViewById(R.id.telefono_contacto);
            cerrar = itemView.findViewById(R.id.boton_cerrar);

            // Escuchador de clicks en algun elemento del adaptador
            // ingresa los datos necesarios en caso de actualización e inicia
            // el activity NuevoContactoActivity.
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

            // Escuchador de clicks para cerrar, se encarga de la transacción necesaria
            // en la base de datos.
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
                            new ViewHolder(v);
                        }
                    }).create().show();
                }
            });
        }
    }
}

