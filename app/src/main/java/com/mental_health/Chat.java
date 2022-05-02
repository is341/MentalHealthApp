package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mental_health.dataModel.DataModelDoctors;
import com.mental_health.models.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class Chat extends AppCompatActivity {

    String id = "0";
    String uid = "0";
    String Tag = "Chat__Tstng";
    ProgressBar pBar;
    ChatView chatView;
    String doctorId = "";
    String patientId = "";
    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (getIntent().getStringExtra("name") != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
            id = getIntent().getStringExtra("id");
        }

        uid = PreferenceManager.getDefaultSharedPreferences(Chat.this).getString("userId", "0");

        pBar = findViewById(R.id.pBar);
        chatView = (ChatView) findViewById(R.id.chat_view);

         user_type = PreferenceManager.getDefaultSharedPreferences(Chat.this).getString("user_type", "");

        if (user_type.equals("doctor")) {
            doctorId = uid;
            patientId = id;
        } else {
            doctorId = id;
            patientId = uid;
        }


        Log.d(Tag, "onCreate: doctorId " + doctorId);
        Log.d(Tag, "onCreate: patientId " + patientId);


        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {

                Log.d(Tag, "sendMessage: "+chatMessage.getMessage());
                OKHttpRequestSend(chatMessage.getMessage());
                return true;
            }
        });

        OKHttpRequests();
    }


    private void OKHttpRequests() {

        String url = "?doctorId=" + doctorId + "&patientId=" + patientId;
        BackGroundTasks task = new BackGroundTasks(Chat.this, Constants.Chat + url);

        task.execute();

    }

    private void OKHttpRequestSend(String msg) {

        String imgPath = "https://static01.nyt.com/images/2018/08/28/us/28vote_print/28vote_xp-articleLarge.jpg";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("doctor_id", doctorId)
                .addFormDataPart("patient_id", patientId)
                .addFormDataPart("msg", msg)
                .addFormDataPart("image_path", imgPath)
                .addFormDataPart("sender", uid)
                .build();

        BackGroundTasks task = new BackGroundTasks(Chat.this, Constants.Chat, requestBody);

        task.execute();

    }

    private void processFinish(String json_string, boolean isPost) {

        JSONArray jsonArray;
        jsonArray = new JSONArray();

        Log.d(Tag, json_string);

        try {
            JSONObject object;
            object = new JSONObject(json_string);

            if (isPost)
                IfMethods(object);
            else
                IfMethodGet(object);

        } catch (JSONException e) {
            e.printStackTrace();

            pBar.setVisibility(View.INVISIBLE);
            Log.d(Tag, "exception : " + e);
        }
    }

    private void IfMethodGet(JSONObject object) {


        Log.d(Tag, "data : " + object.toString());
        try {

            if (object.getString("Response") != null) {


                int arr_size = 0;
                JSONArray user_Arr = object.getJSONArray("Response");
                Log.d(Tag, "Arr : " + user_Arr.toString());




                while (arr_size < user_Arr.length()) {

                    JSONObject JO = user_Arr.getJSONObject(arr_size);

                    String id = JO.getString("id");
                    Log.d(Tag, "id : " + id);

                    String doctor_id = JO.getString("doctor_id");
                    Log.d(Tag, "doctor_id : " + doctor_id);

                    String patient_id = JO.getString("patient_id");
                    Log.d(Tag, "patient_id : " + patient_id);

                    String msg = JO.getString("msg");
                    Log.d(Tag, "msg : " + msg);

                    String image_path = JO.getString("image_path");
                    Log.d(Tag, "image_path : " + image_path);

                    String msg_time = JO.getString("msg_time");
                    Log.d(Tag, "msg_time : " + msg_time);

                    String img_status = JO.getString("img_status");
                    Log.d(Tag, "img_status : " + img_status);

                    String status = JO.getString("status");
                    Log.d(Tag, "status : " + status);

                    String sender = JO.getString("sender");
                    Log.d(Tag, "sender : " + sender);

                    ChatMessage.Type type=ChatMessage.Type.RECEIVED;
                    if (sender.equals(uid)) {
                            type=ChatMessage.Type.SENT;
                    }


                    ChatMessage msggg = new ChatMessage(msg, Long.parseLong(msg_time), type);
                    chatView.addMessage(msggg);
                    arr_size++;
                }


            } else {

                Log.d("tstng_SignUpAct", "task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Chat.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

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

                String showroom_id = "";


                while (arr_size < user_Arr.length()) {

                    JSONObject JO = user_Arr.getJSONObject(arr_size);

                    String id = JO.getString("id");
                    Log.d(Tag, "id : " + id);

                    String username = JO.getString("username");
                    Log.d(Tag, "username : " + username);

                    String password = JO.getString("password");
                    Log.d(Tag, "password : " + password);

                    String full_name = JO.getString("full_name");
                    Log.d(Tag, "full_name : " + full_name);

                    String designation = JO.getString("designation");
                    Log.d(Tag, "designation : " + designation);

                    String email = JO.getString("email");
                    Log.d(Tag, "email : " + email);

                    String phone = JO.getString("phone");
                    Log.d(Tag, "phone : " + phone);

                    String img_path = JO.getString("img_path");
                    Log.d(Tag, "img_path : " + img_path);

                    String user_type = JO.getString("user_type");
                    Log.d(Tag, "user_type : " + user_type);

                    String quiz_status = JO.getString("quiz_status");
                    Log.d(Tag, "quiz_status : " + quiz_status);


                    arr_size++;
                }


            } else {

                Log.d("tstng_SignUpAct", "task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Chat.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

    }

    public class BackGroundTasks extends AsyncTask<String, Void, String> {


        RequestBody requestBody;
        Context ctx;
        String urlx;
        boolean isPost;


        private BackGroundTasks(Context ctx, String urlx, RequestBody requestBody) {

            this.ctx = ctx;
            this.urlx = urlx;
            this.requestBody = requestBody;
            isPost = true;


        }

        private BackGroundTasks(Context ctx, String urlx) {

            this.ctx = ctx;
            this.urlx = urlx;
            isPost = false;
            Log.d(Tag, "BackGroundTasks: " + urlx);


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

                Log.d(Tag, httpClient.connectTimeoutMillis() + "");
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

                Log.d(Tag, "" + resss);
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
            Log.d(Tag, "" + s);

            processFinish(s, isPost);


        }
    }
}