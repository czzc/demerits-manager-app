package com.demerits.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Decrees extends Activity implements AsyncResponse {
    RequestTask reqTask = new RequestTask();
    private ListView usersList;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.decrees);
        reqTask.delegate = this;
        super.onCreate(savedInstanceState);

        usersList = (ListView) findViewById(R.id.usersList);
        ArrayList<String> users = new ArrayList<String>();
        users.add(0, "");
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
        usersList.setAdapter(listAdapter);

    }

    public void request(View v){
        //TODO fix it to where it can execute more than once without force closing app
        reqTask.execute("SERVER_ADDRESS");
    }

    public void processFinish(String output){
        try{
            JSONObject jObject = new JSONObject(output);
            JSONArray jArray = jObject.getJSONArray("users");
            for (int i=0; i < jArray.length(); i++)
            {
                JSONObject oneObject = jArray.getJSONObject(i);
                // Pulling items from the array
                String oneObjectsItem = oneObject.getString("name");
                listAdapter.add(oneObjectsItem);
            }
        } catch (JSONException jExcep){
            Log.w("DECREES", "JSONException: " + jExcep.toString());
            //TODO Handle errors
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
 }