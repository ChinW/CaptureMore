package techt5ve.com.capturemostsmile;

import android.content.res.Resources;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;

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

    public String getLabel(Resources resources) {
        String drawableName = getDrawableName(resources);
        String[] s = null;
        int i = 0;
        if (drawableName.startsWith("neutral")) {
            i = (int) (mRecognizeResult.scores.neutral * 10);
            s = new String[]{"稳若泰山", "胸有成竹", "从容不迫", "慢条斯理", "十拿九稳"};
        } else if (drawableName.startsWith("happiness")) {
            i = (int) (mRecognizeResult.scores.happiness * 10);
            s = new String[]{"神采奕奕", "眉飞色舞", "手舞足蹈", "心花怒放", "怡然自乐"};
        } else if (drawableName.startsWith("surprise")) {
            i = (int) (mRecognizeResult.scores.surprise * 10);
            s = new String[]{"妙语惊人", "目瞪口呆", "大吃一惊", "大惊失色", "瞠目结舌"};
        } else if (drawableName.startsWith("anger")) {
            i = (int) (mRecognizeResult.scores.anger * 10);
            s = new String[]{"火冒三丈", "大动肝火", "气冲牛斗", "怒发冲冠", "暴跳如雷"};
        } else if (drawableName.startsWith("fear")) {
            i = (int) (mRecognizeResult.scores.fear * 10);
            s = new String[]{"栗栗危惧", "胆战心惊", "大惊失色", "寒毛卓竖", "毛骨悚然"};
        }
        if (s == null || i == 0) {
            return "/";
        }
        i = (i % 2 == 0) ? i : i + 1;
        return s[i / 2 - 1];
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
