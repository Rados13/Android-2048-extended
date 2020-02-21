package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.startButton);
        Button contButton = (Button) findViewById(R.id.continueButton);
        Button createButton = (Button) findViewById(R.id.createButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ChooseMapActivity.class);
                startActivity(startIntent);
            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(getApplicationContext(), CreateMapActivity.class);
                startActivity(createIntent);
            }
        });

        contButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continueIntent = new Intent(getApplicationContext(), ContinueActivity.class);
                startActivity(continueIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteIntent = new Intent(getApplicationContext(), ContinueActivity.class);
                deleteIntent.putExtra("com.example.DELETE", true);
                startActivity(deleteIntent);
            }
        });
    }



}
