package com.example.a2048;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScorePainter extends Drawable {

    private final Paint darkBrownPaint;
    private final Paint lightBrownPaint;
    private final Paint whitePaint;
    private String whichTextView;
    private String score;

    ScorePainter(String which, String score) {
        whichTextView = which;
        this.score = score;
        this.darkBrownPaint = new Paint();
        this.lightBrownPaint = new Paint();
        this.whitePaint = new Paint();
        darkBrownPaint.setARGB(255, 188, 143, 143);
        lightBrownPaint.setARGB(255, 208, 160, 160);
        whitePaint.setARGB(255, 255, 255, 255);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = getBounds().width();
        int height = getBounds().height();
        canvas.drawRect(0, 0, width, height, darkBrownPaint);


        lightBrownPaint.setTextAlign(Paint.Align.CENTER);
        whitePaint.setTextAlign(Paint.Align.CENTER);
        lightBrownPaint.setTextSize(height / 2);
        whitePaint.setTextSize(height / 2);
        float widthLight = lightBrownPaint.measureText(whichTextView);
        float widthWhite = whitePaint.measureText(score);

        adjustSize(whitePaint, widthWhite, width, score);
        adjustSize(lightBrownPaint, widthLight, width, whichTextView);
        float lightTextSize = lightBrownPaint.getTextSize();
        float whiteTextSize = whitePaint.getTextSize();

        canvas.drawText(whichTextView,
                0.5f * width,
                (height - height/2 + lightTextSize)/2 , lightBrownPaint);

        canvas.drawText(score,
                0.5f * width,
                0.95f*height + (height/2 - whiteTextSize)/2, whitePaint);
    }

    private void adjustSize(Paint color, float widthColor, float width, String text) {
        while (widthColor > 0.8 * width) {
            float textSize = lightBrownPaint.getTextSize();
            color.setTextSize(0.9f * textSize);
            widthColor = color.measureText(text);
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
