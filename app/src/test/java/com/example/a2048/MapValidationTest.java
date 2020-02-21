package com.example.a2048;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapValidationTest {

    @Test
    public void mapValid() {
        String[] inputs = {"1011101110111011","0001000001110001111101111111011111000111000001000","0111010101010001"};
        int[] sizes = {4,7,4};
        boolean[] expectations = {false,true,true};
        for(int i=0;i<inputs.length;i++){
            assertEquals(MapValidation.mapValid(inputs[i],sizes[i]),expectations[i]);
        }
    }

}