package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mental_health.Adapters.DoctorsAdapter;
import com.mental_health.dataModel.DataModelDoctors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DoctorsDirectory extends AppCompatActivity {



    String Tag = "tstng_Main_Chats";
    String user_type;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;

    DoctorsAdapter mAdapter;
    ArrayList<DataModelDoctors> data;
    ProgressBar pBar;
    boolean isDoc=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_directory);
        user_type= PreferenceManager.getDefaultSharedPreferences(DoctorsDirectory.this).getString("user_type", "");
        if (user_type.equals("doctor")){
            isDoc=true;
            getSupportActionBar().setTitle("Patients Directory");
        }
        else
            getSupportActionBar().setTitle("Doctors Directory");



        recyclerView =  findViewById(R.id.recyclerView1);
        pBar =  findViewById(R.id.pBar);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        data = new ArrayList<DataModelDoctors>();


        mAdapter = new DoctorsAdapter(data, DoctorsDirectory.this,isDoc);
        recyclerView.setAdapter(mAdapter);

        OKHttpRequests();

    }



    private void OKHttpRequests() {


        String url="";
        if (user_type.equals("doctor"))
            url=Constants.getAllPatients;
        else
            url=Constants.getAllDoctors;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("","")
                .build();
        Log.d(Tag, "OKHttpRequests: "+url);
        BackGroundTasks task = new BackGroundTasks(DoctorsDirectory.this, url);

        task.execute();

    }


    public class BackGroundTasks extends AsyncTask<String, Void, String> {


        RequestBody requestBody;
        Context ctx;
        String urlx;
        boolean isPost = false;


        private BackGroundTasks(Context ctx, String urlx, RequestBody requestBody) {

            this.ctx = ctx;
            this.urlx = urlx;
            this.requestBody = requestBody;
            isPost = true;


        }  private BackGroundTasks(Context ctx, String urlx) {

            this.ctx = ctx;
            this.urlx = urlx;
            this.requestBody = requestBody;
            isPost = false;


        }

        @Override
        protected void onPreExecute() {

            pBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            try {


                OkHttpClient httpClient = new OkHttpClient();

                okhttp3.Request request;

                Log.d("tstng_onPostExecute", httpClient.connectTimeoutMillis() + "");
//
                //  httpClient.setReadTimeout(15, TimeUnit.SECONDS);    // socket timeout

                if (isPost) {
                    request = new okhttp3.Request.Builder()
                            .url(urlx.trim())
                            .addHeader("Accept", "application/json")
                            .post(requestBody)
                            .build();
                } else {

                    request = new okhttp3.Request.Builder()
                            .url(urlx.trim())
                            .addHeader("Accept", "application/json")
                            .build();
                }

                okhttp3.Response response = httpClient.newCall(request).execute();

                String resss = "" + response.body().string();

                Log.d("tttttt", "" + resss);
                return "" + resss;
            } catch (IOException e) {
                e.printStackTrace();
                return "error : " + e;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pBar.setVisibility(View.INVISIBLE);
            Log.d("tstng_onPostExecute", "" + s);

            processFinish(s);


        }
    }

    private void processFinish(String json_string) {

        JSONArray jsonArray;
        jsonArray = new JSONArray();
        String tag = "tstng_SignInAct";

        Log.d(tag, json_string);

        try {
            JSONObject object;
            object = new JSONObject(json_string);

            IfMethods(object);

        } catch (JSONException e) {
            e.printStackTrace();

            pBar.setVisibility(View.INVISIBLE);
            Log.d(tag, "exception : " + e);
        }
    }


    private void IfMethods(JSONObject object) {



        Log.d(Tag, "data : " + object.toString());
        try {
//            if (object.toString().contains("wrong credentials")){
//                passTIL.getEditText().setError("Required");
//                passTIL.getEditText().setText("");
//
//            }
            if (object.getString("Response") != null) {



                int arr_size = 0;
                JSONArray user_Arr = object.getJSONArray("Response");
                Log.d(Tag, "Arr : " + user_Arr.toString());

                String showroom_id="";


                while (arr_size < user_Arr.length()) {

                    JSONObject JO_user = user_Arr.getJSONObject(arr_size);

                    String id = JO_user.getString("id");
                    Log.d(Tag, "id : " + id);

                    String username = JO_user.getString("username");
                    Log.d(Tag, "username : " + username);

                    String password = JO_user.getString("password");
                    Log.d(Tag, "password : " + password);

                    String full_name = JO_user.getString("full_name");
                    Log.d(Tag, "full_name : " + full_name);

                    String designation = JO_user.getString("designation");
                    Log.d(Tag, "designation : " + designation);

                    String email = JO_user.getString("email");
                    Log.d(Tag, "email : " + email);

                    String phone = JO_user.getString("phone");
                    Log.d(Tag, "phone : " + phone);

                    String img_path = JO_user.getString("img_path");
                    Log.d(Tag, "img_path : " + img_path);

                    String user_type = JO_user.getString("user_type");
                    Log.d(Tag, "user_type : " + user_type);

                    String quiz_status = JO_user.getString("quiz_status");
                    Log.d(Tag, "quiz_status : " + quiz_status);

                    data.add(new DataModelDoctors(id,img_path, full_name, designation, phone));


                    arr_size++;
                }

                mAdapter.notifyDataSetChanged();

//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("userId", id+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("username", username+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("full_name", full_name+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("designation", designation+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("email", email+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("phone", phone+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("img_path", img_path+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("user_type", user_type+"").apply();
//                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("quiz_status", quiz_status+"").apply();
//
//
//                startActivity(new Intent(Login.this,MainActivity.class));
//                finishAffinity();
//                finish();


            }else {

                Log.d("tstng_SignUpAct","task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(DoctorsDirectory.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

    }
}