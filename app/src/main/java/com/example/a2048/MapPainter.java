package com.example.a2048;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MapPainter extends Drawable {

    private final Paint greyBeginPaint;
    private final Paint brownPaint;
    private Paint darkPaint;
    private String mapStructure;
    private Integer mapSize;

    MapPainter(String mapStructure, int mapSize) {
        this.mapStructure = mapStructure;
        this.mapSize = mapSize;
        greyBeginPaint = new Paint();
        brownPaint = new Paint();
        darkPaint = new Paint();
        greyBeginPaint.setARGB(255, 169, 169, 169);
        brownPaint.setARGB(255, 96, 96, 96);
        darkPaint.setARGB(255, 0, 0, 0);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {


        int width = getBounds().width();
        int height = getBounds().height();
        int oneRect = Math.round(width / mapSize);


        canvas.drawRect(0, 0, oneRect * mapSize, oneRect * mapSize, brownPaint);

        Long valueOfRect;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (mapStructure.charAt(i * mapSize + j) != '0') {
                    valueOfRect = (long) ((int) mapStructure.charAt(i * mapSize + j) - '1');


                    canvas.drawRect((float) ((j + 0.1) * oneRect), (float) ((i + 0.1) * oneRect),
                            (float) ((j + 0.9) * oneRect), (float) ((i + 0.9) * oneRect), greyBeginPaint);


                    valueOfRect = Math.round(Math.pow(2, valueOfRect));

                    if (valueOfRect > 1) {


                        darkPaint.setTextAlign(Paint.Align.CENTER);
                        darkPaint.setTextSize(oneRect / 2);
                        float w = darkPaint.measureText(valueOfRect.toString());
                        float textSize = darkPaint.getTextSize();

                        while(w > 0.8 * oneRect){
                            textSize = darkPaint.getTextSize();
                            darkPaint.setTextSize(0.9f * textSize);
                            w = darkPaint.measureText(valueOfRect.toString());
                        }
                        textSize = darkPaint.getTextSize();
                        canvas.drawText(valueOfRect.toString(),
                                (float) ((j + 0.5) * oneRect ),
                                (float) ((i + 0.5) * oneRect + textSize/4 ), darkPaint);
                    }
                }
            }
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
