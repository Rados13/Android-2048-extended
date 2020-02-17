package com.example.a2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MapAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private String[] mapsName;
    private int[] mapsSize;
    private String[] mapsStructure;
    private String[] highestScores;

    public MapAdapter(Context c, String[] names, int[] sizes, String[] structures, String[] scores){
        mapsName = names;
        mapsSize = sizes;
        mapsStructure = structures;
        highestScores = scores;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mapsName.length;
    }

    @Override
    public Object getItem(int position) {
        return mapsName[position];
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

        String mapName = mapsName[position];
        String highestScore = highestScores[position];
        String mapStructure = mapsStructure[position];
        int mapSize = mapsSize[position];

        nameTextView.setText(mapName);
        highestScoreTextView.setText(highestScore);

        MapPainter map = new MapPainter(MapConverter.stringTo2DArray(mapStructure,mapSize),mapSize);
        mapStructurImageView.setImageDrawable(map);
        mapStructurImageView.setContentDescription(mapName);

        return v;
    }
}
