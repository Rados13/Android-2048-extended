package com.example.a2048;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {

    private static final String mapsFileName = "Maps.json";
    private static final String mapsFilePath = "/Data/Maps.json";
    private static final String savesFileName = "Saves.json";
    private static final String savesFilePath = "/Data/Saves.json";
    private static final String structureField = "mapStructure";
    private static final String nameField = "mapName";
    private static final String highestScoreField = "highestScore";
    private static final String scoreField = "score";
    private static final String holesField = "holes";
    private static final String sizeField = "mapSize";


    static void createFile(Context ctx, String newFileName) {
        File file = new File(ctx.getFilesDir(), "Data");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File saveFile = new File(file, newFileName);
            FileWriter writer = new FileWriter(saveFile);
            if (mapsFileName.equals(newFileName)) {
                writer.append("{\n" +
                        "  \"maps\": [\n" +
                        "    {\n" +
                        "      \"mapName\": \"4x4\",\n" +
                        "      \"highestScore\": \"0\",\n" +
                        "      \"mapSize\": 4,\n" +
                        "      \"mapStructure\": \"1111111111111111\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
            } else {
                writer.append("{\n" +
                        "  \"saves\": [\n" +
                        "    {\n" +
                        "      \"mapName\": \"4x4\",\n" +
                        "      \"holes\": false,\n" +
                        "      \"score\": \"0\",\n" +
                        "      \"mapSize\": 4,\n" +
                        "      \"mapStructure\": \"1111112111111111\"\n" +
                        "    }\n" +
                        "  ]\n" + "}");
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
        }
    }


    static void saveScore(Context ctx, int index, Long score) {
        try {
            JSONArray maps = JSONReader.getFileJSONArray(ctx, mapsFileName);
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                map.remove(highestScoreField);
                map.put(highestScoreField, score.toString());
               fileOverWrite(ctx,maps,mapsFileName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void makeSave(Context ctx, int index, Map mapToSave, String name) {
        try {
            JSONArray maps = JSONReader.getFileJSONArray(ctx, savesFileName);
            JSONObject map;
            if (index == maps.length()) {
                map = new JSONObject();
                map.put(nameField, name);
                map.put(sizeField, mapToSave.getMapSize());
                maps.put(map);
            } else if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                map = maps.getJSONObject(index);
                map.remove(holesField);
                map.remove(scoreField);
                map.remove(structureField);
            }
            map.put(holesField, mapToSave.getHoles());
            map.put(scoreField, Long.toString(mapToSave.getScore()));
            map.put(structureField, MapConverter.arrayToString(mapToSave.getMapStatus(), mapToSave.getMapSize()));
            fileOverWrite(ctx,maps,savesFileName);
        } catch (JSONException e) {
            Log.e("Login activity", e.toString());
            e.printStackTrace();
        }

    }

    static void saveMap(Context ctx, String name, int size, String mapStructure) {
        try {
            JSONArray maps = JSONReader.getFileJSONArray(ctx, mapsFileName);
            JSONObject map = new JSONObject();
            map.put(nameField, name);
            map.put(structureField, mapStructure);
            map.put(highestScoreField, "0");
            map.put(sizeField, size);
            maps.put(map);
            makeSave(ctx, maps.length() - 1, new Map(mapStructure, size, false), name);
            fileOverWrite(ctx,maps,mapsFileName);
        } catch (JSONException e) {
            Log.e("Login activity", e.toString());
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void deleteMap(Context ctx, int index) {
        try {
            JSONArray maps = JSONReader.getFileJSONArray(ctx, mapsFileName);
            JSONArray saves = JSONReader.getFileJSONArray(ctx, savesFileName);
            if (maps.length() <= index) {
                throw new ArrayIndexOutOfBoundsException();
            }
            maps.remove(index);
            saves.remove(index);
            fileOverWrite(ctx, maps,mapsFileName);
            fileOverWrite(ctx, saves,savesFileName);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private static void fileOverWrite(Context ctx, JSONArray maps, String fileName) {
        try {
            if(fileName.equals(mapsFileName)) {
                FileWriter file = new FileWriter(ctx.getFilesDir() + mapsFilePath);
                file.write("{\n" + "\"maps\":" + maps.toString() + "}");
                file.flush();
            }else{
                FileWriter file = new FileWriter(ctx.getFilesDir() + savesFilePath);
                file.write("{\n" + "\"saves\":" + maps.toString() + "}");
                file.flush();
            }
        } catch (IOException e) {
            Log.e("Login activity", e.toString());
            e.printStackTrace();
        }
    }
}
