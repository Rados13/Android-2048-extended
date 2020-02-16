package com.example.a2048;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseMap extends AppCompatActivity {

    ListView mapListView;
    String[] mapsName;
    int[] mapsSize;
    String[] mapsStructure;
    String[] highestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_map);

        Resources res = getResources();
        mapListView = (ListView) findViewById(R.id.mapListView);
        mapsName = res.getStringArray(R.array.mapsName);
        mapsSize = res.getIntArray(R.array.mapsSize);
        mapsStructure = res.getStringArray(R.array.mapsStructure);
        highestScore = res.getStringArray(R.array.highestScore);

        MapAdapter mapAdapter = new MapAdapter(this, mapsName, mapsSize, mapsStructure,highestScore);
        mapListView.setAdapter(mapAdapter);
        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startGame = new Intent(getApplicationContext(), GameActivity.class);

                CheckBox holesActivate = findViewById(R.id.checkBox);
                startGame.putExtra("com.example.HOLES",holesActivate.isChecked());
                startGame.putExtra("com.example.MAP_INDEX", position);
                startActivity(startGame);
            }
        });
    }
}
