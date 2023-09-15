package tech.codedroid.worklab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import tech.codedroid.worklab.R;

public class HomeActivity extends AppCompatActivity {

    AutoCompleteTextView job_search;
    AutoCompleteTextView place_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);

        job_search = findViewById(R.id.home_search_job);

        place_search = findViewById(R.id.home_search_place);




        String[] suggestions = {"Android Developer", "Manager", "Procut Manager"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_suggestions, R.id.suggestion_text, suggestions);
        job_search.setAdapter(adapter);
        place_search.setAdapter(adapter);

        job_search.setThreshold(1);
        place_search.setThreshold(1);


    }


}