package com.sr_sohan.volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button buttonLoad;
    TextView tvName,tvEmail,tvMobile,tvAddress;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonLoad=findViewById(R.id.buttonLoad);
        tvName=findViewById(R.id.tvName);
        tvMobile=findViewById(R.id.tvMobile);
        tvEmail=findViewById(R.id.tvEmail);
        tvAddress=findViewById(R.id.tvAddress);
        progressBar=findViewById(R.id.progressBar);

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);



                String url = "http://192.168.16.211/myPHPproject/ParsonInfo.json";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                Log.d("ServerRes",response);
                                
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String name=jsonObject.getString("name");
                                    String mobill=jsonObject.getString("mobile");
                                    String email=jsonObject.getString("email");
                                    String address=jsonObject.getString("address");

                                    tvName.setText(name);
                                    tvMobile.setText(mobill);
                                    tvEmail.setText(email);
                                    tvAddress.setText(address);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                buttonLoad.setText("Volley Error");
                                progressBar.setVisibility(View.GONE);

                            }
                        });
                RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}