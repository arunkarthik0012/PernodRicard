package com.example.ferreroimagerecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SelectionPageAdapter mselectionPageAdapter;
    private ViewPager mviewPager;
    private Button store_new_vision;
    private StoreVisionHelper storeVisionHelper;
    ArrayList<StoreVisionBo> availlist2=new ArrayList<>();
    public  static int adherence=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mselectionPageAdapter= new SelectionPageAdapter(getSupportFragmentManager());

        storeVisionHelper=new StoreVisionHelper(this);

        mviewPager=(ViewPager)findViewById(R.id.view_pager);
        setupViewPager(mviewPager);
        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("Store Vision");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Store Vision");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        store_new_vision=findViewById(R.id.store_new_vision);
        store_new_vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });



    }
    private void setupViewPager(ViewPager viewPager){
        SelectionPageAdapter adapter=new SelectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AvailabilityFragment(),"Availability");
        adapter.addFragment(new SmartshelfFragment(),"SmartShelf");
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    //bottom dialog
    private void showDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.storevision_bottom_dialog);
        TextView newvision=dialog.findViewById(R.id.new_vision);
        TextView type=dialog.findViewById(R.id.type);
        TextView closeicon=dialog.findViewById(R.id.close_icon);
        TextView category=dialog.findViewById(R.id.category_dialog);
        Spinner typespinner=dialog.findViewById(R.id.type_spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.type_spinner));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(arrayAdapter);
        Spinner categoryspinner=dialog.findViewById(R.id.category_spinner);
        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.category_spinner));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspinner.setAdapter(arrayAdapter1);
        Button picturebutton=dialog.findViewById(R.id.take_pictures);

        picturebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeVisionHelper.ResetDB();
                Intent intent=new Intent(MainActivity.this,IvyIRActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation_;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


        closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


}
