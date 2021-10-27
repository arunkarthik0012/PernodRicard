package com.example.ferreroimagerecognition;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SmartshelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmartshelfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ArrayList<StoreVisionBo> smartBrand=new ArrayList<>();
    public ArrayList<StoreVisionBo> smartCategory=new ArrayList<>();
    public ArrayList<StoreVisionBo> smartPlanogram=new ArrayList<>();
    public StoreVisionHelper storeVisionHelper;
    private RecyclerView avail_recycler_view,avail_recycler_view_1,Plano_recycler;
    Context context;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SmartshelfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmartshelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SmartshelfFragment newInstance(String param1, String param2) {
        SmartshelfFragment fragment = new SmartshelfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeVisionHelper=new StoreVisionHelper(getContext());
        context=getContext();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_smartshelf, container, false);
//        ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avail_recycler_view=view.findViewById(R.id.smart_shelf_recylerview);
        avail_recycler_view_1=view.findViewById(R.id.smart_shelf_recylerview1);
        Plano_recycler=view.findViewById(R.id.plano_shelf_recylerview);

    }

    @Override
    public void onResume() {
        super.onResume();
        avail_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        smartBrand=storeVisionHelper.loadbrandSmartshelfVision();
        smartBrand=storeVisionHelper.loadbrandSmartshelfVision();
        smartBrand=storeVisionHelper.loadbrandSmartshelfVision();
        avail_recycler_view.setAdapter(new SmartBrandAdapter(smartBrand));
        avail_recycler_view_1.setLayoutManager(new LinearLayoutManager(getContext()));
        smartCategory=storeVisionHelper.loadCategorySmartshelfVision();
        smartCategory=storeVisionHelper.loadCategorySmartshelfVision();
        avail_recycler_view_1.setAdapter(new SmartCategoryAdapter(smartCategory));
        smartPlanogram=storeVisionHelper.loadPlanogramCompailance();
        smartPlanogram=storeVisionHelper.loadPlanogramCompailance();
        Plano_recycler.setAdapter(new PlanogramAdapter(smartPlanogram));
        Plano_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}