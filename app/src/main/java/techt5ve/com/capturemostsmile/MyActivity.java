package techt5ve.com.capturemostsmile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        TableLayout parent = (TableLayout) findViewById(R.id.table);

        double[] stored = {0, 0, 0, 0, 0, 0, 0, 0};

        File file = new File(getExternalFilesDir(null), "data");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
            for (int i = 0; i < stored.length && scanner.hasNextDouble(); i++) {
                stored[i] = scanner.nextDouble();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            ViewGroup row = (ViewGroup) parent.getChildAt(i);
            TextView textView = (TextView) row.getChildAt(1);
            textView.setText("" + stored[i]);
        }
    }
}
