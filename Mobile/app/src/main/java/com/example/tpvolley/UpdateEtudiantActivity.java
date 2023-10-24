package com.example.tpvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpvolley.adapter.EtudiantAdapter;
import com.example.tpvolley.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UpdateEtudiantActivity extends AppCompatActivity {
    private TextView id;
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button update;
    private RequestQueue requestQueue ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_etudiant);
        Intent intent = getIntent();

        id = findViewById(R.id.id);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ville = (Spinner) findViewById(R.id.ville);
        update = (Button) findViewById(R.id.update);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);

        String idi = intent.getStringExtra("id");
        String nomi = intent.getStringExtra("nom");
        String prennomi = intent.getStringExtra("prenom");
        String villei = intent.getStringExtra("ville");
        String sexei = intent.getStringExtra("sexe");

        id.setText(idi);
        nom.setText(nomi);
        prenom.setText(prennomi);
        if (sexei.equals("homme")) {
            m.setChecked(true);
            f.setChecked(false);
        }
        else {
            m.setChecked(false);
            f.setChecked(true);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEtudiant();
            }
        });


    }
    private void updateEtudiant() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String sexe;
        if(m.isChecked())
            sexe = "homme";
        else
            sexe = "femme";
        String ids = id.getText().toString();
        String noms = nom.getText().toString();
        String prenoms = prenom.getText().toString();
        String villes = ville.getSelectedItem().toString();
        String UpdateUrl = "http://192.168.1.109/School1/ws/updateEtudiant.php?id=" + ids +
                "&nom=" + noms + "&prenom=" + prenoms + "&ville=" + villes + "&sexe=" + sexe ;
        Log.d("url", UpdateUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, UpdateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Student updated successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateEtudiantActivity.this, EtudiantsActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

}