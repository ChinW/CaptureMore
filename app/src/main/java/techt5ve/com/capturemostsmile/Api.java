package techt5ve.com.capturemostsmile;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public static void upload(File file, double[] values) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("anger", "" + values[0])
                .addFormDataPart("contempt", "" + values[1])
                .addFormDataPart("disgust", "" + values[2])
                .addFormDataPart("fear", "" + values[2])
                .addFormDataPart("happiness", "" + values[2])
                .addFormDataPart("neutral", "" + values[2])
                .addFormDataPart("sadness", "" + values[2])
                .addFormDataPart("surprise", "" + values[2])
                .addFormDataPart("photo", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();
        Request request = new Request.Builder()
                .url("http://python.dog/stream/upload")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response);
            }
        });
    }

}
