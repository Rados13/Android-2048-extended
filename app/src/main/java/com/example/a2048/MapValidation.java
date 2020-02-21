package com.example.a2048;

public class MapValidation {


    static boolean mapValid(String stringStructure, int mapSize) {
        Integer[] sets = new Integer[mapSize * mapSize];

        for (int i = 0; i < mapSize * mapSize; i++) {
            sets[i] = stringStructure.charAt(i) == '0' ? -1 : i;
        }
        for (int i = 0; i < mapSize * mapSize; i++) {
            if (sets[i] != -1) {
                int x = i / mapSize;
                int y = i % mapSize;
                if (x - 1 >= 0 && sets[(x - 1) * mapSize + y]!=-1) {
                    union(sets, i, (x - 1) * mapSize + y);
                }
                if (x + 1 < mapSize && sets[(x +1) * mapSize + y]!=-1)
                    union(sets, i, (x + 1) * mapSize + y);
                if (y - 1 >= 0 && sets[x  * mapSize + y-1]!=-1)
                    union(sets, i, x * mapSize + y - 1);
                if (y + 1 < mapSize && sets[x * mapSize + y + 1]!=-1)
                    union(sets, i, x * mapSize + y + 1);
            }
        }
        int min = -2;
        for (int i = 0; i < mapSize * mapSize; i++) {
            if (min == -2 && sets[i] != -1) min = sets[i];
            else if (sets[i] != -1) {
                if (getRepresent(sets, i) != min) return false;
            }
        }
        return true;
    }

    static void union(Integer[] map, int i, int j) {
        int firstRepresent = getRepresent(map, i);
        int secondRepresent = getRepresent(map, j);
        if (firstRepresent == secondRepresent) return;
        else if (firstRepresent < secondRepresent) {
            map[secondRepresent] = firstRepresent;
        } else {
            map[firstRepresent] = secondRepresent;
        }
    }

    static int getRepresent(Integer[] map, int i) {
        if (map[i] == i) return i;
        else {
            map[i] = getRepresent(map, map[i]);
            return map[i];
        }
    }
}