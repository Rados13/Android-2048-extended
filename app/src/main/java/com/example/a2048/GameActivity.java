package com.example.a2048;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private MapObserver mapObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Resources res = getResources();
        Intent in = getIntent();
        final int index = in.getIntExtra("com.example.MAP_INDEX", -1);
        final boolean holes = in.getBooleanExtra("com.example.HOLES", false);
        String mapStructure = res.getStringArray(R.array.mapsStructure)[index];
        final int mapSize = res.getIntArray(R.array.mapsSize)[index];
        mapObserver = new MapObserver(mapStructure, mapSize, holes);
        refresh();

        // Buttons Listeners

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapStructure = res.getStringArray(R.array.mapsStructure)[index];
                mapObserver = new MapObserver(mapStructure, mapSize, holes);
                refresh();
            }
        });

        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapObserver.undo()) refresh();
                else {
                    Toast.makeText(getApplicationContext(), "You can't undo more than 3 moves", Toast.LENGTH_SHORT).show();
                }
            }
        });


        View view = getWindow().getDecorView();
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Log.d("swipe", "swipe right");
                mapObserver.swipe(SwipeDirection.RIGHT);
                refresh();
            }

            @Override
            public void onSwipeLeft() {
                Log.d("swipe", "swipe left");
                mapObserver.swipe(SwipeDirection.LEFT);
                refresh();
            }

            @Override
            public void onSwipeTop() {
                mapObserver.swipe(SwipeDirection.TOP);
                refresh();
            }

            @Override
            public void onSwipeBottom() {
                mapObserver.swipe(SwipeDirection.BOTTOM);
                refresh();
            }
        });
    }


    void refresh() {
        MapPainter map = new MapPainter(mapObserver.getMapStatus(), mapObserver.getMapSize());
        ImageView img = (ImageView) findViewById(R.id.mapGameImageView);
        img.setImageDrawable(map);
    }

}



