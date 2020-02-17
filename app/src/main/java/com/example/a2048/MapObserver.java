package com.example.a2048;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MapObserver {

    private int mapSize;
    private List<String> previousStates;
    private boolean holes;
    private Integer[][] mapStatus;

    MapObserver(String mapStructure, int mapSize, boolean holes) {
        long score;
        this.mapSize = mapSize;
        previousStates = new ArrayList<String>();
        mapStatus = MapConverter.stringTo2DArray(mapStructure, mapSize);
        this.holes = holes;
    }

    int getMapSize() {
        return mapSize;
    }

    Integer[][] getMapStatus() {
        return mapStatus;
    }

    void setMapStatus(String map, int mapSize) {
        mapStatus = MapConverter.stringTo2DArray(map, mapSize);
    }

    void swipe(SwipeDirection direction) {
        previousStates.add(MapConverter.arrayToString(mapStatus, mapSize));
        if (previousStates.size() > 3) previousStates.remove(0);
        switch (direction) {
            case RIGHT:
                swipeRight();
                break;
            case LEFT:
                swipeLeft();
                break;
            case TOP:
                swipeTop();
                break;
            case BOTTOM:
                swipeBottom();
                break;
        }
        random();
    }

    void swipeTop() {
        for (int j = 0; j < mapSize; j++) {
            for (int i = 1; i < mapSize; i++) {
                int k = 1;
                if (mapStatus[i][j] <= 0) continue;
                for (k = 1; i - k >= 0 && mapStatus[i - k][j] == 0; k++) {
                    mapStatus[i - k][j] = mapStatus[i - k + 1][j];
                    mapStatus[i - k + 1][j] = 0;
                }
                if (i - k >= 0 && mapStatus[i - k][j].equals(mapStatus[i - k + 1][j])) {
                    mapStatus[i - k][j]++;
                    mapStatus[i - k + 1][j] = 0;
                } else if (i - k >= 0 && mapStatus[i - k][j] == -2 && mapStatus[i - k + 1][j] > 0) {
                    mapStatus[i - k][j] = 0;
                    mapStatus[i - k + 1][j] = 0;
                }
            }
        }
    }

    void swipeBottom() {
        for (int j = 0; j < mapSize; j++) {
            for (int i = mapSize - 2; i >= 0; i--) {
                int k = 1;
                if (mapStatus[i][j] <= 0) continue;
                for (k = 1; i + k < mapSize && mapStatus[i + k][j] == 0; k++) {
                    mapStatus[i + k][j] = mapStatus[i + k - 1][j];
                    mapStatus[i + k - 1][j] = 0;
                }
                if (i + k < mapSize && mapStatus[i + k][j].equals(mapStatus[i + k - 1][j])) {
                    mapStatus[i + k][j]++;
                    mapStatus[i + k - 1][j] = 0;
                } else if (i + k < mapSize && mapStatus[i + k][j] == -2 && mapStatus[i + k - 1][j] > 0) {
                    mapStatus[i + k][j] = 0;
                    mapStatus[i + k - 1][j] = 0;
                }
            }
        }
    }

    void swipeRight() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = mapSize - 2; j >= 0; j--) {
                int k = 1;
                if (mapStatus[i][j] <= 0) continue;
                for (k = 1; j + k < mapSize && mapStatus[i][j + k] == 0; k++) {
                    mapStatus[i][j + k] = mapStatus[i][j + k - 1];
                    mapStatus[i][j + k - 1] = 0;
                }
                if (j + k < mapSize && mapStatus[i][j + k].equals(mapStatus[i][j + k - 1])) {
                    mapStatus[i][j + k]++;
                    mapStatus[i][j + k - 1] = 0;
                } else if (j + k < mapSize && mapStatus[i][j + k] == -2 && mapStatus[i][j + k - 1] > 0){
                    mapStatus[i][j + k] = 0;
                    mapStatus[i][j + k - 1] = 0;
                }
            }
        }
    }

    void swipeLeft() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 1; j < mapSize; j++) {
                int k = 1;
                if (mapStatus[i][j] <= 0) continue;
                for (k = 1; j - k >= 0 && mapStatus[i][j - k] == 0; k++) {
                    mapStatus[i][j - k] = mapStatus[i][j - k + 1];
                    mapStatus[i][j - k + 1] = 0;
                }
                if (j - k >= 0 && mapStatus[i][j - k].equals(mapStatus[i][j - k + 1])) {
                    mapStatus[i][j - k]++;
                    mapStatus[i][j - k + 1] = 0;
                } else if (j - k >= 0 && mapStatus[i][j - k] == -2 && mapStatus[i][j - k + 1] > 0){
                    mapStatus[i][j - k] = 0;
                    mapStatus[i][j - k + 1] = 0;
                }
            }
        }
    }

    boolean undo() {
        if (previousStates.size() == 0) return false;
        else {
            mapStatus = MapConverter.stringTo2DArray(previousStates.get(previousStates.size() - 1), mapSize);
            Log.d("Map", MapConverter.arrayToString(mapStatus, mapSize));
            previousStates.remove(previousStates.size() - 1);
            return true;
        }
    }

    void random() {
        ArrayList<Integer[]> emptyPlaces = new ArrayList<Integer[]>();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (mapStatus[i][j] == 0) {
                    emptyPlaces.add(new Integer[]{i, j});
                }
            }
        }

        int idx;
        Integer[] position;
        if (holes && Math.random() * 10 > 7) {
            idx = (int) Math.round(Math.random() * emptyPlaces.size());
            position = emptyPlaces.get(idx);
            mapStatus[position[0]][position[1]] = -2;
            emptyPlaces.remove(idx);
        }

        if (emptyPlaces.size() > 0) {
            idx = (int) Math.round(Math.random() * emptyPlaces.size());
            position = emptyPlaces.get(idx);
            double rand = Math.random();
            int size = 1;
            while (rand < 0.15) {
                rand = Math.random();
                size++;
            }
            mapStatus[position[0]][position[1]] = size;
        }


    }
}
