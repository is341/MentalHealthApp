package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Login extends AppCompatActivity {
    TextView tvsignup;
    TextInputLayout emailTIL, passTIL;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setTitle("Login ");
        getSupportActionBar().hide();

        String text = "<font color=##91c1d>Don't have an account? Click here to </font> <font color=#0677F6>Sign up</font>";
        tvsignup=   findViewById(R.id.tv_signup1);
        emailTIL=   findViewById(R.id.text_input_layout_username);
        pBar=   findViewById(R.id.pBar);
        passTIL=   findViewById(R.id.text_input_layout_password);
        tvsignup.setText(Html.fromHtml(text));




    }

    public void registration_screen(View view) {
        Intent intent = new Intent(Login.this,Registration.class);
        startActivity(intent);
        finish();
    }


    public void loginMethod(View view) {
        try{

            String email=emailTIL.getEditText().getText().toString().trim();
            String pass=passTIL.getEditText().getText().toString().trim();
            if (email.isEmpty()){
                Toast.makeText(Login.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            }
            else
            if (pass.isEmpty()){
                Toast.makeText(Login.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            }else{
                OKHttpRequests(email,pass);
            }
        }catch (NullPointerException e){e.printStackTrace();
            Toast.makeText(Login.this, "Please Enter Your Email and Password", Toast.LENGTH_SHORT).show();

        }

    }

    private void OKHttpRequests(String email,String pass) {


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", email)
                .addFormDataPart("password", pass)
                .build();
        BackGroundTasks task = new BackGroundTasks(Login.this, Constants.SignIn, requestBody);

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



        String Tag = "tstng_SignInAct";
        Log.d(Tag, "data : " + object.toString());
        try {
            if (object.toString().contains("wrong credentials")){
                passTIL.getEditText().setError("Required");
                passTIL.getEditText().setText("");

            }
            if (object.getString("Response") != null) {


                    JSONObject JO_user = new JSONObject(object.getString("Response"));

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

//                int arr_size = 0;
//                JSONArray user_Arr = object.getJSONArray("signup_records");
//                Log.d(Tag, "Arr : " + user_Arr.toString());
//
//                String showroom_id="";
//
//
//                while (arr_size < user_Arr.length()) {
//
//                    JSONObject JO_user = user_Arr.getJSONObject(arr_size);
//
//                    showroom_id = JO_user.getString("signup_id");
//                    Log.d(Tag, "signup_id : " + showroom_id);
//
//
//
//                    arr_size++;
//                }
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("userId", id+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("username", username+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("full_name", full_name+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("designation", designation+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("email", email+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("phone", phone+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("img_path", img_path+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("user_type", user_type+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Login.this).edit().putString("quiz_status", quiz_status+"").apply();


                startActivity(new Intent(Login.this,MainActivity.class));
                finishAffinity();
                finish();


            }else {

                Log.d("tstng_SignUpAct","task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

    }



}