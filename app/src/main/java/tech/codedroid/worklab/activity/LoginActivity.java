package tech.codedroid.worklab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import tech.codedroid.worklab.R;

public class LoginActivity extends AppCompatActivity {

    ImageView login_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tech.codedroid.worklab.R.layout.activity_login);
        login_image = findViewById(R.id.login_image);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        Log.d("===================>", "onCreate: "+height);

        login_image.getLayoutParams().height= height/2;



        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }
}