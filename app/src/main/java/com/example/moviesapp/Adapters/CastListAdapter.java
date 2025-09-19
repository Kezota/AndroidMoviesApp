package com.example.moviesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Domains.Cast;
import com.example.moviesapp.R;

import java.util.ArrayList;

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.ViewHolder> {
    ArrayList<Cast> casts;
    Context context;

    public CastListAdapter(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_actors, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(casts.get(position).getPicUrl())
                .into(holder.pic);
        holder.nameTxt.setText(casts.get(position).getActor());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView nameTxt;
        public ViewHolder(android.view.View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.actorImage);
            nameTxt = itemView.findViewById(R.id.actorNameTxt);
        }
    }
}
