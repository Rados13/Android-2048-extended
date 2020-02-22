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
        greyBeginPaint.setARGB(255, 170, 170, 170);
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


                    changeGreyColor(mapStructure[i][j]);

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

    private void changeGreyColor(int val) {
        int red = 170;
        int green = 170;
        int blue = 170;
        int changeR = 6;
        int changeG = -30;
        int changeB = -30;
        while (val > 1) {
            red += changeR;
            green += changeG;
            blue += changeB;
            if (red == 200 && blue == 20 && green == 20) {
                changeG = 42;
                changeB = -6;
                blue += 10;
            } else if (red == 230 && blue == 0 && green == 230) {
                blue += 10;
                changeR = -40;
                changeB = 4;
                changeG = 0;
            } else if (red == 30 && blue == 30 && green == 230) {
                green -= 30;
                green += 20;
                changeR = -6;
                changeB = 40;
                changeG = -40;
            } else if (red == 0 && blue == 250 && green == 0) {
                changeR = 10;
                changeB = -40;
                changeG = 10;
            } else if (red == 50 && blue == 50 && green == 50) {
                red = 170;
                green = 170;
                blue = 170;
                changeR = 6;
                changeG = -30;
                changeB = -30;
            }
            val--;
        }
        greyBeginPaint.setARGB(255, red, green, blue);
    }


}
