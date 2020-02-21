package com.example.a2048;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MapGamePainter extends Drawable {

    private Paint greyBeginPaint;
    private Paint brownPaint;
    private Paint darkPaint;
    private Integer[][] mapStructure;
    private Integer mapSize;

    MapGamePainter(Integer[][] mapStructure, int mapSize) {
        this.mapStructure = mapStructure;
        this.mapSize = mapSize;
        startSet();
    }

    private void startSet() {
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
        int oneRect = Math.round(width / mapSize);


        double gap = 0.05;
        Long valueOfRect;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (mapStructure[i][j] != -1)
                    canvas.drawRect(j * oneRect, i * oneRect, (j + 1) * oneRect, (i + 1) * oneRect, brownPaint);
                if (mapStructure[i][j] == -2) {
                    canvas.drawRect((float) ((j + gap) * oneRect), (float) ((i + gap) * oneRect),
                            (float) ((j + 1 - gap) * oneRect), (float) ((i + 1 - gap) * oneRect), darkPaint);

                } else if (mapStructure[i][j] >= 0) {


                    canvas.drawRect((float) ((j + gap) * oneRect),
                            (float) ((i + gap) * oneRect),
                            (float) ((j + 1 - gap) * oneRect),
                            (float) ((i + 1 - gap) * oneRect),
                            greyBeginPaint);


                    if (mapStructure[i][j] > 0) {

                        valueOfRect = Math.round(Math.pow(2, mapStructure[i][j]));

                        darkPaint.setTextAlign(Paint.Align.CENTER);
                        darkPaint.setTextSize(oneRect / 2);
                        float w = darkPaint.measureText(valueOfRect.toString());
                        float textSize = darkPaint.getTextSize();

                        while (w > 0.8 * oneRect) {
                            textSize = darkPaint.getTextSize();
                            darkPaint.setTextSize(0.9f * textSize);
                            w = darkPaint.measureText(valueOfRect.toString());
                        }
                        textSize = darkPaint.getTextSize();
                        canvas.drawText(valueOfRect.toString(),
                                (float) (((j + 0.5) * oneRect)),
                                (float) ((i + 0.5) * oneRect + textSize / 4),
                                darkPaint);
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

    Integer[][] getMap() {
        return mapStructure;
    }


}
