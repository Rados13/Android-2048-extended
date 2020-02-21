package com.example.a2048;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MapCreationPainter extends Drawable {

    private final Paint greyBeginPaint;
    private final Paint redPaint;
    private final Paint greenPaint;
    private Integer[][] mapStructure;
    private Integer mapSize;

    MapCreationPainter(Integer[][] mapStructure, int mapSize) {
        this.mapStructure = mapStructure;
        this.mapSize = mapSize;
        greyBeginPaint = new Paint();
        redPaint = new Paint();
        greenPaint = new Paint();
        greyBeginPaint.setARGB(255, 169, 169, 169);
        redPaint.setARGB(255, 255,0,0);
        greenPaint.setARGB(255, 0, 255, 0);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {


        int width = getBounds().width();
        int oneRect = Math.round(width / mapSize);


        canvas.drawRect(0, 0, oneRect * mapSize, oneRect * mapSize, greyBeginPaint);
        double gap = 0.05;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (mapStructure[i][j] == 0) {
                    canvas.drawRect((float) ((j + gap) * oneRect), (float) ((i + gap) * oneRect),
                            (float) ((j + 1 - gap) * oneRect), (float) ((i + 1 - gap) * oneRect), greenPaint);

                } else if (mapStructure[i][j] == -1) {
                    canvas.drawRect((float) ((j + gap) * oneRect), (float) ((i + gap) * oneRect),
                            (float) ((j + 1 - gap) * oneRect), (float) ((i + 1 - gap) * oneRect), redPaint);
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

    String getNewMap(PointF point) {
        int width = getBounds().width();
        int oneRect = Math.round(width / mapSize);
        int i = (int) (point.y / oneRect);
        int j = (int) (point.x / oneRect);
        mapStructure[i][j] = mapStructure[i][j] == 1 ? 0 : -1;
        return MapConverter.arrayToString(mapStructure,mapSize);
    }
}
