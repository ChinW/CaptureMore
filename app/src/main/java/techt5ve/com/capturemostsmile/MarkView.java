package techt5ve.com.capturemostsmile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    public void addItem(Mark item) {
        mMarks.add(item);
        View view = LayoutInflater.from(getContext()).inflate(DEFAULT_ITEM_LAYOUT, this, false);
        ((ImageView) view.findViewById(R.id.mark_emoji)).setImageResource(item.getDrawableRes());
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        RectF rect = item.getRect();
        lp.leftMargin = (int) (rect.left + (rect.width() - lp.width) / 2);
        lp.topMargin = (int) (rect.top -= lp.height);
        view.setLayoutParams(lp);
        addView(view);
        invalidate();
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
