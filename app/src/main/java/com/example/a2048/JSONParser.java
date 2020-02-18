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
import java.io.FileWriter;
import java.io.IOException;

class JSONParser {

    private static final String fileName = "/Data/Maps.json";

    static Map getMapToPlay(Context ctx, int index, boolean holes) {
        Map mapObject = null;
        try {
            JSONObject jsonObject = new JSONObject(getMapsString(ctx));
            JSONArray maps = jsonObject.getJSONArray("maps");
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                mapObject = new Map(map.getString("mapsStructure"), map.getInt("mapsSize"), holes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapObject;
    }

    private static String getMapsString(Context ctx) {
        String result = "";
        try {
            File file = new File(ctx.getFilesDir().getAbsolutePath() + fileName);
            if (!file.exists()) {
                createFile(ctx);
            }
            FileReader reader = new FileReader(ctx.getFilesDir().getAbsolutePath() + fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private static void createFile(Context ctx) {
        File file = new File(ctx.getFilesDir(), "Data");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File saveFile = new File(file, "Maps.json");
            FileWriter writer = new FileWriter(saveFile);
            writer.append("{\n" +
                    "  \"maps\": [\n" +
                    "    {\n" +
                    "      \"mapsName\": \"4x4\",\n" +
                    "      \"highestScore\": \"0\",\n" +
                    "      \"mapsSize\": 4,\n" +
                    "      \"mapsStructure\": \"1111111111111111\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}");
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

    static Long getMapHighestScore(Context ctx, int index) {
        try {
            JSONObject jsonObject = new JSONObject(getMapsString(ctx));

            JSONArray maps = jsonObject.getJSONArray("maps");
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                return Long.valueOf(map.get("highestScore").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void saveScore(Context ctx, int index, Long score) {
        try {
            JSONObject jsonObject = new JSONObject(getMapsString(ctx));
            JSONArray maps = jsonObject.getJSONArray("maps");
            if (index > maps.length()) {
                throw new ArrayIndexOutOfBoundsException();
            } else {
                JSONObject map = maps.getJSONObject(index);
                map.remove("highestScore");
                map.put("highestScore", score.toString());
                FileWriter file = new FileWriter(ctx.getFilesDir() + fileName);
                file.write("{\n"+ "\"maps\":" + maps.toString() + "}");
                file.flush();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
