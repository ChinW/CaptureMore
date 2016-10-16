package techt5ve.com.capturemostsmile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        TableLayout parent = (TableLayout) findViewById(R.id.table);

        double[] stored = {0, 0, 0, 0, 0, 0, 0, 0};

        File file = new File(getFilesDir(), "data");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            Scanner scanner = new Scanner(is);
            for (int i = 0; i < stored.length && scanner.hasNextDouble(); i++) {
                stored[i] = scanner.nextDouble();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            ViewGroup row = (ViewGroup) parent.getChildAt(i);
            TextView textView = (TextView) row.getChildAt(1);
            textView.setText("" + stored[i]);
        }
    }
}
