package com.example.prabalkar.marketapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private Context mContext;
    private Activity mActivity;

    private Button mButton;
    private TextView mTextview;
    private String mJsonUrlString = "https://s3.ap-south-1.amazonaws.com/org.whatsmad.test/api.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mActivity =MainActivity.this;

        mButton= (Button) findViewById(R.id.button);
        mTextview = (TextView) findViewById(R.id.textview);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextview.setText(" ");

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, mJsonUrlString, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = response.getJSONArray("mymart");

                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject mymart = array.getJSONObject(i);

                                        String itemid = mymart.getString("itemId");
                                        String name = mymart.getString("name");
                                        String image = mymart.getString("image");

                                        mTextview.append(itemid + "\n" + name + "\n" + image);
                                        mTextview.append("\n\n");

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );
                        requestQueue.add(jsonObjectRequest);

                }

        });

    }
}
