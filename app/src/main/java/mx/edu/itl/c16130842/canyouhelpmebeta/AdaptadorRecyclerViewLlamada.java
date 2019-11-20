package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorRecyclerViewLlamada extends RecyclerView.Adapter<AdaptadorRecyclerViewLlamada.ViewHolder> {

    private Context context;

    public AdaptadorRecyclerViewLlamada(Context context) {
        this.context = context;
    }

    @Override
    public AdaptadorRecyclerViewLlamada.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_contacto, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptadorRecyclerViewLlamada.ViewHolder holder, int position) {
        holder.imagen.setImageResource(R.drawable.ic_phone_forwarded_black_24dp);
        holder.nombre.setText(Contactos.getContactos().get(position).getNombre());
        holder.telefono.setText(Contactos.getContactos().get(position).getTelefono());
    }

    @Override
    public int getItemCount() {
        return Contactos.getContactos().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView telefono;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_llamada);
            nombre = itemView.findViewById(R.id.nombre_contacto);
            telefono = itemView.findViewById(R.id.telefono_contacto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Contactos.getContactos().get(position).getTelefono()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED)
                        return;
                    context.startActivity(intent);
                }
            });
        }
    }
}
