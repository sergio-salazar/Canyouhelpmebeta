package mx.edu.itl.c16130842.canyouhelpmebeta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MedicamentosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicamentos, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CapturaMedicamentosActivity.class);
                startActivity(intent);
            }
        });
        context = container.getContext();
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorRecyclerView(context);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*prueba = (TextView)findViewById(R.id.txvPrueba);

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
    });*/
}
