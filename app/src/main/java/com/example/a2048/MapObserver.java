package com.example.a2048;

import java.util.ArrayList;
import java.util.List;

public class MapObserver {

    private String mapStructure;
    private int mapSize;
    private List<String> previousStates;
    private boolean holes;
    private Integer[][] structure;

    MapObserver(String mapStructure, int mapSize, boolean holes) {
        this.mapStructure = mapStructure;
        this.mapSize = mapSize;
        previousStates = new ArrayList<String>();
        stringTo2DArray();
        this.holes = holes;
    }

    void stringTo2DArray() {
        structure = new Integer[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                structure[i][j] = (mapStructure.charAt(i * mapSize + j) == '0') ? 0 : 1;
            }
        }
    }

    String arrayToString(){
        String result = "";
        for(int i=0;i<mapSize;i++){
            for(int j=0;j<mapSize;j++){
                result += (char) (structure[i][j] + '1');
            }
        }
        return result;
    }

    int getMapSize(){
        return mapSize;
    }


    void swipeTop() {
        for (int i = 0; i < mapSize - 1; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (structure[i + 1][j].equals(structure[i][j])) {
                    structure[i][j]++;
                    structure[i + 1][j] = 1;
                }
                if (i - 1 >= 0 && structure[i - 1][j] == 1) {
                    structure[i - 1][j] = structure[i][j];
                    structure[i][j] = 1;
                }
            }
        }
    }


    void swipeBottom() {
        for (int i = mapSize; i >= 1; i--) {
            for (int j = 0; j < mapSize; j++) {
                if (structure[i - 1][j].equals(structure[i][j])) {
                    structure[i][j]++;
                    structure[i - 1][j] = 1;
                }
                if (i + 1 < mapSize && structure[i + 1][j] == 1) {
                    structure[i + 1][j] = structure[i][j];
                    structure[i][j] = 1;
                }
            }
        }
    }

    void swipeRight() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = mapSize - 1; j >= 1; j--) {
                if (structure[i][j - 1].equals(structure[i][j])) {
                    structure[i][j]++;
                    structure[i][j - 1] = 1;
                }
                if (j - 1 < mapSize && structure[i][j - 1] == 1) {
                    structure[i][j - 1] = structure[i][j];
                    structure[i][j] = 1;
                }
            }
        }
    }

    void swipeLeft() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize - 1; j++) {
                if (structure[i][j + 1].equals(structure[i][j])) {
                    structure[i][j]++;
                    structure[i][j + 1] = 1;
                }
                if (j - 1 >= 0 && structure[i][j - 1] == 1) {
                    structure[i][j - 1] = structure[i][j];
                    structure[i][j] = 1;
                }
            }
        }
    }

}
