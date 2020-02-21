package com.example.a2048;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class JSONReader {

    private static final String mapsFileName = "Maps.json";
    private static final String savesFileName = "Saves.json";
    private static final String structureField = "mapStructure";
    private static final String nameField = "mapName";
    private static final String highestScoreField = "highestScore";
    private static final String scoreField = "score";
    private static final String holesField = "holes";
    private static final String sizeField = "mapSize";


    static ArrayList<ArrayList<String>> getMaps(Context ctx) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        try {
            JSONArray maps = getFileJSONArray(ctx, mapsFileName);
            for (int i = 0; i < maps.length(); i++) {
                JSONObject map = maps.getJSONObject(i);
                result.get(0).add(map.getString(nameField));
                result.get(1).add(map.getString(highestScoreField));
                result.get(2).add(Integer.valueOf(map.getInt(sizeField)).toString());
                result.get(3).add(map.getString(structureField));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    static Map getMapToPlay(Context ctx, int index, boolean holes) {
        Map mapObject = null;
        try {
            JSONArray maps = getFileJSONArray(ctx, mapsFileName);
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                mapObject = new Map(map.getString(structureField), map.getInt(sizeField), holes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapObject;
    }

    static JSONArray getFileJSONArray(Context ctx, String fileName) {
        JSONArray result = null;
        try {
            File file = new File(ctx.getFilesDir().getAbsolutePath() + "/Data/" + fileName);
            if (!file.exists()) {
                JSONWriter.createFile(ctx, fileName);
            }
            FileReader reader = new FileReader(ctx.getFilesDir().getAbsolutePath() + "/Data/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            if (fileName.equals(mapsFileName)) {
                result = jsonObject.getJSONArray("maps");
            } else {
                result = jsonObject.getJSONArray("saves");
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    static Long getMapHighestScore(Context ctx, int index) {
        try {
            JSONArray maps = getFileJSONArray(ctx, mapsFileName);
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                return Long.valueOf(map.get(highestScoreField).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ArrayList<ArrayList<String>> getSaves(Context ctx) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        result.add(new ArrayList<String>());
        try {
            JSONArray maps = getFileJSONArray(ctx, savesFileName);
            for (int i = 0; i < maps.length(); i++) {
                JSONObject map = maps.getJSONObject(i);
                result.get(0).add(map.getString(nameField));
                result.get(1).add(map.getString(scoreField));
                result.get(2).add(Integer.valueOf(map.getInt(sizeField)).toString());
                result.get(3).add(map.getString(structureField));
                result.get(4).add(Boolean.toString(map.getBoolean(holesField)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    static Map getSave(Context ctx, int index) {
        Map resultMap = null;
        try {
            JSONArray maps = getFileJSONArray(ctx, savesFileName);
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                resultMap = new Map(map.getString(structureField),
                        Long.valueOf(map.getString(scoreField)), map.getInt(sizeField),
                        map.getBoolean(holesField));
            }
        } catch (JSONException e) {
            Log.e("Login activity", e.toString());
            e.printStackTrace();
        }
        return resultMap;
    }




}
