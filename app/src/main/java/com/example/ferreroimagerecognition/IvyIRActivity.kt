package com.example.ferreroimagerecognition

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sukshi.inventorymanagementlib.Activities.SukshiCameraActivity
import com.sukshi.inventorymanagementlib.Networking.SukshiNetworkHelper
import com.sukshi.inventorymanagementlib.SukshiError
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.URI
import java.security.AccessController.getContext

class IvyIRActivity : AppCompatActivity() {

    lateinit var storevisionhelper:StoreVisionHelper



    companion object {
        val ksmsBUNDLE_IMAGE_URI = "BUNDLE_IMAGE_URI"
        val ksmsBUNDLE_CENTER_IMAGE_URI = "BUNDLE_CENTER_IMAGE_URI"
        val ksmsBUNDLE_CICKED_IMAGE_ID = "BUNDLE_CICKED_IMAGE_ID"


        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    }

    var currentClickedImageView: ImageButton? = null
    var centerImageURI: String? = null
    var photoImageMap = mutableMapOf<Int, String?>()
    var rootGridLayout: GridLayout? = null
    lateinit var progressDialog:ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ivy_iractivity)

        storevisionhelper = StoreVisionHelper(this.applicationContext)
        rootGridLayout = findViewById<GridLayout>(R.id.root_grid);
        enableDisableImageButtons()
        val sukshiNetworkHelper = SukshiNetworkHelper(this)
        sukshiNetworkHelper.initSDK(this, "ivy_ferrero") { message, isSuccess ->
            //Note: proceed with your flow
            Log.d("IVYIRActivity", "message {$message} -->{$isSuccess}")
        }

        progressDialog= ProgressDialog(this)
        progressDialog.setContentView(R.layout.progress_dailog)


    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    fun onClick(view: View?) {
        currentClickedImageView = view as ImageButton?

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

    }
    fun startCamera() {
        var params =  JSONObject();
        params.put("aspect_ratio", "horizontal") //string “vertical” for featuretype Price Check, remaining
        params.put("shouldShowReviewScreen", true); // boolean
        params.put("shouldSetPadding", true); // boolean
        params.put("padding", 0.05); // double value
        params.put("front_view", false); // boolean
        params.put("device_model", "OnePlus5");
        SukshiCameraActivity.start(applicationContext, { sukshiError: SukshiError?,
                                                         jsonObject: JSONObject? ->
            Log.d("IRActivity", jsonObject.toString())
//start loading
            progressDialog.show();
            progressDialog.setMessage("Processing Image...")
            currentClickedImageView?.setImageURI(Uri.fromFile(File(jsonObject?.getString("imageUri"))))
            uploadFile(jsonObject)

        }, params);
    }

    fun uploadFile(responseJson: JSONObject?){
        val params = JSONObject()
        val header = JSONObject()
        params.put("app_id", "ivy_ferrero") //string
        /*
        switch (featuretype) {
    case "Availability & Count":
        featuretype = "count";
        break;
    case "Share of Shelf":
        featuretype = "sos";
        break;
    case "Price check":
        featuretype = "price_check";
        break;
    case "Compliance":
        featuretype = "compliance";
        break;
}
         */
        params.put("type", "count") // string select feature type
        header.put("referenceId", "A1")

        if (responseJson!!.has("imageUri")) {
            val sukshiNetworkHelper = SukshiNetworkHelper(applicationContext)
            sukshiNetworkHelper.makeApiCallforFileupload(
                responseJson?.getString("imageUri"),
                params,
                header
            ) { error, result ->
                if (error == null) {
                    //Handle result

                    handleSuccessResponse(result)
                    uploadFileForSOS(responseJson)
                    Log.d("IRActivity", "Success:" + result.toString())

                } else {
                    //Handle error
                    Log.d("IRActivity", "Error:" + error.toString())
                }
            }
        }

    }
    fun uploadFileForSOS(responseJson: JSONObject?){
        val params = JSONObject()
        val header = JSONObject()
        params.put("app_id", "ivy_ferrero") //string
        /*
        switch (featuretype) {
    case "Availability & Count":
        featuretype = "count";
        break;
    case "Share of Shelf":
        featuretype = "sos";
        break;
    case "Price check":
        featuretype = "price_check";
        break;
    case "Compliance":
        featuretype = "compliance";
        break;
}
         */
        params.put("type", "sos") // string select feature type
        header.put("referenceId", "A1")

        if (responseJson!!.has("imageUri")) {
            val sukshiNetworkHelper = SukshiNetworkHelper(applicationContext)
            sukshiNetworkHelper.makeApiCallforFileupload(
                responseJson?.getString("imageUri"),
                params,
                header
            ) { error, result ->
                if (error == null) {
                    //Handle result
                    Log.d("Suucess", "uploadFileForSOS: "+result)
                    handleSuccessResponseForSOS(result)
                    uploadFileForCompliance(responseJson)
                    //hide dailog loading
                    progressDialog.dismiss()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Log.d("IRActivity", "Success:" + result.toString())

                } else {
                    //Handle error
                    Log.d("IRActivity", "Error:" + error.toString())
                }
            }
        }

    }

    fun uploadFileForCompliance(responseJson: JSONObject?){
        val params = JSONObject()
        val header = JSONObject()
        params.put("app_id", "ivy_ferrero") //string
        /*
        switch (featuretype) {
    case "Availability & Count":
        featuretype = "count";
        break;
    case "Share of Shelf":
        featuretype = "sos";
        break;
    case "Price check":
        featuretype = "price_check";
        break;
    case "Compliance":
        featuretype = "compliance";
        break;
}
         */
        params.put("type", "compliance") // string select feature type
        header.put("referenceId", "A1")

        if (responseJson!!.has("imageUri")) {
            val sukshiNetworkHelper = SukshiNetworkHelper(applicationContext)
            sukshiNetworkHelper.makeApiCallforFileupload(
                responseJson?.getString("imageUri"),
                params,
                header
            ) { error, result ->
                if (error == null) {
                    //Handle result
                    handleSuccessResponseForCompliance(result)
                    //hide dailog loading
                    progressDialog.dismiss()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Log.d("IRActivity", "Success:" + result.toString())

                } else {
                    //Handle error
                    Log.d("IRActivity", "Error:" + error.toString())
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            // If all permissions granted , then start Camera
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                // If permissions are not granted,
                // present a toast to notify the user that
                // the permissions were not granted.
                Toast.makeText(
                    this, "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    fun handleSuccessResponse(responseJson: JSONObject?)  {
        if (responseJson!!.has("status_code")){
            var response_code = responseJson!!.getInt("status_code")
            if (response_code == 200){
                //Parse the remaining data here
                if (responseJson!!.has("details")){
                    var detailsArray: JSONArray = responseJson!!.getJSONArray("details")

                    if (detailsArray.length() > 0) {
//                        for (index in 0..detailsArray.length()-1) {
                            var detailsObj = detailsArray.getJSONObject(0)

                            if (detailsObj.has("tag")) {
                                //detailsObj.getString("tag")
                            }

                            //Parse compliance Object here
                            if (detailsObj.has("compliance")) {
                                var compilanceObj = detailsObj.getJSONObject("compliance")
                                if (compilanceObj != null) {
                                    if (compilanceObj.has("score")) {
                                        //compilanceObj.getInt("score")

                                    }
                                    if (compilanceObj.has("message")) {
                                        compilanceObj.getString("message")

                                    }

                                }
                            }

                            //Parse compliance SOS here
                            if (detailsObj.has("sos")) {
                                var sosObj = detailsObj.getJSONObject("sos")
                                if (sosObj != null) {
                                    if (sosObj.has("number")) {
                                        //sosObj.getInt("number")

                                    }
                                    if (sosObj.has("length")) {
                                        // sosObj.getInt("length")

                                    }

                                }
                            }


                            //Parse Price check here
                            if (detailsObj.has("price_check")) {
                                var price_checkObj = detailsObj.getJSONObject("price_check")
                                if (price_checkObj != null) {
                                    if (price_checkObj.has("id")) {
                                        Log.d("IRActivity", price_checkObj.getString("id"))

                                    }
                                    if (price_checkObj.has("message")) {
                                        Log.d("IRActivity", price_checkObj.getString("message"))

                                    }

                                }
                            }

                            //Parse products here
                            if (detailsObj.has("products")) {
                                var productsArray: JSONArray =
                                    detailsObj.getJSONArray("products")
                                for (index in 0..productsArray.length() - 1) {
                                    var productObj = productsArray.getJSONObject(index)
                                    if (productObj!!.has("id")) {
                                        Log.d(
                                            "IRActivity",
                                            productObj.getString("id")
                                        ) // join with in Pcode
                                    }
                                    if (productObj!!.has("count")) {
                                        if (productObj.get("count") is Int)
                                            Log.d(
                                                "IRActivity",
                                                productObj.getInt("count").toString()
                                            ) // join with in Pcode

                                        storevisionhelper.UpdateOosFacing(productObj)
                                    }
                                }


                            }
//                        }
                    }


                }
            }
//            storevisionhelper.loadSkuSmartVision()
        }else {
            Log.d("IRActivity", "Response Error")
        }



    }


    fun handleSuccessResponseForSOS(responseJson: JSONObject?)  {
        if (responseJson!!.has("status_code")){
            var response_code = responseJson!!.getInt("status_code")
            if (response_code == 200){
                //Parse the remaining data here
                if (responseJson!!.has("details")){
                    var detailsArray: JSONArray = responseJson!!.getJSONArray("details")
                    if (detailsArray.length() > 0) {
//                        for(index in 0..detailsArray.length()-1) {
                            var detailsObj = detailsArray.getJSONObject(0)

                            if (detailsObj.has("tag")) {
                                //detailsObj.getString("tag")
                            }

                            //Parse compliance Object here
                            if (detailsObj.has("compliance")) {
                                var compilanceObj = detailsObj.getJSONObject("compliance")
                                if (compilanceObj != null) {
                                    if (compilanceObj.has("score")) {
                                        //compilanceObj.getInt("score")

                                    }
                                    if (compilanceObj.has("message")) {
                                        compilanceObj.getString("message")

                                    }

                                }
                            }


                            //Parse compliance SOS here
//                            if (detailsObj.has("sos")) {
//                                var sosObj = detailsObj.getJSONObject("sos")
//                                if (sosObj != null) {
//                                    if (sosObj.has("number")) {
//                                        //sosObj.getInt("number")
//
//                                    }
//                                    if (sosObj.has("length")) {
//                                        // sosObj.getInt("length")
//
//
//                                    }
//                                    storevisionhelper.updateBrandSOS(sosObj)
//                                }
//                            }


                        if (detailsObj.has("sos")) {
                            val productsArray: JSONArray = detailsObj.getJSONArray("sos")
                            for (index in 0..productsArray.length() - 1) {
                                val sosObj = productsArray.getJSONObject(index)
                                if (sosObj!!.has("id")) {
                                    Log.d("IRActivity", sosObj.getString("id")) // join with in Pcode
                                }
                                storevisionhelper.updateBrandSOS(sosObj)

                            }


                        }


                        //Parse Price check here
                            if (detailsObj.has("price_check")) {
                                var price_checkObj = detailsObj.getJSONObject("price_check")
                                if (price_checkObj != null) {
                                    if (price_checkObj.has("id")) {
                                        Log.d("IRActivity", price_checkObj.getString("id"))

                                    }
                                    if (price_checkObj.has("message")) {
                                        Log.d("IRActivity", price_checkObj.getString("message"))

                                    }

                                }
                            }

                            //Parse products here
                            if (detailsObj.has("products")) {
                                var productsArray: JSONArray =
                                    detailsObj.getJSONArray("products")
                                for (index in 0..productsArray.length() - 1) {
                                    var productObj = productsArray.getJSONObject(index)
                                    if (productObj!!.has("id")) {
                                        Log.d(
                                            "IRActivity",
                                            productObj.getString("id")
                                        ) // join with in Pcode
                                    }
                                    if (productObj!!.has("count")) {
                                        if (productObj.get("count") is Int)
                                            Log.d(
                                                "IRActivity",
                                                productObj.getInt("count").toString()
                                            ) // join with in Pcode
                                    }
                                }


                            }
//                        }
                    }


                }
            }
//            storevisionhelper.loadSkuSmartVision()
        }else {
            Log.d("IRActivity", "Response Error")
        }



    }


    fun handleSuccessResponseForCompliance(responseJson: JSONObject?)  {
        if (responseJson!!.has("status_code")){
            var response_code = responseJson!!.getInt("status_code")
            if (response_code == 200){
                //Parse the remaining data here
                if (responseJson!!.has("details")){
                    var detailsArray: JSONArray = responseJson!!.getJSONArray("details")
                    if (detailsArray.length() > 0) {
//                        for(index in 0..detailsArray.length()-1) {
                        var detailsObj = detailsArray.getJSONObject(0)

                        if (detailsObj.has("tag")) {
                            //detailsObj.getString("tag")
                        }

                        //Parse compliance Object here
                        if (detailsObj.has("compliance")) {
                            var compilanceObj = detailsObj.getJSONObject("compliance")
                            if (compilanceObj != null) {
                                if (compilanceObj.has("score")) {
                                    //compilanceObj.getInt("score")

                                }
                                if (compilanceObj.has("message")) {
                                    compilanceObj.getString("message")

                                }
                                storevisionhelper.updateCompliance(compilanceObj)

                            }
                        }


                        //Parse compliance SOS here
                        if (detailsObj.has("sos")) {
                            var sosObj = detailsObj.getJSONObject("sos")
                            if (sosObj != null) {
                                if (sosObj.has("number")) {
                                    //sosObj.getInt("number")

                                }
                                if (sosObj.has("length")) {
                                    // sosObj.getInt("length")


                                }

                            }
                        }


                        //Parse Price check here
                        if (detailsObj.has("price_check")) {
                            var price_checkObj = detailsObj.getJSONObject("price_check")
                            if (price_checkObj != null) {
                                if (price_checkObj.has("id")) {
                                    Log.d("IRActivity", price_checkObj.getString("id"))

                                }
                                if (price_checkObj.has("message")) {
                                    Log.d("IRActivity", price_checkObj.getString("message"))

                                }

                            }
                        }

                        //Parse products here
                        if (detailsObj.has("products")) {
                            var productsArray: JSONArray =
                                detailsObj.getJSONArray("products")
                            for (index in 0..productsArray.length() - 1) {
                                var productObj = productsArray.getJSONObject(index)
                                if (productObj!!.has("id")) {
                                    Log.d(
                                        "IRActivity",
                                        productObj.getString("id")
                                    ) // join with in Pcode
                                }
                                if (productObj!!.has("count")) {
                                    if (productObj.get("count") is Int)
                                        Log.d(
                                            "IRActivity",
                                            productObj.getInt("count").toString()
                                        ) // join with in Pcode
                                }
                            }


                        }
//                        }
                    }


                }
            }
//            storevisionhelper.loadSkuSmartVision()
        }else {
            Log.d("IRActivity", "Response Error")
        }



    }





    fun enableDisableImageButtons() {
        if (photoImageMap.size == 0) {
            // no images in the grid
            //Disable All the buttons
            val count: Int = rootGridLayout!!.getChildCount()
            for (i in 0 until count) {
                val child: ImageButton = rootGridLayout!!.getChildAt(i) as ImageButton
                child.isEnabled = false
            }
            //enable center button
            (findViewById<ImageButton>(R.id.iv_2x2) as ImageButton).isEnabled = true
        } else if ((photoImageMap.get(R.id.iv_2x2) != null) && (!isTopRightBottomLeftAdded())) {
            //if check
            val count: Int = rootGridLayout!!.getChildCount()
            for (i in 0 until count) {
                val child: ImageButton = rootGridLayout!!.getChildAt(i) as ImageButton
                when (child.id) {
                    R.id.iv_1x2, R.id.iv_2x1 ->
                        child.isEnabled = (photoImageMap.get(child.id) == null)
                    else ->
                        child.isEnabled = false

                }
            }
        } else if (isTopRightBottomLeftAdded()) {
            //enable Other ImageButtons one by one
            val count: Int = rootGridLayout!!.getChildCount()
            for (i in 0 until count) {
                val child: ImageButton = rootGridLayout!!.getChildAt(i) as ImageButton
                when (child.id) {
                    R.id.iv_1x1 ->
                        if ((photoImageMap.get(child.id) == null)) {
                            child.isEnabled = true
                        } else {
                            child.isEnabled = false
                        }

                    else ->
                        child.isEnabled = false
                }
            }

        }
    }

    fun isTopRightBottomLeftAdded(): Boolean {
        val count: Int = rootGridLayout!!.getChildCount()
        var isStarComplete = true;
        for (i in 0 until count) {
            val child: ImageButton = rootGridLayout!!.getChildAt(i) as ImageButton
            when (child.id) {
                R.id.iv_1x2, R.id.iv_2x1, R.id.iv_2x2 ->
                    if (photoImageMap.get(child.id) == null) {
                        isStarComplete = false
                        break

                    }
            }
        }
        return isStarComplete
    }

    override fun onDestroy() {
        super.onDestroy()
        photoImageMap.clear()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}