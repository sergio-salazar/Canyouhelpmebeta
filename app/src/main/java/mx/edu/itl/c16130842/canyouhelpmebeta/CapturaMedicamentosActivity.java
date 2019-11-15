package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CapturaMedicamentosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner tipoMedicamento;
    EditText nombreMedicamento;
    EditText dosisMedicamento;
    EditText horasMedicamento;
    CheckBox urgenteMedicamento;
    private  int operacion;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_medicamentos);
        //Obteniendo referencias a los objetos
        tipoMedicamento = findViewById(R.id.spinnerTipo);
        nombreMedicamento = findViewById(R.id.edNombreMedicamento);
        dosisMedicamento = findViewById(R.id.edDosisMedicamento);
        horasMedicamento = findViewById(R.id.edHorasMedicamento);
        urgenteMedicamento = findViewById(R.id.cbUrgente);

        //Populando el spinner con los datos
        /*ArrayAdapter<CharSequence> adaptadorSpinner = ArrayAdapter.createFromResource
                (this, R.array.tipos_medicinas, R.layout.support_simple_spinner_dropdown_item);
        adaptadorSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tipoMedicamento.setAdapter(adaptadorSpinner);
        tipoMedicamento.setOnItemSelectedListener(this);

        //Revisando el tipo de operación
        Intent i = getIntent();
        Bundle b = i.getExtras();
        operacion = b.getInt("ACT");
        posicion = b.getInt("POS");
        if(operacion == 2) {
            popularCampos(posicion);
        }*/
    }

    //Método para popular los campos en caso de que se desee actualizar medicinas
    public void popularCampos(int posicion) {
        Medicamentos.Medicamento med = Medicamentos.getMedicinas().get(posicion);
        nombreMedicamento.setText(med.getNombre());
        dosisMedicamento.setText(med.getDosis());
        horasMedicamento.setText(med.getHoras());
        urgenteMedicamento.setSelected(med.isUrgente());
    }

    //Método para guardar los medicamentos
    public void btnGuardarMedicamentoOnClick(View v) {
        String cadNomMedicamento = nombreMedicamento.getText().toString();
        int intDosisMedicamento = Integer.parseInt(dosisMedicamento.getText().toString());
        int intHorasMedicamento = Integer.parseInt(horasMedicamento.getText().toString());
        boolean boolUrgenteMedicamento = urgenteMedicamento.isSelected();

        if(operacion == 1) {
            if(!(cadNomMedicamento.equals(""))) {
                Medicamentos.AnadeMedicamento(tipoMedicamento.getSelectedItemPosition(),
                        cadNomMedicamento,
                        intDosisMedicamento,
                        intHorasMedicamento,
                        boolUrgenteMedicamento);
            } else if(operacion==2) {
                Medicamentos.ActualizarMedicamento(posicion, cadNomMedicamento, intDosisMedicamento,
                        intHorasMedicamento, boolUrgenteMedicamento);
            }
        }
    }

    public void checkBoxOnClick(View v) {
        if(urgenteMedicamento.isSelected()) {
            urgenteMedicamento.setSelected(false);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Medicamento Estricto");
            dialog.setMessage("Seleccionar un medicamento como estricto provocará que los recordatorios sean puntuales, incluso de madrugada." +
                    "\n Un medicamento no urgente se procurará empalmar con los demás");
            dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    urgenteMedicamento.setSelected(true);
                }
            });
            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    urgenteMedicamento.setSelected(false);
                }
            });
            dialog.setCancelable(false);
            dialog.create();
            dialog.show();
        }
    }

    //MÉTODOS POR PARTE DEL ESCUCHADOR DEL SPINNER
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tipoMedicina = findViewById(R.id.txtTipo);
        switch(i) {
            case 0:
                tipoMedicina.setText("pastillas");
                break;
            case 1:
                tipoMedicina.setText("pastillas");
                break;
            case 2:
                tipoMedicina.setText("inyeccion(es)");
                break;
            case 3:
                tipoMedicina.setText("toma");
                break;
            default:
                tipoMedicina.setText("toma");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
