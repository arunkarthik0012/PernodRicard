package com.example.ferreroimagerecognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.ferreroimagerecognition.MainActivity.sumOos;
import static com.example.ferreroimagerecognition.MainActivity.sumOpportunity;

public class PriceCheckAdapter extends RecyclerView.Adapter<PriceCheckAdapter.ViewHolder> {

    Context context;
    private final ArrayList<StoreVisionBo> storeVisionBolist;

    public PriceCheckAdapter(ArrayList<StoreVisionBo> storeVisionBolist){
        this.storeVisionBolist=storeVisionBolist;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.price_check_row_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.textView1.setText(storeVisionBolist.get(position).getSku());
        holder.textView2.setText(String.valueOf(storeVisionBolist.get(position).getSuggestedPrice()));
        holder.textView4.setText(String.valueOf(storeVisionBolist.get(position).getSuggestedPrice()-storeVisionBolist.get(position).getActualPrice()));
        holder.textView3.setText(String.valueOf(storeVisionBolist.get(position).getActualPrice()));

    }

    @Override
    public int getItemCount() {
        return storeVisionBolist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.price_textview_1);
            textView2=itemView.findViewById(R.id.price_textview_2);
            textView3=itemView.findViewById(R.id.price_textview_3);
            textView4=itemView.findViewById(R.id.price_textview_4);

        }
    }
}
