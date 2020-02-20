package com.example.a2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MapAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<String> mapsName;
    private ArrayList<String> mapsSize;
    private ArrayList<String> mapsStructure;
    private ArrayList<String> highestScores;
    private ArrayList<String> holes;

    public MapAdapter(Context c, ArrayList<String> names, ArrayList<String> sizes,
                     ArrayList<String> structures, ArrayList<String> scores) {
        mapsName = names;
        mapsSize = sizes;
        mapsStructure = structures;
        highestScores = scores;
        holes = null;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public MapAdapter(Context c, ArrayList<String> names, ArrayList<String> sizes,
                      ArrayList<String> structures, ArrayList<String> scores, ArrayList<String> holesArray) {
        mapsName = names;
        mapsSize = sizes;
        mapsStructure = structures;
        highestScores = scores;
        holes = holesArray;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return mapsName.size();
    }

    @Override
    public Object getItem(int position) {
        return mapsName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.maplist_detail, null);
        TextView nameTextView = (TextView) v.findViewById(R.id.mapNameTextView);
        ImageView mapStructurImageView = (ImageView) v.findViewById(R.id.mapView);
        TextView highestScoreTextView = (TextView) v.findViewById(R.id.highestScoreTextView);
        TextView holesTextView = (TextView) v.findViewById(R.id.holesDetailsTextView);
        String scoreString;
        if (parent.getId() == R.id.continueListView) {
            String holesText = "Holes:\n" + holes.get(position);
            holesTextView.setText(holesText);
            scoreString = "Score:\n";
        } else {
            scoreString = "Highest\nScore:\n";
        }

        String mapName = "Map name: " + mapsName.get(position);
        scoreString += highestScores.get(position);
        String mapStructure = mapsStructure.get(position);
        int mapSize = Integer.valueOf(mapsSize.get(position));

        nameTextView.setText(mapName);
        highestScoreTextView.setText(scoreString);

        MapPainter map = new MapPainter(MapConverter.stringTo2DArray(mapStructure, mapSize), mapSize);
        mapStructurImageView.setImageDrawable(map);
        mapStructurImageView.setContentDescription(mapName);

        return v;
    }
}
