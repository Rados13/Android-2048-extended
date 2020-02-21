package com.example.a2048;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateMapActivity extends AppCompatActivity {

    int size;
    String mapStructure = "";
    MapCreationPainter mapPainter;
    PointF lastTouchDown = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);

        EditText sizeView = findViewById(R.id.sizeEditText);

        changeSize(4);
        sizeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sizeView = findViewById(R.id.sizeEditText);
                changeSize(Integer.valueOf(sizeView.getText().toString()));
            }
        });

        ImageView map = findViewById(R.id.mapCreationImageView);

        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    lastTouchDown.x = event.getX();
                    lastTouchDown.y = event.getY();
                }

                return false;
            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMapStructure(mapPainter.getNewMap(lastTouchDown));
            }
        });

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameView = findViewById(R.id.mapNameTextView);
                if (nameView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "You don't give map name", Toast.LENGTH_SHORT).show();
                }
                if (!MapValidation.mapValid(mapStructure, size)) {
                    Toast.makeText(getApplicationContext(), "Map is not valid!", Toast.LENGTH_SHORT).show();
                } else {
                    JSONWriter.saveMap(getApplicationContext(), nameView.getText().toString(), size, mapStructure);
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        });


    }


    void changeSize(Integer value) {
        if (value > 20) {
            Toast.makeText(getApplicationContext(), "It to small, you will see nothing, (size<20)", Toast.LENGTH_SHORT).show();
        }
        size = value;
        mapStructure = "";
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < size * size; i++) tmp.append('1');
        mapStructure = tmp.toString();
        refresh();
    }

    void changeMapStructure(String map) {
        mapStructure = map;
        refresh();
    }

    void refresh() {
        mapPainter = new MapCreationPainter(MapConverter.stringTo2DArray(mapStructure, size), size);
        ImageView img = (ImageView) findViewById(R.id.mapCreationImageView);
        img.setImageDrawable(mapPainter);
    }


}
