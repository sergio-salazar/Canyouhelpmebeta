package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;

public class ContactosFragment extends Fragment {

    /*DatabaseReference databaseReference;
    EditText edtNombre1, edtNombre2, edtNombre3;
    EditText tel1,tel2, tel3;
    EditText mensaje;

    static final int REQUEST_SELECT_CONTACT = 1;*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contactos, null);
    }

    /*public void btnGuardarOnClick(View view) {
        edtNombre1 = (EditText) view.findViewById(R.id.edtContacto1);
        edtNombre2 = (EditText)view.findViewById(R.id.edtContacto2);
        edtNombre3 = (EditText)view.findViewById(R.id.edtContacto3);
        tel1 = (EditText)view.findViewById(R.id.edtTelefono1);
        tel2 = (EditText)view.findViewById(R.id.edtTelefono2);
        tel3 = (EditText)view.findViewById(R.id.edtTelefono3);
        mensaje = (EditText)view.findViewById(R.id.edtMensaje);
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
        Toast.makeText(view.getContext(),"Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
    }

    protected void btnContacto1OnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);*/
        /*if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }*/
    /*}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            /*Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String number = cursor.getString(numberIndex);
                //String name = cursor.getString(nameIndex);
                //edtNombre1.setText(number);
                //tel1.setText(cursor.getString(1));
            }
        }
    }*/
}
