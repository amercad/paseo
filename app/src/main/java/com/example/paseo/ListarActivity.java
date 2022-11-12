package com.example.paseo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {

    RecyclerView recyclerPaseo;
    ArrayList<Paseo> listaPaseo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        recyclerPaseo = findViewById(R.id.rvListarPaseo);
        listaPaseo = new ArrayList<>();
        recyclerPaseo.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerPaseo.setHasFixedSize(true);
        cargarPaseo();
    }

    public void cargarPaseo() {
        db.collection("facturas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                Paseo objPaseo = new Paseo();
                                objPaseo.setCodigo(document.getString("Codigo"));
                                objPaseo.setNombre(document.getString("Nombre"));
                                objPaseo.setCiudad(document.getString("Ciudad"));
                                objPaseo.setCantidad(document.getString("Ciudad"));
                                listaPaseo.add(objPaseo);
                            }
                            PaseoAdapter addPaseo = new PaseoAdapter(listaPaseo);
                            recyclerPaseo.setAdapter(addPaseo);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(ListarActivity.this, "Erro, no se encontro registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}