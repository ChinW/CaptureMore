package techt5ve.com.capturemostsmile;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okio.BufferedSink;

/**
 * Created by Shaoxing on 10/15/16.
 */

public class OctetStreamRequestBody extends RequestBody {
    public static final String CONTENT_TYPE = "application/octet-stream";

    private String mFilePath;

    public OctetStreamRequestBody(String filePath) {
        mFilePath = filePath;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(CONTENT_TYPE);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(mFilePath);
            os = sink.outputStream();
            byte[] buf = new byte[1024];
            int read = 0;
            while ((read = is.read(buf)) > 0) {
                os.write(buf, 0, read);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}
