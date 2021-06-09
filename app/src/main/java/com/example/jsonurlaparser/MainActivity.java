package com.example.jsonurlaparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    String name, age;

    private static  String JSON_URL = "https://run.mocky.io/v3/01db357e-0afb-4470-a5d9-03870646c72b";

    ArrayList<HashMap<String,String>> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        friendsList = new ArrayList<>();
        lv = findViewById(R.id.listview);


        GetData getData = new GetData();
        getData.execute();



    }


    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);


                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();

                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();

            }
                return current;
            }


        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Friends");

                for(int i = 0; i < jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    name = jsonObject1.getString("name");
                    age = jsonObject1.getString("age");


                    //Hashmap
                    HashMap<String, String> friends = new HashMap<>();

                    friends.put("name", name);
                    friends.put("age", age);

                    friendsList.add(friends);



                }


        } catch (JSONException e) {
                e.printStackTrace();
            }


            //Displaying the results

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    friendsList,
                    R.layout.row_layout,
                    new String[] {"name", "age"},
                    new int[] {R.id.textView, R.id.textView2}
                    );

            lv.setAdapter(adapter);



        }
    }
    





}