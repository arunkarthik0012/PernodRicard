package com.example.ferreroimagerecognition;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ferreroimagerecognition.MainActivity.adherence;
import static com.example.ferreroimagerecognition.MainActivity.sumOos;
import static com.example.ferreroimagerecognition.MainActivity.sumOpportunity;

public class StoreVisionHelper {

    private Context mContext;
    private Activity ctx;
    DBUtil db;





    public StoreVisionHelper(Context context) {
        this.mContext = context;
        this.db = new DBUtil(mContext,DBUtil.MASTER_DB_NAME );
        db.createDataBase();

    }

    public ArrayList<StoreVisionBo> loadCategorySmartVision(){
         ArrayList<StoreVisionBo>  availItems=new ArrayList<>();
        try {
            db.openDataBase();

            String sql="select sv.category,sum(svp.FacingTarget) as facing, sum(svp.count) as Availabity, sv.OutofStock,sum(svp.Opportunity) as oppprotunities from smartvision sv LEFT JOIN SmartVisionProductMaster svp WHERE svp.parentid = 18";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
                while (c.moveToNext()){
                    StoreVisionBo storeVisionBo=new StoreVisionBo();
                    storeVisionBo.setCategory(c.getString(0));
                    storeVisionBo.setNooffacing(c.getInt(1));
                    storeVisionBo.setAvailability(c.getInt(2));
                    storeVisionBo.setOutofstock(c.getInt(2)-c.getInt(1));
                    Integer oos=c.getInt(2)-c.getInt(1);
                    Log.d("oos", "loadCategorySmartVision: "+oos);
                    Log.d("opportuniy   ", "loadCategorySmartVision: "+c.getInt(4));
                    storeVisionBo.setOpportunity(oos*c.getInt(4));
                    availItems.add(storeVisionBo);
                }

            c.close();
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

        return availItems;
        }
    public ArrayList<StoreVisionBo> loadSkuSmartVision(){
        ArrayList<StoreVisionBo> categorylist=new ArrayList<>();
        try {

            db.openDataBase();
            String sql="select svp.pname, svp.FacingTarget,svp.count,(svp.FacingTarget-svp.count),svp.Opportunity as OutofStock from smartVisionProductMaster svp where svp.parentid !=0 ORDER BY svp.sequence";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
            sumOpportunity=0;
            sumOos=0;
            while (c.moveToNext()){
                 StoreVisionBo storeVisionBo=new StoreVisionBo();
                 storeVisionBo.setSku(c.getString(0));
                storeVisionBo.setNooffacing(c.getInt(1));
                storeVisionBo.setAvailability(c.getInt(2));
                int oosavaila=c.getInt(3);
                Log.d("oosavaila", "loadSkuSmartVision: "+oosavaila);
                if (c.getInt(3)>0){
                    storeVisionBo.setOutofstock(c.getInt(3));
                    sumOos=sumOos+c.getInt(3);
                    sumOpportunity=sumOpportunity+c.getInt(3)*c.getInt(4);
                }else {
                    storeVisionBo.setOutofstock(0);
                }
                storeVisionBo.setOpportunity(c.getInt(4));
                categorylist.add(storeVisionBo);
            }

            c.close();
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

        return categorylist;
    }
    public void UpdateOosFacing(JSONObject jsonObject){
        ArrayList<StoreVisionBo> updateoos=new ArrayList<>();

        try {
            db.openDataBase();
            for (int i=0;i<jsonObject.length();i++) {
                db.updateSQL("UPDATE SmartVisionProductMaster SET count= '" + jsonObject.get("count") + "'"+" WHERE pCode = '" + jsonObject.get("id").toString() + "'" +" AND parentid != 0" );
                db.updateSQL("UPDATE SmartVision set outofstock=(SELECT sum(svp.FacingTarget-svp.count) FROM SmartVisionProductMaster svp), nooffacing=(SELECT sum(svp.FacingTarget) FROM SmartVisionProductMaster svp)");

            }
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

    }


    public ArrayList<StoreVisionBo> loadbrandSmartshelfVision(){
        ArrayList<StoreVisionBo> smartrecyclerview1=new ArrayList<>();
        try {
            db.openDataBase();
            String sql="select svp.pname,svp.sosTarget,svp.Opportunity,svp.AcctualTarget from SmartVisionProductMaster svp where ParentID='0' ORDER BY svp.sequence";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
            while (c.moveToNext()){
                StoreVisionBo storeVisionBo=new StoreVisionBo();
                storeVisionBo.setBrand(c.getString(0));
                storeVisionBo.setSos(c.getInt(1));
                storeVisionBo.setOpportunity(c.getInt(2));
                storeVisionBo.setGap(c.getInt(3));


                smartrecyclerview1.add(storeVisionBo);
            }

            c.close();
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

        return smartrecyclerview1;
    }

    public void updateBrandSOS(JSONObject jsonObject){
        try {
            db.openDataBase();
                db.updateSQL("Update smartvisionProductMaster set length='" + jsonObject.get("number") + "'"+" WHERE pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND parentid ==0");
                db.updateSQL("UPDATE SmartVisionProductMaster set SosTarget=(SELECT(svp.length-svp.SosTarget) FROM SmartVision sv LEFT JOIN SmartVisionProductMaster svp where svp.pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND svp.parentid ==0) "+" where pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND parentid ==0");
                db.updateSQL("Update SmartVisionProductMaster set AcctualTarget=(SELECT (sv.Opportunities-svp.SosTarget)From SmartVision sv LEFT JOIN SmartVisionProductMaster svp where svp.pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND svp.parentid ==0)"+" where pCode="+"'"+jsonObject.get("id").toString()+"'"+" AND parentid ==0");
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }
    }


    public ArrayList<StoreVisionBo> loadCategorySmartshelfVision(){
        ArrayList<StoreVisionBo> smartcategorylist=new ArrayList<>();
        try {
            db.openDataBase();
            String sql="select category,svp.SOsTarget,opportunities ,svp.AcctualTarget from SmartVision LEFT JOIN SmartVisionProductMaster svp where ParentID='0' AND pCode='fr'";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
            while (c.moveToNext()){
                StoreVisionBo storeVisionBo=new StoreVisionBo();
                storeVisionBo.setCategory(c.getString(0));
                storeVisionBo.setSos(c.getInt(1));
                storeVisionBo.setOpportunity(c.getInt(2));
                storeVisionBo.setGap(c.getInt(3));


                smartcategorylist.add(storeVisionBo);
            }

            c.close();
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

        return smartcategorylist;
    }
    public ArrayList<StoreVisionBo> loadPlanogramCompailance(){

        ArrayList<StoreVisionBo> planoRecyclerview=new ArrayList<>();
        try {
            db.openDataBase();
            String sql="select svp.pname, svp.compliance from SmartVisionProductMaster svp where ParentID='0'";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
            while (c.moveToNext()){
                StoreVisionBo storeVisionBo=new StoreVisionBo();
                storeVisionBo.setBrand(c.getString(0));
                storeVisionBo.setScore(c.getInt(1));
                planoRecyclerview.add(storeVisionBo);
            }

            c.close();
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }

        return planoRecyclerview;

    }

    public void updateCompliance(JSONObject jsonObject){
        try {
            db.openDataBase();
            adherence=jsonObject.getInt("score");
            db.updateSQL("Update smartvisionProductMaster set compliance='" + jsonObject.get("score") + "'"+" WHERE pCode ='fr'");
//            db.updateSQL("UPDATE SmartVisionProductMaster set SosTarget=(SELECT(svp.length-svp.SosTarget) FROM SmartVision sv LEFT JOIN SmartVisionProductMaster svp) where ParentID='0'");
//            db.updateSQL("Update SmartVisionProductMaster set AcctualTarget=(SELECT (sv.Opportunities-svp.SosTarget)From SmartVision sv LEFT JOIN SmartVisionProductMaster svp)where ParentID='0'");
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }
    }
    public void ResetDB(){
        try {
            db.openDataBase();

                db.updateSQL("UPDATE SmartVisionProductMaster SET count= 0, AcctualTarget=0,SosTarget=0,compliance=0");


            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }
    }

}
