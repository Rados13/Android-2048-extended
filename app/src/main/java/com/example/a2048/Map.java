package com.example.a2048;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private int mapSize;
    private List<String> previousStates;
    private boolean holes;
    private Integer[][] mapStatus;
    long score;
    ArrayList<Long> previousScores;

    Map(String mapStructure, int mapSize, boolean holes) {
        score = 0;
        previousScores = new ArrayList<Long>();
        this.mapSize = mapSize;
        previousStates = new ArrayList<String>();
        mapStatus = MapConverter.stringTo2DArray(mapStructure, mapSize);
        random();
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
        previousScores.add(score);
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
        boolean[][] joined = new boolean[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                joined[i][j] = false;
            }
        }
        for (int k = 0; k < mapSize; k++) {
            for (int j = 0; j < mapSize; j++) {
                for (int i = 1; i < mapSize - k; i++) {
                    if (mapStatus[i][j] <= 0) {
                        continue;
                    } else if (mapStatus[i - 1][j] == 0) {
                        mapStatus[i - 1][j] = mapStatus[i][j];
                        joined[i - 1][j] = joined[i][j];
                        mapStatus[i][j] = 0;
                        joined[i][j] = false;

                    } else if (mapStatus[i - 1][j].equals(mapStatus[i][j]) && !joined[i - 1][j] && !joined[i][j]) {
                        mapStatus[i - 1][j]++;
                        mapStatus[i][j] = 0;
                        joined[i - 1][j] = true;
                        score += Math.pow(2, mapStatus[i - 1][j]);
                    } else if (mapStatus[i - 1][j] == -2 && mapStatus[i][j] > 0) {
                        mapStatus[i - 1][j] = 0;
                        mapStatus[i][j] = 0;
                        joined[i][j] = false;
                    }
                }
            }
        }
    }

    void swipeBottom() {
        boolean[][] joined = new boolean[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                joined[i][j] = false;
            }
        }
        for (int k = 0; k < mapSize; k++) {
            for (int j = 0; j < mapSize; j++) {
                for (int i = mapSize - 2; i >= k; i--) {
                    if (mapStatus[i][j] <= 0) continue;
                    else if (mapStatus[i + 1][j] == 0) {
                        mapStatus[i + 1][j] = mapStatus[i][j];
                        mapStatus[i][j] = 0;
                        joined[i + 1][j] = joined[i][j];
                        joined[i][j] = false;
                    } else if (mapStatus[i + 1][j].equals(mapStatus[i][j]) && !joined[i + 1][j] && !joined[i][j]) {
                        mapStatus[i + 1][j]++;
                        joined[i + 1][j] = true;
                        mapStatus[i][j] = 0;
                        score += Math.pow(2, mapStatus[i + 1][j]);
                    } else if (mapStatus[i + 1][j] == -2 && mapStatus[i][j] > 0) {
                        mapStatus[i][j] = 0;
                        mapStatus[i + 1][j] = 0;
                        joined[i][j] = false;
                    }
                }
            }
        }
    }

    void swipeRight() {
        boolean[][] joined = new boolean[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                joined[i][j] = false;
            }
        }
        for (int k = 0; k < mapSize; k++) {
            for (int i = 0; i < mapSize; i++) {
                for (int j = mapSize - 2; j >= k; j--) {
                    if (mapStatus[i][j] <= 0) continue;
                    else if (mapStatus[i][j + 1] == 0) {
                        mapStatus[i][j + 1] = mapStatus[i][j];
                        mapStatus[i][j] = 0;
                        joined[i][j + 1] = joined[i][j];
                        joined[i][j] = false;
                    } else if (mapStatus[i][j].equals(mapStatus[i][j + 1]) && !joined[i][j] && !joined[i][j + 1]) {
                        mapStatus[i][j + 1]++;
                        mapStatus[i][j] = 0;
                        joined[i][j + 1] = true;
                        score += Math.pow(2, mapStatus[i][j + 1]);
                    } else if (mapStatus[i][j + 1] == -2 && mapStatus[i][j] > 0) {
                        mapStatus[i][j + 1] = 0;
                        mapStatus[i][j] = 0;
                        joined[i][j] = false;
                    }
                }
            }
        }
    }

    void swipeLeft() {
        boolean[][] joined = new boolean[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                joined[i][j] = false;
            }
        }
        for (int k = 0; k < mapSize; k++) {
            for (int i = 0; i < mapSize; i++) {
                for (int j = 1; j < mapSize - k; j++) {
                    if (mapStatus[i][j] <= 0) continue;
                    else if (mapStatus[i][j - 1] == 0) {
                        mapStatus[i][j - 1] = mapStatus[i][j];
                        mapStatus[i][j] = 0;
                    } else if (mapStatus[i][j].equals(mapStatus[i][j - 1]) && !joined[i][j] && !joined[i][j - 1]) {
                        mapStatus[i][j - 1]++;
                        mapStatus[i][j] = 0;
                        joined[i][j - 1] = true;
                        score += Math.pow(2, mapStatus[i][j - 1]);
                    } else if (mapStatus[i][j - 1] == -2 && mapStatus[i][j] > 0) {
                        mapStatus[i][j - 1] = 0;
                        mapStatus[i][j] = 0;
                        joined[i][j - 1] = false;

                    }
                }
            }
        }
    }

    boolean undo() {
        if (previousStates.size() == 0) return false;
        else {
            mapStatus = MapConverter.stringTo2DArray(previousStates.get(previousStates.size() - 1), mapSize);
            previousStates.remove(previousStates.size() - 1);
            score = previousScores.get(previousScores.size()-1);
            previousScores.remove(previousScores.size()-1);
            return true;
        }
    }

    private void random() {
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
        if (holes && Math.random() * 10 > 7 && emptyPlaces.size() < mapSize*mapSize) {
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

    public Long getScore() {
        return score;
    }
}
