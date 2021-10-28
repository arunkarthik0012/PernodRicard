package com.example.ferreroimagerecognition;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import static com.example.ferreroimagerecognition.MainActivity.sumOos;
import static com.example.ferreroimagerecognition.MainActivity.sumOpportunity;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private final ArrayList<StoreVisionBo> storeVisionBolist;
    Context context;

    public AvailabilityAdapter(ArrayList<StoreVisionBo> storeVisionBolist ){
        this.storeVisionBolist=storeVisionBolist;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.availability_row_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AvailabilityAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(storeVisionBolist.get(position).getCategory());
        holder.textView2.setText(String.valueOf(storeVisionBolist.get(position).getNooffacing()));
        holder.textView4.setText(String.valueOf(storeVisionBolist.get(position).getAvailability()));
        holder.textView3.setText(String.valueOf(sumOos));
        holder.textView5.setText("\u20ac"+String.valueOf(sumOpportunity));
        Integer integer= Integer.parseInt(holder.textView3.getText().toString());

        if (integer>0){
            holder.textView3.setTextColor(ContextCompat.getColor(context,R.color.red_color));
        }
        else
        {
            holder.textView3.setTextColor(ContextCompat.getColor(context,R.color.green_color));
        }


    }

    @Override
    public int getItemCount() {
        return storeVisionBolist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2,textView3,textView4,textView5;
        public ViewHolder(View itemview){
            super(itemview);
            textView1=itemView.findViewById(R.id.availability_textview_1);
            textView2=itemView.findViewById(R.id.availability_textview_2);
            textView3=itemView.findViewById(R.id.availability_textview_3);
            textView4=itemView.findViewById(R.id.availability_textview_4);
            textView5=itemView.findViewById(R.id.availability_textview_5);

        }

    }
}
