package techt5ve.com.capturemostsmile;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class Api {
    private static final String TAG = "Api";
    private static EmotionServiceClient sEmotionServiceClient = new EmotionServiceRestClient(BuildConfig.OCP_APIM_SUBSCRIPTION_KEY);
    private static ExecutorService sExecutor = Executors.newCachedThreadPool();

    public static Observable<List<RecognizeResult>> recognize(final InputStream is) {
        return Observable.create(new Observable.OnSubscribe<List<RecognizeResult>>() {
            @Override
            public void call(Subscriber<? super List<RecognizeResult>> subscriber) {
                try {
                    List<RecognizeResult> results = sEmotionServiceClient.recognizeImage(is);
                    if (results != null && results.size() > 0) {
                        subscriber.onNext(results);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception("There is no people."));
                    }
                } catch (EmotionServiceException | IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static void upload(final File file, final double[] values) {
        sExecutor.execute(new Runnable() {
            @Override
            public void run() {
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
                        .add("fear", "" + values[3])
                        .add("happiness", "" + values[4])
                        .add("neutral", "" + values[5])
                        .add("sadness", "" + values[6])
                        .add("surprise", "" + values[7])
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
        });
    }

    public static Observable<Void> writeRecord(final File out, final double[] results) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                double[] stored = {0, 0, 0, 0, 0, 0, 0, 0};
                if (out.exists()) {
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(new FileInputStream(out));
                        for (int i = 0; scanner.hasNextDouble() && i < stored.length; i++) {
                            stored[i] = scanner.nextDouble();
                            stored[i] += results[i];
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        stored = results;
                    } finally {
                        if (scanner != null) {
                            scanner.close();
                        }
                    }
                } else {
                    stored = results;
                }
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new FileOutputStream(out));
                    for (double d : stored) {
                        writer.println(d);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        });
    }
}
