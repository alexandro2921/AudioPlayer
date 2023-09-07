package com.example.audioplayer;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private RVAdapterInterface rvAdapterInterface;
    private Context context;
    private List<Model> modelList;

    public RVAdapter(RVAdapterInterface rvAdapterInterface, Context context, List<Model> modelList) {
        this.rvAdapterInterface = rvAdapterInterface;
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(modelList.get(position).getIdImage());
        holder.songName.setText(modelList.get(position).getAudioName());
        holder.artistName.setText(modelList.get(position).getArtistName());
        if(modelList.get(position).getPlaying()){
            holder.playingNow.setVisibility(View.VISIBLE);
        }else{
            holder.playingNow.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView songName,artistName,playingNow;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName =itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            playingNow = itemView.findViewById(R.id.playNow);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( rvAdapterInterface!=null){
                        int pos = getAdapterPosition();

                        if( pos!=RecyclerView.NO_POSITION){
                            rvAdapterInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    public void setResultList(List<Model> resultList){
        this.modelList=resultList;
    }
}
