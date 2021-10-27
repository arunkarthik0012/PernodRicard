package com.example.ferreroimagerecognition;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailabilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView avail_recycler_view,avail_recycler_view_1;
    private TextView adherence;
    private StoreVisionBo storeVisionBo;
    private ArrayList<StoreVisionBo> availlist=new ArrayList<>();
    ArrayList<StoreVisionBo>  availlist2=new ArrayList<>(availlist.size());
    public StoreVisionHelper storeVisionHelper;
    private AvailabilityAdapter availabilityAdapter;
    Context context;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvailabilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailabilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailabilityFragment newInstance(String param1, String param2) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        storeVisionHelper=new StoreVisionHelper(getContext());

//        for (StoreVisionBo assetBO : availlist) {
//            availlist2.add(new StoreVisionBo(assetBO));
//        }
//        availlist=bmodel.availItems1;

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_availability, container, false);
//        ((ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        return view;    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avail_recycler_view=view.findViewById(R.id.availability_recylerview);
        avail_recycler_view_1=view.findViewById(R.id.availability_recylerview1);
        adherence=view.findViewById(R.id.adherence);
        

    }

    @Override
    public void onResume() {
        super.onResume();

        avail_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        availlist=storeVisionHelper.loadCategorySmartVision();
        availlist=storeVisionHelper.loadCategorySmartVision();
        avail_recycler_view.setAdapter(new AvailabilityAdapter(availlist));
        avail_recycler_view_1.setLayoutManager(new LinearLayoutManager(getContext()));
        availlist2=storeVisionHelper.loadSkuSmartVision();
        availlist2=storeVisionHelper.loadSkuSmartVision();
        avail_recycler_view_1.setAdapter(new AvailabilitySkuAdapter(availlist2));

        adherence.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
//        SharedPreferences sharedPreferences= context.getSharedPreferences("",Context.MODE_PRIVATE);

    }
}