package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracionActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText edtNombre1, edtNombre2, edtNombre3;
    EditText tel1,tel2, tel3;
    EditText mensaje;

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    private ContactsManager contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        getSupportActionBar().hide();

        contacts = new ContactsManager(this);
    }

    public void btnGuardarOnClick(View view) {
        edtNombre1 = (EditText)findViewById(R.id.edtContacto1);
        edtNombre2 = (EditText)findViewById(R.id.edtContacto2);
        edtNombre3 = (EditText)findViewById(R.id.edtContacto3);
        tel1 = (EditText)findViewById(R.id.edtTelefono1);
        tel2 = (EditText)findViewById(R.id.edtTelefono2);
        tel3 = (EditText)findViewById(R.id.edtTelefono3);
        mensaje = (EditText)findViewById(R.id.edtMensaje);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if (!"".equals(edtNombre1.getText().toString()) && !"".equals(tel1.getText().toString())) {
            databaseReference.child("nombre1").setValue(edtNombre1.getText().toString());
            databaseReference.child("telefono1").setValue(tel1.getText().toString());
        }
        if (!"".equals(edtNombre2.getText().toString()) && !"".equals(tel2.getText().toString())) {
            databaseReference.child("nombre2").setValue(edtNombre2.getText().toString());
            databaseReference.child("telefono2").setValue(tel2.getText().toString());
        }
        if (!"".equals(edtNombre3.getText().toString()) && !"".equals(tel3.getText().toString())) {
            databaseReference.child("nombre3").setValue(edtNombre3.getText().toString());
            databaseReference.child("telefono3").setValue(tel3.getText().toString());
        }
        if (!"".equalsIgnoreCase(mensaje.getText().toString())) {
            databaseReference.child("mensaje").setValue(mensaje.getText().toString());
        }
        Toast.makeText(getApplicationContext(),"Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
    }

    protected void btnContacto1OnClick(View view) {
        /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }*/

        //contacts.selectContact();

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            //tel1.setText(cursor.getString(0));
            if (cursor != null && cursor.moveToFirst()) {
                //int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                //String number = cursor.getString(numberIndex);
                //String name = cursor.getString(nameIndex);
                //edtNombre1.setText("Hola");
                tel1.setText(cursor.getString(0));
            }
        }*/

        /*super.onActivityResult(requestCode, resultCode, data);
        contacts.onActivityResult(requestCode, resultCode, data, new ContactsManager.onSelectedPhone() {
            @Override
            public void onSuccess(String phone) {
                tel1.setText(phone);
            }

            @Override
            public void onFailure() {
                Toast.makeText(ConfiguracionActivity.this, "No funciona.", Toast.LENGTH_SHORT);
            }
        });*/

        if (requestCode ==  REQUEST_SELECT_PHONE_NUMBER) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    private void renderContact(Uri uri) {
        edtNombre1.setText(getContactName(uri));
    }

    private String getContactName(Uri uri) {
        String name = null;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }

    protected void btnContacto2OnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    protected void btnContacto3OnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }
}
