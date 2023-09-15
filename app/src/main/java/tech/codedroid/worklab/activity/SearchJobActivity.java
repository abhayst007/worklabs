package tech.codedroid.worklab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Predicate;

import javax.xml.transform.Result;

import tech.codedroid.worklab.R;
import tech.codedroid.worklab.utils.Data;
import tech.codedroid.worklab.utils.FetchData;

public class SearchJobActivity extends AppCompatActivity {

    LinearLayout parent_layout;

    AutoCompleteTextView job_search;
    AutoCompleteTextView place_search;
    ProgressBar progressBar;
    Button job_search_button;

    FrameLayout not_found;


    TextView job_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
//        FetchData fetchData = new FetchData(getApplicationContext());
//        fetchData.requestCategories();
        job_search = findViewById(R.id.job_search_job);
        job_error = findViewById(R.id.job_error_title);
        place_search = findViewById(R.id.job_search_place);
        job_search_button = findViewById(R.id.job_search_button);
        progressBar = findViewById(R.id.search_progressbar);

        not_found = findViewById(R.id.not_found);


        ArrayAdapter<String> adapterJobs = new ArrayAdapter<>(this, R.layout.custom_suggestions, R.id.suggestion_text, Data.job);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, R.layout.custom_suggestions, R.id.suggestion_text, Data.locations);
        job_search.setAdapter(adapterJobs);
        place_search.setAdapter(adapterCity);

        job_search.setThreshold(1);
        place_search.setThreshold(1);


        job_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                job_error.setVisibility(View.GONE);
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
                    job_search_button.performClick();
                    return true;
                }
                return false;
            }
        });

        parent_layout = findViewById(R.id.parent_layout);

        job_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (job_search.getText().toString().trim().equals("")) {
                    job_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (place_search.getText().toString().trim().equals("")) {
                    place_search.setText("India");
                }
                progressBar.setVisibility(View.VISIBLE);

                FetchData fetchData = new FetchData(getApplicationContext());
                fetchData.reqJob(job_search.getText().toString(), place_search.getText().toString(), new FetchData.ApiResponseCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONArray result = response.getJSONArray("results");
                            Log.e("TAG", "onSuccess:======================> " + result.length());
                            if (result.length() == 0) {
                                Log.e("TAG", "onSuccess:======================> yaha chal raha hai");
                                not_found.setVisibility(View.VISIBLE);
                                return;
                            } else {
                                not_found.setVisibility(View.GONE);
                            }

                            Log.e("TAG", "onSuccess:======================> yahasiadhsadhsaduysahodusahdu chal raha hai");
                            parent_layout.removeAllViews();
//                            Log.e("TAG", "onSuccess: ===============>" + result.length());
                            int count = Integer.parseInt(response.getString("count"));
                            addView(parent_layout, result, count);
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }


        });

        Intent recievedintent = getIntent();

        if (recievedintent != null) {
            job_search.setText(recievedintent.getStringExtra("job_search"));
            place_search.setText(recievedintent.getStringExtra("location_search"));

            job_search_button.performClick();
        }


    }


    public void addView(LinearLayout parent_layout, JSONArray res, int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < count; i++) {
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = res.getJSONObject(finalI);
                                View inflated_layout = getLayoutInflater().inflate(R.layout.job_listview, null);
                                TextView job_title = inflated_layout.findViewById(R.id.job_title);
                                TextView job_company = inflated_layout.findViewById(R.id.job_company);
                                TextView job_location = inflated_layout.findViewById(R.id.job_location);
                                TextView job_desc = inflated_layout.findViewById(R.id.job_desc);
                                Button job_apply = inflated_layout.findViewById(R.id.job_apply);

                                job_title.setText(obj.getString("title"));
                                job_company.setText(obj.getJSONObject("company").getString("display_name"));
                                job_location.setText(obj.getJSONObject("location").getString("display_name"));


                                String desc = obj.getString("description");

                                String[] words = desc.split("\\s+");
                                int no_words = 20;

                                StringBuilder short_desc = new StringBuilder();

                                for (int i = 0; i < Math.min(no_words, words.length); i++) {
                                    short_desc.append(words[i]).append(" "); // Add a space between words
                                }

                                job_desc.setText(short_desc.toString() + "...");

                                job_apply.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            Log.e("TAG", "onClick: ============>" + obj.getString("redirect_url"));

                                            String uri = obj.getString("redirect_url");
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });

                                parent_layout.addView(inflated_layout);
                            } catch (Exception e) {
                                Log.e("TAG", "run: " + e.toString());
                            }

                        }
                    });
                }
            }
        }).start();
    }


}