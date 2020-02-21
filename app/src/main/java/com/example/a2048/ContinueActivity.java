package com.example.a2048;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContinueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        ListView gamesListView = (ListView) findViewById(R.id.continueListView);

        Intent in = getIntent();
        final boolean delete = in.getBooleanExtra("com.example.DELETE", false);
        ArrayList<ArrayList<String>> mapsStats;
        MapAdapter mapAdapter ;
        if (delete) {
            mapsStats = JSONReader.getMaps(this);
            for (int i = 0; i < mapsStats.size(); i++) {
                mapsStats.get(i).remove(0);
            }
            mapAdapter = new MapAdapter(this, mapsStats.get(0),
                    mapsStats.get(2), mapsStats.get(3), mapsStats.get(1));

        }else{
            mapsStats = JSONReader.getSaves(this);
            mapAdapter = new MapAdapter(this, mapsStats.get(0),
                    mapsStats.get(2), mapsStats.get(3), mapsStats.get(1),mapsStats.get(4));
        }
        gamesListView.setAdapter(mapAdapter);

        gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextIntent;
                if(!delete) {
                    nextIntent = new Intent(getApplicationContext(), GameActivity.class);
                    nextIntent.putExtra("com.example.SAVE", true);
                    nextIntent.putExtra("com.example.MAP_INDEX", position);
                }else{
                    JSONWriter.deleteMap(getApplicationContext(),position+1);
                    nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(nextIntent);
            }
        });
    }
}
