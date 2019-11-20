package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolder> {
    private Context context;
    //Para indicar operación de actualización
    private int opcion = 2;

    public AdaptadorRecyclerView(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_medicina, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int tipo = Medicamentos.getMedicinas().get(position).getTipo();
        switch(tipo) {
            case 1:
                viewHolder.imagen.setImageResource(R.drawable.pastillas);
                break;
            case 2:
                viewHolder.imagen.setImageResource(R.drawable.suspension);
                break;
            case 3:
                viewHolder.imagen.setImageResource(R.drawable.inyeccion);
                break;
            default:
                viewHolder.imagen.setImageResource(R.drawable.pastillas);
                break;
        }
        viewHolder.titulo.setText(Medicamentos.getMedicinas().get(position).getNombre());
        viewHolder.dosis.setText(Medicamentos.getMedicinas().get(position).getDosisCad());
    }

    public void notificarCambios() {
        notifyDataSetChanged();
    }

    public void actualizarMedicamento(int posicion) {
        Intent i = new Intent(context, CapturaMedicamentosActivity.class);
        i.putExtra("ACT", opcion);
        i.putExtra("POS", posicion);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return Medicamentos.getMedicinas().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView titulo;
        public TextView dosis;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_medicamento);
            titulo = itemView.findViewById(R.id.titulo_medicamento);
            dosis = itemView.findViewById(R.id.dosis_medicamento);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    actualizarMedicamento(position);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
