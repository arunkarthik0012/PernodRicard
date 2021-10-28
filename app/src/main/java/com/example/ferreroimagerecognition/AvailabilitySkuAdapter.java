package com.example.ferreroimagerecognition;

import android.app.ActivityManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class AvailabilitySkuAdapter extends RecyclerView.Adapter<AvailabilitySkuAdapter.ViewHolder> {

    private final ArrayList<StoreVisionBo> storeVisionBolist;
    Context context;

    public AvailabilitySkuAdapter(ArrayList<StoreVisionBo> storeVisionBolist){
        this.storeVisionBolist=storeVisionBolist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        context= viewGroup.getContext();
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.availability_row_items,viewGroup,false);
//        ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AvailabilitySkuAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(storeVisionBolist.get(i).getSku());
        viewHolder.textView2.setText(String.valueOf(storeVisionBolist.get(i).getNooffacing()));
        viewHolder.textView4.setText(String.valueOf(storeVisionBolist.get(i).getAvailability()));
        viewHolder.textView3.setText(String.valueOf(storeVisionBolist.get(i).getOutofstock()));
        viewHolder.textView5.setText("\u20ac"+String.valueOf(storeVisionBolist.get(i).getOpportunity()*storeVisionBolist.get(i).getOutofstock()));
        Integer integer= Integer.parseInt(viewHolder.textView3.getText().toString());
        if (integer>0){
            viewHolder.textView3.setTextColor(ContextCompat.getColor(context,R.color.red_color));
        }
        else
        {
            viewHolder.textView3.setTextColor(ContextCompat.getColor(context,R.color.green_color));
        }



    }

    @Override
    public int getItemCount() {
        return storeVisionBolist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1,textView2,textView3,textView4,textView5;
        public ViewHolder( View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.availability_textview_1);
            textView2=itemView.findViewById(R.id.availability_textview_2);
            textView3=itemView.findViewById(R.id.availability_textview_3);
            textView4=itemView.findViewById(R.id.availability_textview_4);
            textView5=itemView.findViewById(R.id.availability_textview_5);
        }
    }
}
