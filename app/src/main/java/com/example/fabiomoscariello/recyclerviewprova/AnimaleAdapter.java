package com.example.fabiomoscariello.recyclerviewprova;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fabiomoscariello.recyclerviewprova.Model.Animale;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AnimaleAdapter extends RecyclerView.Adapter<AnimaleViewHolder> {
    public ArrayList<Animale> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Animale> dataSet) {
        this.dataSet = dataSet;
    }

    private ArrayList<Animale> dataSet;


    public AnimaleAdapter(ArrayList<Animale> dataSet) {
        this.dataSet = dataSet;
    }
    @NonNull
    @Override
    public AnimaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new AnimaleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimaleViewHolder animaleViewHolder, int position) {
    animaleViewHolder.bind(dataSet.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
