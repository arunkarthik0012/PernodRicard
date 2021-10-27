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

public class SmartCategoryAdapter extends RecyclerView.Adapter<SmartCategoryAdapter.ViewHolder> {

    private final ArrayList<StoreVisionBo> storeVisionBolist;
    Context context;

    public SmartCategoryAdapter(ArrayList<StoreVisionBo> storeVisionBolist){
        this.storeVisionBolist=storeVisionBolist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        context= viewGroup.getContext();
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.smart_row_list_items,viewGroup,false);
//        ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SmartCategoryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView1.setText(storeVisionBolist.get(i).getCategory());
        viewHolder.textView2.setText(String.valueOf(storeVisionBolist.get(i).getSos()));
        viewHolder.textView3.setText(String.valueOf(storeVisionBolist.get(i).getOpportunity()));
        viewHolder.textView4.setText(String.valueOf(storeVisionBolist.get(i).getGap()));
        Integer integer= Integer.parseInt(viewHolder.textView4.getText().toString());
        if (integer>0){
            viewHolder.textView4.setTextColor(ContextCompat.getColor(context,R.color.red_color));
        }
        else
        {
            viewHolder.textView4.setTextColor(ContextCompat.getColor(context,R.color.green_color));
        }


    }

    @Override
    public int getItemCount() {
        return storeVisionBolist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1,textView2,textView3,textView4;
        public ViewHolder( View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.smart_textview_1);
            textView2=itemView.findViewById(R.id.smart_textview_2);
            textView3=itemView.findViewById(R.id.smart_textview_3);
            textView4=itemView.findViewById(R.id.smart_textview_4);

        }
    }

}
