package com.example.firebasead.calendario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.R;
import com.example.firebasead.database.eventosDatabase.Evento;

import java.util.ArrayList;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.ViewHolder>{

    private ArrayList<Evento> eventosList;

    public interface ItemClickListener {
        void onClick(View view, int position, Evento evento);
    }

    private ItemClickListener clickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    private RecyclerViewClickListener listener;

    public AdaptadorEventos(ArrayList<Evento> dataSet) {
        eventosList = dataSet;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tituloEvento;
        private final TextView fechaEvento;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tituloEvento = (TextView) v.findViewById(R.id.tituloEvento);
            fechaEvento = (TextView) v.findViewById(R.id.fechaEvento);

        }


        public TextView getTituloEvento() {
            return tituloEvento;
        }

        public TextView getFechaEvento() {
            return fechaEvento;
        }

        public void onClick(View view) {
            // Si tengo un manejador de evento lo propago con el índice
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition(), eventosList.get(getAdapterPosition()));
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.caja_eventos, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTituloEvento().setText(eventosList.get(position).getTitulo());
        holder.getFechaEvento().setText(eventosList.get(position).getFechaInicio().toString());
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }
}
