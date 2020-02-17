package com.example.a2048;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapConverterTest {

    @Test
    public void stringTo2DArray() {
        String [] inputs = {"101111010","111101111"};
        int [] mapsSize = {3,3};
        Integer [][][] expected = {{{0,-1,0},{0,0,0},{-1,0,-1}},{{0,0,0},{0,-1,0},{0,0,0}}};
        for(int i=0;i<inputs.length;i++){
            assertArrayEquals(MapConverter.stringTo2DArray(inputs[i],mapsSize[i]),expected[i]);
        }
    }

    @Test
    public void arrayToString() {
        Integer [][][] inputs = {{{0,-1,0},{0,0,0},{-1,0,-1}},{{0,0,0},{0,-1,0},{0,0,0}}};
        int [] mapsSize = {3,3};
        String [] expected = {"101111010","111101111"};
        for(int i=0;i<inputs.length;i++){
            assertEquals(MapConverter.arrayToString(inputs[i],mapsSize[i]),expected[i]);
        }
    }
}