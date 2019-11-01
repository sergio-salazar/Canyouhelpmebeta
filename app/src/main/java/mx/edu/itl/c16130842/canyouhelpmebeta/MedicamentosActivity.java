package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicamentosActivity extends AppCompatActivity {

    TextView prueba;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        prueba = (TextView)findViewById(R.id.txvPrueba);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("nombre1").setValue("La Jeti");
        databaseReference.child("nombre1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String texto = dataSnapshot.getValue().toString();
                    prueba.setText(texto);
                } else {
                    prueba.setText("No existe.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
