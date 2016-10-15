package techt5ve.com.capturemostsmile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class MarkView extends FrameLayout {
    private static final int DEFAULT_ITEM_LAYOUT = R.layout.mark_item;
    private Paint mPaint;

    private List<Mark> mMarks = new ArrayList<>();

    public MarkView(Context context) {
        this(context, null);
    }

    public MarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void addItem(final Mark item) {
        mMarks.add(item);
        final View view = LayoutInflater.from(getContext()).inflate(DEFAULT_ITEM_LAYOUT, this, false);
        ((ImageView) view.findViewById(R.id.mark_emoji)).setImageResource(item.getDrawableRes(getResources()));
        ((TextView) view.findViewById(R.id.mark_label)).setText(item.getLable(getResources()));

        view.setAlpha(0);
        view.setTranslationY(200);
        view.setScaleX(0.1f);
        view.setScaleY(0.1f);
        addView(view);
        invalidate();

        RectF rect = item.getRect();
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.leftMargin = (int) (rect.left);
        lp.topMargin = (int) (rect.top - 260);
        view.setLayoutParams(lp);
        view.animate().alpha(1).translationY(0).scaleX(0.6f).scaleY(0.6f)
                .setStartDelay(mMarks.indexOf(item) * 150)
                .setDuration(1000)
                .setInterpolator(new OvershootInterpolator(3.f))
                .start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.mark_stroke, getContext().getTheme()));
        for (Mark mark : mMarks) {
            canvas.drawRect(mark.getRect(), mPaint);
        }
    }

    public void clear() {
        mMarks.clear();
        removeAllViews();
    }

}
