package techt5ve.com.capturemostsmile;

import android.graphics.RectF;
import android.support.annotation.DrawableRes;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class Mark {
    private RecognizeResult mRecognizeResult;
    private float mScale = 1f;

    public Mark(RecognizeResult recognizeResult, float sclae) {
        mRecognizeResult = recognizeResult;
        mScale = sclae;
    }

    @DrawableRes
    public int getDrawableRes() {
        return R.drawable.ic_capture;
    }

    public String getLable() {
        return "";
    }

    public RectF getRect() {
        return new RectF(mRecognizeResult.faceRectangle.left * mScale, mRecognizeResult.faceRectangle.top * mScale,
                (mRecognizeResult.faceRectangle.left + mRecognizeResult.faceRectangle.width) * mScale,
                (mRecognizeResult.faceRectangle.top + mRecognizeResult.faceRectangle.height) * mScale);
    }
}
