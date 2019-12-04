/*------------------------------------------------------------------------------------------
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                       GESTIÓN DE PROYECTOS DE SOFTWARE
:*
:*                   SEMESTRE: AGO-DIC/2019    HORA: 11-12 HRS
:*
:*                       Activity para capturar/actualizar medicamentos
:*
:*  Archivo     : CapturaMedicamentosActivity.java
:*  Autor       : PPS
:*  Compilador  : Android Studio 3.1.3
:*  Descripción : Activity que sirve para actualizar/capturar nuevos medicamentos. Es un activity
:*  sencillo. Mediante el Bundle sabe que operación debe realizar, y popula los campos o los deja
:*  vacíos acorde con la operación.
:*  de teléfono. 
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  04/012/2019 Iván García Moreno   Comentarios
:*------------------------------------------------------------------------------------------*/
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

    /*
     * Método para realizar el onCreate. Obtiene referencias a los campos, inicializa los
     * datos del spinner y mediante el objeto Bundle revisa que tipo de operación es.
     * En caso de que sea una operación de actualización (2) se llama a popularCampos
     * que consulta la base de datos y rellena los campos.
     */ 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_medicamentos);

        getSupportActionBar().hide();

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
    
    /*
     * Método para guardar lo smedicamentos. Obtiene los datos y llama al método correspondiente
     * en la clase.
     */
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

    /*
     * Método para actualizar los medicamentos. Obtiene los datos y llama al método correspondiente
     * de la clase.
     */
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

    /*
     * Escuchador de eventos del comboBox prioritario. Se activa en caso de que se detecte un click sobre el 
     * comboBox, y notifica al usuario acerca del alcance de su decisión.
     */
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
