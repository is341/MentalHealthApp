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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Registration extends AppCompatActivity {

    TextView tvsignin;
    RadioGroup rdGroup;
    TextInputLayout usernameTIL, emailTIL, passTIL, phoneTIL, fullnameTIL, designationTIL;
    ProgressBar pBar;

    String img_path = "https://static01.nyt.com/images/2018/08/28/us/28vote_print/28vote_xp-articleLarge.jpg";
    String user_type = "patient";
    String TAG = "RegistrationAct_tags";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        String text = "<font color=##91c1d>Don't have an account? Click here to </font> <font color=#0677F6>Sign In</font>";
        tvsignin = findViewById(R.id.tv_signin);
        tvsignin.setText(Html.fromHtml(text));


        usernameTIL = findViewById(R.id.text_input_layout_username);
        emailTIL = findViewById(R.id.text_input_layout_email);
        passTIL = findViewById(R.id.text_input_layout_password);
        phoneTIL = findViewById(R.id.text_input_layout_phone);
        fullnameTIL = findViewById(R.id.text_input_layout_fullname);
        designationTIL = findViewById(R.id.text_input_layout_designation);
        pBar=   findViewById(R.id.pBar);

        rdGroup = findViewById(R.id.rdGroup);

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdDoctor)
                    user_type = "doctor";
                if (checkedId == R.id.rdPatient)
                    user_type = "patient";

                Log.d(TAG, "onCheckedChanged: : " + user_type);

            }
        });
    }

    public void register(View view) {
        try{

            String email=emailTIL.getEditText().getText().toString().trim();
            String pass=passTIL.getEditText().getText().toString().trim();
            String username=usernameTIL.getEditText().getText().toString().trim();
            String phone=phoneTIL.getEditText().getText().toString().trim();
            String fullname=fullnameTIL.getEditText().getText().toString().trim();
            String designation=designationTIL.getEditText().getText().toString().trim();
            if (email.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            }
            else
            if (pass.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            }
            else
            if (username.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your username", Toast.LENGTH_SHORT).show();
            }
            else
            if (phone.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your phone number", Toast.LENGTH_SHORT).show();
            }
            else
            if (fullname.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your Full Name", Toast.LENGTH_SHORT).show();
            }
            else
            if (designation.isEmpty()){
                Toast.makeText(Registration.this, "Please Enter Your designation", Toast.LENGTH_SHORT).show();
            }else{
                OKHttpRequests();
            }
        }catch (NullPointerException e){e.printStackTrace();
            Toast.makeText(Registration.this, "Please Enter Your Email and Password", Toast.LENGTH_SHORT).show();

        }
    }




    private void OKHttpRequests() {

        String email=emailTIL.getEditText().getText().toString().trim();
        String pass=passTIL.getEditText().getText().toString().trim();
        String username=usernameTIL.getEditText().getText().toString().trim();
        String phone=phoneTIL.getEditText().getText().toString().trim();
        String fullname=fullnameTIL.getEditText().getText().toString().trim();
        String designation=designationTIL.getEditText().getText().toString().trim();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email)
                .addFormDataPart("username", username)
                .addFormDataPart("password", pass)
                .addFormDataPart("full_name", fullname)
                .addFormDataPart("designation", designation)
                .addFormDataPart("phone", phone)
                .addFormDataPart("img_path", img_path)
                .addFormDataPart("user_type", user_type)
                .build();
        BackGroundTasks task = new BackGroundTasks(Registration.this, Constants.Register, requestBody);

        task.execute();

    }

    private void OKHttpRequests(String email,String pass) {
        pBar.setVisibility(View.VISIBLE);


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", email)
                .addFormDataPart("password", pass)
                .build();
        BackGroundTasks task = new BackGroundTasks(Registration.this, Constants.SignIn, requestBody);

        task.execute();

    }

    public void login_screen(View view) {
        startActivity(new Intent(Registration.this,Login.class));
        finish();
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

            Log.d(TAG, "onPreExecute: ");
            pBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            try {


                OkHttpClient httpClient = new OkHttpClient();

                okhttp3.Request request;

                Log.d(TAG, httpClient.connectTimeoutMillis() + "");
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

                Log.d(TAG, "" + resss);
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
            Log.d(TAG, "" + s);

            processFinish(s);


        }
    }

    private void processFinish(String json_string) {

        JSONArray jsonArray;
        jsonArray = new JSONArray();


        Log.d(TAG, json_string);

        try {
            JSONObject object;
            object = new JSONObject(json_string);

            IfMethods(object);

        } catch (JSONException e) {
            e.printStackTrace();

            pBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "exception : " + e);
        }
    }


    private void IfMethods(JSONObject object) {



        String Tag=TAG;
        Log.d(TAG, "data : " + object.toString());
        try {
            if (object.toString().contains("Username already exist")){
                passTIL.getEditText().setError("Required");
                usernameTIL.getEditText().setText("");


            }else if(object.toString().contains("Success")){
                OKHttpRequests(usernameTIL.getEditText().getText().toString(),passTIL.getEditText().getText().toString());
            }else
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


                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("userId", id+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("username", username+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("full_name", full_name+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("designation", designation+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("email", email+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("phone", phone+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("img_path", img_path+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("user_type", user_type+"").apply();
                PreferenceManager.getDefaultSharedPreferences(Registration.this).edit().putString("quiz_status", quiz_status+"").apply();


                startActivity(new Intent(Registration.this,MainActivity.class));
                finishAffinity();
                finish();


            }else {

                Log.d(TAG,"task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Registration.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

    }
}