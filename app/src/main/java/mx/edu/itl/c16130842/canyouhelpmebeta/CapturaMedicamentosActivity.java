package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CapturaMedicamentosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Referencias a los objetos del layour para obtener/añadir información
    EditText edNombre;
    EditText edDosis;
    EditText edHoras;
    CheckBox cbPrioritario;
    Spinner spinnerTipo;
    String tipoMedicina;
    EditText edToma;

    //Código para ubicar si es una solicitud de captura o de actualización
    //1 para capturar nuevo medicamento, 2 para actualizar medicamento existente
    private static int CODIGO_OPERACION=-1;
    Intent i;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_medicamentos);

        //Inicialización de los objetos
        edNombre = findViewById(R.id.edNombre);
        edDosis = findViewById(R.id.edDosis);
        edHoras = findViewById(R.id.edHoras);
        cbPrioritario = findViewById(R.id.checkBox);
        spinnerTipo = findViewById(R.id.spinner);
        edToma = findViewById(R.id.edToma);

        //Populando el spinner con los datos (tipos de medicamentos)
        ArrayAdapter<CharSequence> adaptadorSpinner = ArrayAdapter.createFromResource
                (this, R.array.tipos_medicinas, R.layout.support_simple_spinner_dropdown_item);
        adaptadorSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adaptadorSpinner);
        spinnerTipo.setOnItemSelectedListener(this);


        //Vemos que método invocó a este Activity, si fue uno de actualización (operación 2)
        // se rellenaran los campos. Los datos se representan mediante (tipo de operación es OP,
        // posición a actualizar es POS). En caso de que sea de actualización, obtenemos la posición
        // del Bundle
        i = getIntent();
        bundle = i.getExtras();
        if(bundle.containsKey("OP")) {
            CODIGO_OPERACION = bundle.getInt("OP");
            if(CODIGO_OPERACION == 2 && bundle.getInt("POS") != -1) {
                popularCampos(bundle.getInt("POS"));
            }
        }
    }


    /*
     * Método para relelnar la info en caso de que sea una operación de actualización. Recupera
     * el objeto mediante la posición de la clase Medicamentos.
     */
    public void popularCampos(int posicion) {
        Medicamentos.Medicamento med = Medicamentos.getMedicinas().get(posicion);
        edNombre.setText(med.getNombre());
        edDosis.setText(String.valueOf(med.getDosis()));
        edHoras.setText(String.valueOf(med.getHoras()));
        cbPrioritario.setSelected(med.getPrioridad());
        edToma.setText(String.valueOf(med.getDurTrat()));
    }

    /*
     * Método que es activado en caso de que se presione el botón salvar. Verifica que operación
     * está realizando y de allí decide a que método llamar.
     */
    public void btnSalvarOnClick(View v) {
        if(CODIGO_OPERACION == 1 && bundle.getInt("POS")==-1) {
            salvarMedicamento();
            setResult(RESULT_OK);
            finish();
        } else if(CODIGO_OPERACION == 2 && bundle.getInt("POS")!=-1) {
            actualizarMedicamento();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Ha habido un problema con la aplicación", Toast.LENGTH_LONG);
        }
    }

    private void salvarMedicamento() {
        String nombreMedicamento = edNombre.getText().toString();
        int dosisMedicamento = Integer.parseInt(edDosis.getText().toString());
        int horasMedicamento = Integer.parseInt(edHoras.getText().toString());
        boolean esPrioritario = cbPrioritario.isSelected();
        int diasDeToma = Integer.parseInt(edToma.getText().toString());
        Medicamentos.AnadeMedicamento(spinnerTipo.getSelectedItemPosition(), nombreMedicamento,
                dosisMedicamento, horasMedicamento,
                esPrioritario, diasDeToma);
    }

    // Método para actualizar los medicamentos desde la clase Medicamentos,
    // recibe todos los parámetros de un medicamento
    private void actualizarMedicamento() {
        double dosis = Double.valueOf(edDosis.getText().toString());
        Medicamentos.ActualizarMedicamento(bundle.getInt("POS"),
                spinnerTipo.getSelectedItemPosition(),
                edNombre.getText().toString(),
                (int) dosis,
                Integer.parseInt(edHoras.getText().toString()),
                cbPrioritario.isSelected(),
                Integer.parseInt(edToma.getText().toString())
        );
    }

    //Escuchador de eventos del cbPrioritario, notifica en caso de que seleccione un medicamento
    //como urgente
    public void cbPrioritarioOnClick(View v) {
        if(cbPrioritario.isSelected()) {
            cbPrioritario.setSelected(false);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso");
            builder.setMessage("Marcar un medicamento como prioritario hará que sea notificado" +
                    "estrictamente (cada ciertas horas, incluso de noche). Los medicamentos no " +
                    "marcados como tal serán empalmados (siempre que tengan una hora de diferencia)" +
                    "y no serán notificados de noche");
            builder.setCancelable(false);
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cbPrioritario.setSelected(false);
                }
            });
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cbPrioritario.setSelected(true);
                }
            });
            builder.create();
            builder.show();
        }
    }

    //MÉTODOS POR PARTE DEL ESCUCHADOR DEL SPINNER
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipoMedicina = "pastillas";
        switch(i) {
            case 0:
                tipoMedicina = "pastillas";
                break;
            case 1:
                tipoMedicina = "pastillas";
                break;
            case 2:
                tipoMedicina = "inyeccion";
                break;
            case 3:
                tipoMedicina = "toma";
                break;
            default:
                tipoMedicina = "toma";
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

}