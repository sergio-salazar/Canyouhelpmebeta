package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfiguracionActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText edtNombre1, edtNombre2, edtNombre3;
    EditText tel1,tel2, tel3;
    EditText mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
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
}
