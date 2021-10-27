package com.example.ferreroimagerecognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlanogramAdapter extends RecyclerView.Adapter<PlanogramAdapter.ViewHolder> {

    private final ArrayList<StoreVisionBo> storeVisionBolist;
    Context context;


    public PlanogramAdapter(ArrayList<StoreVisionBo> storeVisionBolist ){
        this.storeVisionBolist=storeVisionBolist;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.planogram_row_items,parent,false);
        return new PlanogramAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.textView.setText(storeVisionBolist.get(position).getBrand());
        holder.textView1.setText(String.valueOf(storeVisionBolist.get(position).isScore()));

        Integer integer= Integer.parseInt(holder.textView1.getText().toString());
        if (integer>0){
            holder.textView1.setTextColor(ContextCompat.getColor(context,R.color.green_color));
        }
        else
        {
            holder.textView1.setTextColor(ContextCompat.getColor(context,R.color.red_color));
        }

    }

    @Override
    public int getItemCount() {
        return storeVisionBolist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView,textView1;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.planogram_textview_1);
            textView1=itemView.findViewById(R.id.planogram_textview_2);
        }
    }
}
