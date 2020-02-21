package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChooseMapActivity extends AppCompatActivity {

    ListView mapListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_map);

        mapListView = (ListView) findViewById(R.id.mapListView);

        ArrayList<ArrayList<String>> mapsStats = JSONReader.getMaps(this);


        MapAdapter mapAdapter = new MapAdapter(this, mapsStats.get(0),
                mapsStats.get(2), mapsStats.get(3), mapsStats.get(1));

        mapListView.setAdapter(mapAdapter);

        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startGameIntent;
                startGameIntent = new Intent(getApplicationContext(), GameActivity.class);
                CheckBox holesActivate = findViewById(R.id.checkBox);
                startGameIntent.putExtra("com.example.HOLES", holesActivate.isChecked());
                startGameIntent.putExtra("com.example.MAP_INDEX", position);
                startActivity(startGameIntent);
            }
        });
    }
}
