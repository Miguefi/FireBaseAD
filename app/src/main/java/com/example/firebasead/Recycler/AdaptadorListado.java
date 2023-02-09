package com.example.firebasead.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.R;

import java.util.ArrayList;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {

    private ArrayList<PerfilesClientes> perfilesList;

    public interface ItemClickListener {
        void onClick(View view, int position, PerfilesClientes perfilesClientes);
    }

    private ItemClickListener clickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    private RecyclerViewClickListener listener;

    public AdaptadorListado(ArrayList<PerfilesClientes> dataSet) {
        perfilesList = dataSet;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nomClienteCajaPerfiles1;
        private final TextView letraNombre;
        private final ImageView imagenCajaPerfiles;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nomClienteCajaPerfiles1 = (TextView) v.findViewById(R.id.nomClienteCajaPerfiles1);
            letraNombre = (TextView) v.findViewById(R.id.Letra);
            imagenCajaPerfiles = (ImageView) v.findViewById(R.id.imagenCajaPerfiles);

        }


        public TextView getNomCliente() {
            return nomClienteCajaPerfiles1;
        }

        public TextView getLetraNom() {
            return letraNombre;
        }

        public ImageView getImagenPerfil() {
            return imagenCajaPerfiles;
        }

        public void onClick(View view) {
            // Si tengo un manejador de evento lo propago con el índice
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition(), perfilesList.get(getAdapterPosition()));
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.caja_perfiles, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//      String letraActual = perfilesList.get(position).getLetra();
//      String letraSiguiente = "";
        holder.getNomCliente().setText(perfilesList.get(position).getNombre() + " "  + perfilesList.get(position).getApellidos());
        holder.getLetraNom().setText(perfilesList.get(position).getLetra());
        //holder.getImagenPerfil().setImageResource(perfilesList.get(position).getImagen());
//      if (letraActual != letraSiguiente) {
//          holder.getLetraNom().setText(perfilesList.get(position).getLetra());
//      }
//      letraSiguiente = perfilesList.get(position).getLetra();
    }

    @Override
    public int getItemCount() {
        return perfilesList.size();
    }

}