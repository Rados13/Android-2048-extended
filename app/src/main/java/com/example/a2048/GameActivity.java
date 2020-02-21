package com.example.a2048;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private Map map;
    int index;
    boolean save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent in = getIntent();
        final boolean holes;
        save = in.getBooleanExtra("com.example.SAVE", false);
        index = in.getIntExtra("com.example.MAP_INDEX", -1);
        if (save) {
            map = JSONParser.getSave(this, index);
            holes = map.getHoles();
        } else {
            holes = in.getBooleanExtra("com.example.HOLES", false);
            map = JSONParser.getMapToPlay(this, index, holes);
        }
        refresh();
        // Buttons Listeners

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = JSONParser.getMapToPlay(getApplicationContext(), index, holes);
                refresh();
            }
        });

        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.undo()) refresh();
                else {
                    Toast.makeText(getApplicationContext(), "You can't undo more than 3 moves", Toast.LENGTH_SHORT).show();
                }
            }
        });


        View view = getWindow().getDecorView();
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                onSwipe(SwipeDirection.RIGHT);
            }

            @Override
            public void onSwipeLeft() {
                onSwipe(SwipeDirection.LEFT);
            }

            @Override
            public void onSwipeTop() {
                onSwipe(SwipeDirection.TOP);
            }

            @Override
            public void onSwipeBottom() {
                onSwipe(SwipeDirection.BOTTOM);
            }

            void onSwipe(SwipeDirection direction) {
                map.swipe(direction);
                refresh();
                JSONParser.makeSave(getApplicationContext(), index, map,null);
            }
        });
    }


    void refresh() {
        final Resources res = getResources();

        MapPainter map = new MapPainter(this.map.getMapStatus(), this.map.getMapSize());
        ImageView img = (ImageView) findViewById(R.id.mapGameImageView);
        img.setImageDrawable(map);

        Long scoreValue = this.map.getScore();
        Long highestScoreValue = JSONParser.getMapHighestScore(this, index);

        ScorePainter score = new ScorePainter("SCORE", scoreValue.toString());
        ImageView scoreImageView = (ImageView) findViewById(R.id.scoreImageView);
        scoreImageView.setImageDrawable(score);

        ScorePainter highestScore;
        if (scoreValue > highestScoreValue) {

            highestScore = new ScorePainter("HIGH SCORE", scoreValue.toString());
            JSONParser.saveScore(this, index, scoreValue);
        } else {
            highestScore = new ScorePainter("HIGH SCORE", highestScoreValue.toString());
        }
        ImageView highestScoreImageView = (ImageView) findViewById(R.id.highestScoreImageView);
        highestScoreImageView.setImageDrawable(highestScore);

    }

}



