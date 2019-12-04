package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoContactoActivity extends AppCompatActivity {
    // Referencias a las vistas necesarias
    EditText edtNombre;
    EditText edtTelefono;
    TextView txvEditar;
    Bundle extras;
    // Cadenas que se despliegan
    String nombre, telefono, editar;
    // Código para hacer la solicitud de obtener contactos existentes
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    /*
     * Método onCreate: obtiene referencias a las cadenas y los 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        getSupportActionBar().hide();

        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtTelefono = (EditText)findViewById(R.id.edtTelefono);
        txvEditar = (TextView)findViewById(R.id.txvEditar);

        extras = getIntent().getExtras();
        nombre = extras.getString("nombre");
        telefono = extras.getString("telefono");
        editar = extras.getString("editar");
        edtNombre.setText(nombre);
        edtTelefono.setText(telefono);
        txvEditar.setText(editar);
    }

    /*
     * Método que llama al startActivity for results para seleccionar un contacto nuevo, 
     * mediante un intent programado como tal
     */
    public void btnContactoExistenteOnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
    }

    /*
     * Método que es lamado autómaticamente por el sistema Android al regresar del results. Obtiene
     * los datos del Uri que viene como resultado del intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                Uri datos = data.getData();
                edtNombre.setText(getName(datos));
                edtTelefono.setText(getPhone(datos));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * Obtiene el nombre de un contacto mediante su Uri, consultándola en los Contactos, 
     * el resultado es regresado en un cursor.
     */
    public String getName(Uri uri) {
        String nombre = null;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0);
        }
        cursor.close();
        return nombre;
    }

    /*
     * Obtiene el teléfono de un contacto mediante su Uri, consultándola en los Contactos, 
     * el resultado es regresado en un cursor.
     */
    public String getPhone(Uri uri) {
        String telefono = null;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor.moveToFirst()) {
            telefono = cursor.getString(0);
        }
        cursor.close();
        return telefono;
    }

    /*
     * Método para guardar un contacto. Obtiene los datos y crea un objeto manejador de
     * base de datos para actualizarlo. Mediante el texto en txvEditar revisa si es una 
     * operación de actualización o de captura de un nuevo contacto y llama a los métodos
     * correspondientes en la bae de datos.
     */
    public void btnGuardarOnClick(View view) {
        String nombreContacto = edtNombre.getText().toString();
        String telefonoContacto = edtTelefono.getText().toString();
        ContactoSQLiteHelper contacto = new ContactoSQLiteHelper(this);

        if ("0".equalsIgnoreCase(txvEditar.getText().toString())) {
            if (nombreContacto.length() > 0 && telefonoContacto.length() > 0) {
                contacto.agregarContacto(nombreContacto, telefonoContacto);
                Toast.makeText(this, "El contacto se ha añadido exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Debe ingresar todos los datos del contacto.", Toast.LENGTH_SHORT).show();
            }
        }
        if ("1".equalsIgnoreCase(txvEditar.getText().toString())) {
            if (nombreContacto.length() > 0 && telefonoContacto.length() > 0) {
                contacto.editarContacto(nombreContacto, telefonoContacto, nombre, telefono);
                Toast.makeText(this, "El contacto se ha modificado exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Debe ingresar todos los datos del contacto.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
