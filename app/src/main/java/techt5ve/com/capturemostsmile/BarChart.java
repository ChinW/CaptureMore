package techt5ve.com.capturemostsmile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shaoxing on 10/16/16.
 */

public class BarChart extends View {

    private int[] mColors = {0xAAD50000, 0xAAAA00FF, 0xAA311B92, 0xAA1A237E, 0xAA1DE9B6, 0xAA558B2F, 0xAA616161, 0xAAF9A825};
    private double[] mValues = {};
    private String[] mLabels = {"anger", "contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"};
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public BarChart(Context context) {
        this(context, null);
    }

    public BarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    public int[] getColors() {
        return mColors;
    }

    public void setColors(int[] colors) {
        mColors = colors;
        invalidate();
    }

    public double[] getValues() {
        return mValues;
    }

    public void setValues(double[] values) {
        mValues = values;
        invalidate();
    }

    public String[] getLabels() {
        return mLabels;
    }

    public void setLabels(String[] labels) {
        mLabels = labels;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLabelsAndBars(canvas);
        drawBaseline(canvas);
    }

    private void drawBaseline(Canvas canvas) {
    }

    private void drawLabelsAndBars(Canvas canvas) {
        if (mValues.length == 0) {
            return;
        }
        mPaint.setTextSize(getResources().getDisplayMetrics().scaledDensity * 10);
        int w = mWidth / mLabels.length;
        double maxV = mValues[0];
        for (int i = 1; i < mValues.length; i++) {
            if (mValues[i] > maxV) {
                maxV = mValues[i];
            }
        }
        float factor = (float) ((mHeight - mPaint.getFontSpacing() * 2) / maxV);
        for (int i = 0; i < mLabels.length; i++) {
            canvas.save();
            canvas.translate(w * i, 0);

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColors[i]);
            RectF rectF = new RectF(w * 0.15f, (float) (mHeight - mValues[i] * factor), w * 0.85f, mHeight);
            canvas.drawRect(rectF, mPaint);

            Rect labelBounds = new Rect();
            mPaint.getTextBounds(mLabels[i], 0, mLabels[i].length(), labelBounds);
            canvas.drawText(mLabels[i], (w - labelBounds.width()) / 2f, rectF.top + mPaint.ascent(), mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0x30000000);
            canvas.drawRect(rectF, mPaint);

            canvas.restore();
        }
    }
}
