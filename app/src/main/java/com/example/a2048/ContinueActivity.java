package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContinueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        ListView gamesListView = (ListView) findViewById(R.id.continueListView);

        final ArrayList<ArrayList<String>> mapsStats = JSONParser.getSaves(this);
        MapAdapter mapAdapter = new MapAdapter(this, mapsStats.get(0),
                mapsStats.get(2), mapsStats.get(3), mapsStats.get(1),mapsStats.get(4));

        gamesListView.setAdapter(mapAdapter);

        gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startGame = new Intent(getApplicationContext(), GameActivity.class);

                startGame.putExtra("com.example.SAVE", true);
                startGame.putExtra("com.example.MAP_INDEX", position);
                startActivity(startGame);
            }
        });
    }
}
