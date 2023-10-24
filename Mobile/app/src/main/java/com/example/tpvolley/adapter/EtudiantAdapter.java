package com.example.tpvolley.adapter;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tpvolley.EtudiantsActivity;
import com.example.tpvolley.UpdateEtudiantActivity;
import com.example.tpvolley.beans.Etudiant;

import com.example.tpvolley.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {
    private static final String TAG = "EtudiantAdapter";
    private List<Etudiant> etudiants;
    private Context context;

    private RequestQueue requestQueue ;


    public EtudiantAdapter(Context context, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        this.context = context;

    }


    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.etudiant_item,
                viewGroup, false);
        final EtudiantViewHolder holder = new EtudiantViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder starViewHolder, int i) {
        Random random = new Random();
        int indice;
        int avatar;
        if(etudiants.get(i).getSexe().equals("homme")) {
            indice = random.nextInt(avatarMen.length);
            avatar = avatarMen[indice];
        }
        else {
            indice = random.nextInt(avatarWomen.length);
            avatar = avatarWomen[indice];
        }
        Log.d(TAG, "onBindView call ! "+ i);
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().override(100, 100))
                .load(avatar)
                .into(starViewHolder.image);
        starViewHolder.nom.setText(etudiants.get(i).getNom() + " " + etudiants.get(i).getPrenom().toUpperCase());
        starViewHolder.ville.setText(etudiants.get(i).getVille());
        if(etudiants.get(i).getSexe().equals("homme"))
            starViewHolder.sexe.setImageResource(R.mipmap.h);
        else
            starViewHolder.sexe.setImageResource(R.mipmap.f);
        starViewHolder.ide.setText(etudiants.get(i).getId()+"");
        starViewHolder.pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateEtudiantActivity.class);
                intent.putExtra("id",starViewHolder.ide.getText().toString());
                intent.putExtra("nom",etudiants.get(i).getNom());
                intent.putExtra("prenom",etudiants.get(i).getPrenom());
                intent.putExtra("ville",etudiants.get(i).getVille());
                intent.putExtra("sexe",etudiants.get(i).getSexe());
                context.startActivity(intent);
            }
        });
        starViewHolder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Do you want to delete this student ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String id = starViewHolder.ide.getText().toString();
                        deleteStudent(id);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return etudiants.size();
    }
    public class EtudiantViewHolder extends RecyclerView.ViewHolder {
        TextView ide;
        ImageView image;
        TextView nom;
        TextView ville;
        ImageView sexe;
        ImageView pen;
        ImageView trash;
        RelativeLayout parent;
        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            ide = itemView.findViewById(R.id.ide);
            image = itemView.findViewById(R.id.image);
            nom = itemView.findViewById(R.id.nom);
            ville = itemView.findViewById(R.id.ville);
            sexe = itemView.findViewById(R.id.sexe);
            parent = itemView.findViewById(R.id.parent);
            pen = itemView.findViewById(R.id.pen);
            trash = itemView.findViewById(R.id.trash);
        }
    }
    public  void  updateEtudiants(List<Etudiant> etudiants){
        this.etudiants = etudiants;
        notifyDataSetChanged();
    }
    private void deleteStudent(String id) {
        requestQueue = Volley.newRequestQueue(context);
        String deleteUrl = "http://192.168.1.109/School1/ws/deleteEtudiant.php?id="+id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type type = new TypeToken<List<Etudiant>>(){}.getType();
                        List<Etudiant> upEtudiants = new Gson().fromJson(response, type);
                        updateEtudiants(upEtudiants);
                        Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        requestQueue.add(stringRequest);
    }

    private static final int[] avatarMen = {
            R.mipmap.h2,
            R.mipmap.h3,
            R.mipmap.h4,
            R.mipmap.h5,
            R.mipmap.h6,
            R.mipmap.h7,
    };
    private static final int[] avatarWomen = {
            R.mipmap.f1,
            R.mipmap.f2,
            R.mipmap.f3,
            R.mipmap.f4,
    };
}