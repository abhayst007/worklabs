package tech.codedroid.worklab.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kotlinx.coroutines.Job;

public class FetchData {

    private static Context context;

    private final String BASE_URL = "https://api.adzuna.com/v1/api/jobs/in/";
    private final String APP_ID = "1892552e";
    private final String APP_KEY = "29df7fbdb4977da22b875b972744e1c5";

    public FetchData(Context context) {
        FetchData.context = context;
    }

    public interface ApiResponseCallback {
        void onSuccess(JSONObject response);

        void onError(VolleyError error);
    }

    public void reqJob(String JobSearch, String Location, final ApiResponseCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Uri.Builder builder = Uri.parse(BASE_URL + "search/1").buildUpon();
        builder.appendQueryParameter("app_id", APP_ID);
        builder.appendQueryParameter("app_key", APP_KEY);
        builder.appendQueryParameter("results_per_page", String.valueOf(100));
//        builder.appendQueryParameter("page", String.valueOf(1));
        builder.appendQueryParameter("what", JobSearch);
        builder.appendQueryParameter("what_and", "Remote "+"Work From home "+" WFH ");
//        builder.appendQueryParameter("what_phrase", JobSearch);
//        builder.appendQueryParameter("what_phrase", "Remote "+"Work From home "+" WFH ");
//        builder.appendQueryParameter("what_or", " Remote " + " Work From Home");
//        builder.appendQueryParameter("what_exclude", " ");
        builder.appendQueryParameter("where", Location);

        String PARAM_URL = builder.build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, PARAM_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("TAG", "onResponse: ===========================>" + response.toString());
                    callback.onSuccess(response);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void requestJob() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Uri.Builder builder = Uri.parse(BASE_URL + "categories").buildUpon();
        builder.appendQueryParameter("app_id", APP_ID);
        builder.appendQueryParameter("app_key", APP_KEY);

        String PARAM_URL = builder.build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, PARAM_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("TAG", "onResponse: ===========================>" + response.toString());

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "" + error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
