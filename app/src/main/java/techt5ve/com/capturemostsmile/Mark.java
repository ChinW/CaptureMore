package techt5ve.com.capturemostsmile;

import android.content.res.Resources;
import android.graphics.RectF;
import android.icu.text.DateFormat;
import android.support.annotation.DrawableRes;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;

import java.lang.reflect.Field;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class Mark {
    private RecognizeResult mRecognizeResult;
    private float mScale = 1f;

    public Mark(RecognizeResult recognizeResult, float scale) {
        mRecognizeResult = recognizeResult;
        mScale = scale;
    }

    @DrawableRes
    public int getDrawableRes(Resources resources) {
        return resources.getIdentifier(getDrawableName(resources), "drawable", BuildConfig.APPLICATION_ID);
    }

    public String getLable(Resources resources) {
        return getDrawableName(resources);
    }

    public RectF getRect() {
        return new RectF(mRecognizeResult.faceRectangle.left * mScale, mRecognizeResult.faceRectangle.top * mScale,
                (mRecognizeResult.faceRectangle.left + mRecognizeResult.faceRectangle.width) * mScale,
                (mRecognizeResult.faceRectangle.top + mRecognizeResult.faceRectangle.height) * mScale);
    }

    private String getDrawableName(Resources resources) {
        double max = mRecognizeResult.scores.anger;
        String prefix = "anger_";

        if (mRecognizeResult.scores.contempt > max) {
            max = mRecognizeResult.scores.contempt;
            prefix = "contempt_";
        }
        if (mRecognizeResult.scores.disgust > max) {
            max = mRecognizeResult.scores.disgust;
            prefix = "disgust_";
        }
        if (mRecognizeResult.scores.fear > max) {
            max = mRecognizeResult.scores.fear;
            prefix = "fear_";
        }
        if (mRecognizeResult.scores.happiness > max) {
            max = mRecognizeResult.scores.happiness;
            prefix = "happiness_";
        }
        if (mRecognizeResult.scores.neutral > max) {
            max = mRecognizeResult.scores.neutral;
            prefix = "neutral_";
        }
        if (mRecognizeResult.scores.sadness > max) {
            max = mRecognizeResult.scores.sadness;
            prefix = "sadness_";
        }
        if (mRecognizeResult.scores.surprise > max) {
            max = mRecognizeResult.scores.surprise;
            prefix = "surprise_";
        }

        int i = (int) (max * 10);
        i = (i % 2 == 0) ? i : i + 1;

        return prefix + i;
    }
}
