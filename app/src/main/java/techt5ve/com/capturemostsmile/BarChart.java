package techt5ve.com.capturemostsmile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shaoxing on 10/16/16.
 */

public class BarChart extends View {

    private int[] mColors = {0xFFD50000, 0xFFAA00FF, 0xFF311B92, 0xFF1A237E, 0xFF1DE9B6, 0xFF558B2F, 0xFF616161, 0xFFF9A825};
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
        super.onDraw(canvas);
        drawLabels(canvas);
        drawBars(canvas);
        drawBaseline(canvas);
    }

    private void drawBaseline(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFDDDDDD);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);
    }

    private void drawLabels(Canvas canvas) {
        if (mLabels.length == 0) {
            return;
        }
        int w = mWidth / mLabels.length;
        for (int i = 0; i < mLabels.length; i++) {

        }
    }

    private void drawBars(Canvas canvas) {
        if (mValues.length == 0) {
            return;
        }
        canvas.save();
        int w = mWidth / mLabels.length;
        float factor = mHeight*20;
        for (int i = 0; i < mLabels.length; i++) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColors[i]);
            canvas.translate(i * w, 0);
            RectF rectF = new RectF(w * 0.15f, (float) (mHeight - mValues[i] * factor), w * 0.85f, mHeight);
            canvas.drawRect(rectF, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0x50000000);
            canvas.drawRect(rectF, mPaint);
        }
        canvas.restore();
    }
}
