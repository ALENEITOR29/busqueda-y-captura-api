package com.example.catalogodeproductosapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private final List<Producto> productos;

    public ProductoAdapter(List<Producto> productos) { this.productos = productos; }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvCategoria.setText(holder.itemView.getContext().getString(R.string.category_prefix, producto.getCategoria()));
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "$%,.0f", producto.getPrecio()));
        holder.tvRating.setText(String.format(Locale.getDefault(), "★ %.1f", producto.getRating()));

        Glide.with(holder.itemView.getContext()).load(producto.getImagen())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.imgProducto);

        Glide.with(holder.itemView.getContext())
                .load("https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=800")
                .into(holder.imgFondoGalaxia);

        holder.btnRecompensa.setOnClickListener(v -> Toast.makeText(v.getContext(), "Recompensa por " + producto.getNombre() + ": ¡VIVO O MUERTO!", Toast.LENGTH_SHORT).show());
        holder.itemView.setOnClickListener(v -> Toast.makeText(v.getContext(), producto.getNombre(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() { return productos.size(); }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto, imgFondoGalaxia;
        TextView tvNombre, tvCategoria, tvPrecio, tvRating;
        Button btnRecompensa;
        ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            imgFondoGalaxia = itemView.findViewById(R.id.imgFondoGalaxia);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvRating = itemView.findViewById(R.id.tvRating);
            btnRecompensa = itemView.findViewById(R.id.btnRecompensa);
        }
    }
}
