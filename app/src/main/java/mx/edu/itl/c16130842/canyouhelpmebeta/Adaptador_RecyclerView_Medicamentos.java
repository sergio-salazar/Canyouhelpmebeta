/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Adaptador para el RecyclerView
:*
:*  Archivo     : AdaptadorRecyclerView.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Clase que actúa como Adaptador para el RecyclerView en ListadoMedicamentosActivity
:*  utiliza a su subclase ViewHolder para "rellenar" los campos. Este adaptador consiste en un conjunto
:*  de CardViews (tarjeta_medicina.mxl).
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  19/011/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador_RecyclerView_Medicamentos extends RecyclerView.Adapter<Adaptador_RecyclerView_Medicamentos.ViewHolder> {

    //Variable necesaria para iniciar el intent
    private Context context;

    //Para indicar operación de actualización cuando se desee eso, ya que el MedicinasActivity es iniciado como forResults
    private int OPERACION_ACTUALIZACION = 2;

    //Constructor de la clase, recibe el context de ListadoMedicamentosActivity
    public Adaptador_RecyclerView_Medicamentos(Context context) {
        this.context = context;
    }


    /* 
     * Método inflador de los ViewHolder. Es llamado autómaticamente según tantos elementos haya (obtenido mediante
     * getItemCount), e infla el layout adecuado.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_medicinas, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    /* 
     * Método que es llamado autómaticamente conforme se van rellenando los RecyclerView de información
     * se obtienen referencias a los elementos del ViewHolder (definido en tarjeta_medicina.xml) y se
     * rellenan con su información correspondiente
     */
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

    //Método que recibe la notificación para actualizar el RecyclerView en caso de captura de medicamentos
    public void notificarCambios() {
        notifyDataSetChanged();
    }

    /*
     * Método que inicializa el Activity CapturaMedicamentosActivity, utilizando identificadores numéricos
     * para que este sepa que es una operación de actualización y no de captura nueva.
     */
    public void actualizarMedicamento(int posicion) {
        Intent i = new Intent(context, CapturaMedicamentosActivity.class);
        i.putExtra("OP", OPERACION_ACTUALIZACION);
        i.putExtra("POS", posicion);
        context.startActivity(i);
    }

    //Método que retorna la cantidad de medicamentos que tenemos para desplegar, es usado para saber cuantas cartas debe crear
    @Override
    public int getItemCount() {
        return Medicamentos.getMedicinas().size();
    }

    /*
     * Clase que nos permite reutilizar una misma vista, en este caso el CadView medicamentos
     * Mediante esta clase el RecyclerView "clona" la vista
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //Referencias a los objetos del viewHolder: un ImageView y dos TextView
        public ImageView imagen;
        public TextView titulo;
        public TextView dosis;

        /*
         * Creación del objeto ViewHolder. Se obtienen las referencias a los elementos View
         * dentro del layour con el que estamos trabajando y se pone un onClick listener
         * para iniciar el proceso de actualización de medicamentos
        */
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

        //ToString
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
