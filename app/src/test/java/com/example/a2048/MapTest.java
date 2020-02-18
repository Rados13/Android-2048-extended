package com.example.a2048;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {

    String [] structures = {"111111111", "1111000011111111","111111111","111111111"};
    Integer [] sizes = {3,4,3,3};

    @Test
    public void swipeTop() {
        String [] maps = {"321123121","1111000022222222","222222232","222222333"};
        String [] expected = {"333121111", "1111000033331111","333232111","333333111"};
        for(int i=0;i<maps.length;i++) {
            Map map = new Map(structures[i], sizes[i], false);
            map.setMapStatus(maps[i],sizes[i]);
            map.swipeTop();

            assertEquals(expected[i], MapConverter.arrayToString(map.getMapStatus(),sizes[i]));
        }

    }

    @Test
    public void swipeBottom() {
        String [] maps = {"321123121","2222111100002222","222222222","222222333"};
        String [] expected = {"111121333", "1111222200002222","111222333","111333333"};
        for(int i=0;i<maps.length;i++) {
            Map map = new Map(structures[i], sizes[i], false);
            map.setMapStatus(maps[i],sizes[i]);
            map.swipeBottom();

            assertEquals(expected[i], MapConverter.arrayToString(map.getMapStatus(),sizes[i]));
        }
    }


//    String [] maps = {"321 123 121","1111 0000 2222 2222","222222222"};

    @Test
    public void swipeRight() {
        String [] maps = {"321123121","2201220122012201","222222222","222222333"};
        String [] expected = {"132123112", "1301130113011301","123123123","123123134"};
        for(int i=0;i<maps.length;i++) {
            Map map = new Map(structures[i], sizes[i], false);
            map.setMapStatus(maps[i],sizes[i]);
            map.swipeRight();

            assertEquals(expected[i], MapConverter.arrayToString(map.getMapStatus(),sizes[i]));
        }
    }

    @Test
    public void swipeLeft() {

        String [] maps = {"321123121","1111000022222222","222222222","222222333"};
        String [] expected = {"321231211", "1111000033113311","321321321", "321321431"};
        for(int i=0;i<maps.length;i++) {
            Map map = new Map(structures[i], sizes[i], false);
            map.setMapStatus(maps[i],sizes[i]);
            map.swipeLeft();

            assertEquals(expected[i], MapConverter.arrayToString(map.getMapStatus(),sizes[i]));
        }
    }
}