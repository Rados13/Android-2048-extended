package com.example.a2048;

public class MapConverter {
    static Integer[][] stringTo2DArray(String mapStructure, int mapSize) {
        Integer[][] mapStatus = new Integer[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                mapStatus[i][j] = mapStructure.charAt(i * mapSize + j) - '1';
            }
        }
        return mapStatus;
    }

    static String arrayToString(Integer[][] mapStatus, int mapSize) {
        String result = "";
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                result += (char) (mapStatus[i][j] + '1');
            }
        }
        return result;
    }
}
