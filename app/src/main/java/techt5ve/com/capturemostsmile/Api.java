package techt5ve.com.capturemostsmile;

import android.os.Handler;
import android.os.Looper;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class Api {
    private static final String TAG = "Api";
    public static EmotionServiceClient sEmotionServiceClient = new EmotionServiceRestClient(BuildConfig.OCP_APIM_SUBSCRIPTION_KEY);

    public static void recognize(final InputStream is, final int orgWidth, final int orgHeight, final RecognitionListener listener) {
        new Thread() {
            @Override
            public void run() {
                List<RecognizeResult> results = null;
                try {
                    results = sEmotionServiceClient.recognizeImage(is);
                } catch (EmotionServiceException | IOException e) {
                    e.printStackTrace();
                } finally {
                    final List<RecognizeResult> finalResults = results;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (finalResults != null) {
                                listener.onSuccess(finalResults, orgWidth, orgHeight);
                            } else {
                                listener.onFailure();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    public interface RecognitionListener {
        public void onSuccess(List<RecognizeResult> results, int orgWidth, int orgHeight);

        public void onFailure();
    }

}
