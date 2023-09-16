package tech.codedroid.worklab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tech.codedroid.worklab.R;
import tech.codedroid.worklab.utils.Data;

public class HomeActivity extends AppCompatActivity {

    AutoCompleteTextView job_search;
    AutoCompleteTextView place_search;


    Button search_btn;

    CardView productive;
    CardView career;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);

        job_search = findViewById(R.id.home_search_job);

        place_search = findViewById(R.id.home_search_place);

        productive = findViewById(R.id.productive_card);
        career = findViewById(R.id.career_card);

        error = findViewById(R.id.home_error_title);
        search_btn = findViewById(R.id.home_search_button);


//        ArrayAdapter<String> adapterJobs = new ArrayAdapter<>(this, R.layout.custom_suggestions, R.id.suggestion_text, Data.job);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, R.layout.custom_suggestions, R.id.suggestion_text, Data.locations);
//        job_search.setAdapter(adapterJobs);
        place_search.setAdapter(adapterCity);


//        job_search.setThreshold(1);
        place_search.setThreshold(1);

        job_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                error.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        job_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // Close the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    place_search.requestFocus();
                    return true;
                }
                return false;
            }
        });

        place_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // Close the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    search_btn.performClick();
                    return true;
                }
                return false;
            }
        });


        career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://novoresume.com/career-blog/job-search-tips"));
                startActivity(intent);
            }
        });

        productive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://slack.com/intl/en-in/blog/collaboration/top-strategies-improving-business-productivity"));
                startActivity(intent);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (job_search.getText().toString().trim().equals("")) {
                    error.setVisibility(View.VISIBLE);
                    return;
                }

                Intent intent = new Intent(HomeActivity.this, SearchJobActivity.class);
                intent.putExtra("job_search", job_search.getText().toString());
                intent.putExtra("location_search", place_search.getText().toString());

                startActivity(intent);
            }
        });

    }


}