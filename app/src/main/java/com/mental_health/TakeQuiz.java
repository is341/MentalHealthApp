package com.mental_health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mental_health.dataModel.DataModelDoctors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class TakeQuiz extends AppCompatActivity {

    String Tag="Tstng_TakeQuiz";
    ProgressBar pBar;
    int q1=-1;
    int q2=-1;
    int q3=-1;
    int q4=-1;
    int q5=-1;

    boolean IsQ1=false;
    boolean IsQ2=false;
    boolean IsQ3=false;
    boolean IsQ4=false;
    boolean IsQ5=false;

    RadioGroup q1radioGroup;
    RadioGroup q2radioGroup;
    RadioGroup q3radioGroup;
    RadioGroup q4radioGroup;
    RadioGroup q5radioGroup;


    RadioButton rbQ1A1;
    RadioButton rbQ1A2;
    RadioButton rbQ1A3;
    RadioButton rbQ2A1;
    RadioButton rbQ2A2;
    RadioButton rbQ2A3;
    RadioButton rbQ2A4;
    RadioButton rbQ3A1;
    RadioButton rbQ3A2;
    RadioButton rbQ3A3;
    RadioButton rbQ3A4;
    RadioButton rbQ4A1;
    RadioButton rbQ4A2;
    RadioButton rbQ4A3;
    RadioButton rbQ5A1;
    RadioButton rbQ5A2;
    RadioButton rbQ5A3;

    String q1Ans="";
    String q2Ans="";
    String q3Ans="";
    String q4Ans="";
    String q5Ans="";
    String userId="0";



    String[] q1AnsArr= {"0","1-3","3+"};
    String[] q2AnsArr= {"Very often","Once or twice a month","Once a week","Every day"};
    String[] q3AnsArr= {"Very often","Once or twice a month","Once a week","Every night"};
    String[] q4AnsArr= {"Never","Once or twice","2+"};
    String[] q5AnsArr= {"Never","Once or twice","2+"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        getSupportActionBar().hide();

        initialization();

        radioGroupsClicks();


        new BackGroundTasks(TakeQuiz.this, Constants.Patient+"?patient_id="+userId).execute();

    }

    private void radioGroupsClicks() {
        q1radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.q1radioAns1)
                    q1=1;
                if (checkedId==R.id.q1radioAns2)
                    q1=2;
                    if (checkedId==R.id.q1radioAns3)
                    q1=3;

                    int pos=q1-1;
                    q1Ans=q1AnsArr[pos];

            }
        });
        q2radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.q2radioAns1)
                    q2=1;
                if (checkedId==R.id.q2radioAns2)
                    q2=2;
                    if (checkedId==R.id.q2radioAns3)
                    q2=3;
                    if (checkedId==R.id.q2radioAns4)
                    q2=4;

                int pos=q2-1;
                q2Ans=q2AnsArr[pos];
            }
        });
        q3radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.q3radioAns1)
                    q3=1;
                if (checkedId==R.id.q3radioAns2)
                    q3=2;
                    if (checkedId==R.id.q3radioAns3)
                    q3=3;
                    if (checkedId==R.id.q3radioAns4)
                    q3=4;

                int pos=q3-1;
                q3Ans=q3AnsArr[pos];
            }
        });
        q4radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.q4radioAns1)
                    q4=1;
                if (checkedId==R.id.q4radioAns2)
                    q4=2;
                    if (checkedId==R.id.q4radioAns3)
                    q4=3;


                int pos=q4-1;
                q4Ans=q4AnsArr[pos];

            }
        });
        q5radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.q5radioAns1)
                    q5=1;
                if (checkedId==R.id.q5radioAns2)
                    q5=2;
                    if (checkedId==R.id.q5radioAns3)
                    q5=3;


                int pos=q5-1;
                q5Ans=q5AnsArr[pos];

            }
        });
    }

    private void initialization() {
        userId = PreferenceManager.getDefaultSharedPreferences(TakeQuiz.this).getString("userId", "0");

        pBar=findViewById(R.id.pBar);

        q1radioGroup=findViewById(R.id.q1radioGroup);
        q2radioGroup=findViewById(R.id.q2radioGroup);
        q3radioGroup=findViewById(R.id.q3radioGroup);
        q4radioGroup=findViewById(R.id.q4radioGroup);
        q5radioGroup=findViewById(R.id.q5radioGroup);

        rbQ1A1=findViewById(R.id.q1radioAns1);
        rbQ1A2=findViewById(R.id.q1radioAns2);
        rbQ1A3=findViewById(R.id.q1radioAns3);
        rbQ2A1=findViewById(R.id.q2radioAns1);
        rbQ2A2=findViewById(R.id.q2radioAns2);
        rbQ2A3=findViewById(R.id.q2radioAns3);
        rbQ2A4=findViewById(R.id.q2radioAns4);
        rbQ3A1=findViewById(R.id.q3radioAns1);
        rbQ3A2=findViewById(R.id.q3radioAns2);
        rbQ3A3=findViewById(R.id.q3radioAns3);
        rbQ3A4=findViewById(R.id.q3radioAns4);
        rbQ4A1=findViewById(R.id.q4radioAns1);
        rbQ4A2=findViewById(R.id.q4radioAns2);
        rbQ4A3=findViewById(R.id.q4radioAns3);
        rbQ5A1=findViewById(R.id.q5radioAns1);
        rbQ5A2=findViewById(R.id.q5radioAns2);
        rbQ5A3=findViewById(R.id.q5radioAns3);
    }

    public void submitquiz(View view) {
        if (q1!=-1 && q2!=-1 && q3!=-1 && q4!=-1 && q5!=-1){

            IsQ1=true;
            OKHttpRequests("1",q1Ans);
        }
        else Toast.makeText(TakeQuiz.this, "Please Answer All Questions First", Toast.LENGTH_SHORT).show();
    }
    private void OKHttpRequests(String question_id,String answer) {


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id",userId)
                .addFormDataPart("question_id",question_id)
                .addFormDataPart("answer",answer)
                .build();
        BackGroundTasks task = new BackGroundTasks(TakeQuiz.this, Constants.Patient,requestBody);

        task.execute();

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


        }  private BackGroundTasks(Context ctx, String urlx) {

            this.ctx = ctx;
            this.urlx = urlx;
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

            if(isPost) {

                if (IsQ1 && !IsQ2) {
                    IsQ2 = true;
                    OKHttpRequests(2 + "", q2Ans);

                } else if (IsQ1 && IsQ2 && !IsQ3) {
                    IsQ3 = true;
                    OKHttpRequests(3 + "", q3Ans);

                } else if (IsQ1 && IsQ2 && IsQ3 && !IsQ4) {
                    IsQ4 = true;
                    OKHttpRequests(4 + "", q4Ans);

                } else if (IsQ1 && IsQ2 && IsQ3 && IsQ4 && !IsQ5) {
                    IsQ5 = true;
                    OKHttpRequests(5 + "", q5Ans);

                } else {
                    finish();
                }
            }else{

                  processFinish(s);
            }


        }
    }

    private void processFinish(String json_string) {

        JSONArray jsonArray;
        jsonArray = new JSONArray();

        Log.d(Tag, json_string);

        try {
            JSONObject object;
            object = new JSONObject(json_string);

            IfMethods(object);

        } catch (JSONException e) {
            e.printStackTrace();

            pBar.setVisibility(View.INVISIBLE);
            Log.d(Tag, "exception : " + e);
        }
    }


    private void IfMethods(JSONObject object) {




        Log.d(Tag, "data : " + object.toString());
        try {

            if (object.getString("Response") != null) {



                int arr_size = 0;
                JSONArray user_Arr = object.getJSONArray("Response");
                Log.d(Tag, "Arr : " + user_Arr.toString());




                while (arr_size < user_Arr.length()) {

                    JSONObject JO_user = user_Arr.getJSONObject(arr_size);

                    String id = JO_user.getString("id");
                    Log.d(Tag, "id : " + id);
                    String question_id = JO_user.getString("question_id");
                    Log.d(Tag, "question_id : " + question_id);
                    String user_id = JO_user.getString("user_id");
                    Log.d(Tag, "user_id : " + user_id);
                    String answer = JO_user.getString("answer");
                    Log.d(Tag, "answer : " + answer);

if (question_id.equals("1")){
    for (int i=0; i<q1AnsArr.length; i++){
        if (answer.equals(q1AnsArr[i])){
            if (i==0)
                rbQ1A1.setChecked(true);
            if (i==1)
                rbQ1A2.setChecked(true);
            if (i==2)
                rbQ1A3.setChecked(true);
        }
    }
}
if (question_id.equals("2")){
    for (int i=0; i<q2AnsArr.length; i++){
        if (answer.equals(q2AnsArr[i])){
            if (i==0)
                rbQ2A1.setChecked(true);
            if (i==1)
                rbQ2A2.setChecked(true);
            if (i==2)
                rbQ2A3.setChecked(true);
            if (i==3)
                rbQ2A4.setChecked(true);
        }
    }
}
if (question_id.equals("3")){
    for (int i=0; i<q3AnsArr.length; i++){
        if (answer.equals(q3AnsArr[i])){
            if (i==0)
                rbQ3A1.setChecked(true);
            if (i==1)
                rbQ3A2.setChecked(true);
            if (i==2)
                rbQ3A3.setChecked(true);
            if (i==3)
                rbQ3A4.setChecked(true);
        }
    }
}
if (question_id.equals("4")){
    for (int i=0; i<q4AnsArr.length; i++){
        if (answer.equals(q4AnsArr[i])){
            if (i==0)
                rbQ4A1.setChecked(true);
            if (i==1)
                rbQ4A2.setChecked(true);
            if (i==2)
                rbQ4A3.setChecked(true);
        }
    }
}
if (question_id.equals("4")){
    for (int i=0; i<q5AnsArr.length; i++){
        if (answer.equals(q5AnsArr[i])){
            if (i==0)
                rbQ5A1.setChecked(true);
            if (i==1)
                rbQ5A2.setChecked(true);
            if (i==2)
                rbQ5A3.setChecked(true);
        }
    }
}

                    arr_size++;
                }




            }else {

                Log.d(Tag,"task.isSuccessful : null");
                pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(TakeQuiz.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        pBar.setVisibility(View.INVISIBLE);

    }
}