package com.example.catalogodeproductosapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvEstado;
    private ProductoAdapter adapter;
    private final List<Producto> listaProductos = new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgGalaxia = findViewById(R.id.imgGalaxia);
        if (imgGalaxia != null) {
            Glide.with(this)
                    .load("https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=800")
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgGalaxia);
        }

        RecyclerView rvProductos = findViewById(R.id.rvProductos);
        progressBar = findViewById(R.id.progressBar);
        tvEstado = findViewById(R.id.tvEstado);
        rvProductos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProductos);
        rvProductos.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);
        cargarProductos();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void cargarProductos() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (tvEstado != null) tvEstado.setText("");
        String url = "https://rickandmortyapi.com/api/character";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray resultadosJson = response.getJSONArray("results");
                        listaProductos.clear();
                        for (int i = 0; i < resultadosJson.length(); i++) {
                            JSONObject objeto = resultadosJson.getJSONObject(i);
                            String nombre = objeto.optString("name", getString(R.string.fallback_product_name));
                            String especie = objeto.optString("species", getString(R.string.fallback_category));
                            String estado = objeto.optString("status", "Alive");
                            String categoria = especie + " • " + estado;
                            String imagen = objeto.optString("image", "");

                            double precio;
                            if (nombre.equalsIgnoreCase("Rick Sanchez")) {
                                precio = 100000000.0;
                            } else {
                                precio = 5000000.0 + (i * 1500000.0);
                            }

                            double rating = 4.5 + ((i % 5) * 0.1);
                            listaProductos.add(new Producto(nombre, categoria, imagen, precio, rating));
                        }
                        adapter.notifyDataSetChanged();
                        if (tvEstado != null) {
                            tvEstado.setText(getString(R.string.products_loaded, listaProductos.size()));
                            tvEstado.setTextColor(0xFF68D391);
                        }
                    } catch (Exception e) {
                        if (tvEstado != null) tvEstado.setText(getString(R.string.error_processing));
                    }
                }, error -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    if (tvEstado != null) tvEstado.setText(getString(R.string.error_connection));
                });
        requestQueue.add(request);
    }
}
