package com.example.ferreroimagerecognition;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ferreroimagerecognition.MainActivity.adherence;

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

            String sql="select sv.category,sv.nooffacing, sum(svp.count) as Availabity, sv.OutofStock from smartvision sv LEFT JOIN SmartVisionProductMaster svp";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
                while (c.moveToNext()){
                    StoreVisionBo storeVisionBo=new StoreVisionBo();
                    storeVisionBo.setCategory(c.getString(0));
                    storeVisionBo.setNooffacing(c.getInt(1));
                    storeVisionBo.setAvailability(c.getInt(2));
                    storeVisionBo.setOutofstock(c.getInt(3));



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
            String sql="select svp.pname, svp.FacingTarget,svp.count,(svp.FacingTarget-svp.count) as OutofStock from smartVisionProductMaster svp where svp.FacingTarget>0 ORDER BY svp.sequence";
            Cursor c = db.selectSQL(sql);
            Log.d("Store Vision Helper",c.getCount()+"");
            while (c.moveToNext()){
                 StoreVisionBo storeVisionBo=new StoreVisionBo();
                 storeVisionBo.setSku(c.getString(0));
                storeVisionBo.setNooffacing(c.getInt(1));
                storeVisionBo.setAvailability(c.getInt(2));
                storeVisionBo.setOutofstock(c.getInt(3));



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
            String sql="select svp.pname,svp.sosTarget,sv.opportunities,svp.AcctualTarget from SmartVision sv LEFT JOIN SmartVisionProductMaster svp where ParentID='0'";
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
                db.updateSQL("UPDATE SmartVisionProductMaster set SosTarget=(SELECT(svp.length-svp.SosTarget) FROM SmartVision sv LEFT JOIN SmartVisionProductMaster svp where pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND parentid ==0)");
                db.updateSQL("Update SmartVisionProductMaster set AcctualTarget=(SELECT (sv.Opportunities-svp.SosTarget)From SmartVision sv LEFT JOIN SmartVisionProductMaster svp where pCode ="+"'"+jsonObject.get("id").toString()+"'"+" AND parentid ==0)");
            db.closeDB();

        }catch (Exception e) {
            Commons.printException(e);
        }
    }


    public ArrayList<StoreVisionBo> loadCategorySmartshelfVision(){
        ArrayList<StoreVisionBo> smartcategorylist=new ArrayList<>();
        try {
            db.openDataBase();
            String sql="select DISTINCT category,svp.SOsTarget,opportunities ,svp.AcctualTarget from SmartVision LEFT JOIN SmartVisionProductMaster svp where ParentID='0'";
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
            db.updateSQL("Update smartvisionProductMaster set compliance='" + jsonObject.get("score") + "'"+" WHERE pname ='Toblerone'");
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
