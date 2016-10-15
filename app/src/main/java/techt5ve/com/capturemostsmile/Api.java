package techt5ve.com.capturemostsmile;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        InputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int read;
            while ((read = is.read(buf)) > 0) {
                os.write(buf, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String imgStr = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("anger", "" + values[0])
                .add("contempt", "" + values[1])
                .add("disgust", "" + values[2])
                .add("fear", "" + values[2])
                .add("happiness", "" + values[2])
                .add("neutral", "" + values[2])
                .add("sadness", "" + values[2])
                .add("surprise", "" + values[2])
                .add("photo", "data:image/jpg;base64," + imgStr)
                .build();
        Request request = new Request.Builder()
                .url("http://python.dog/stream/upload64")
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
