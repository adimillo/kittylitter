package com.whatscat.drpanda.whatscat;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class CatLoader extends AsyncTask<String, Void, String[]> {

    final ImageView mMainCat;
    final ProgressBar mSpinner;
    Drawable mImage = null;
    Random mRand = new Random();

    public CatLoader(View mainView) {;
        mMainCat = (ImageView)mainView.findViewById(R.id.mainCat);
        mSpinner = (ProgressBar)mainView.findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE);
        mMainCat.setVisibility(View.VISIBLE);
        mRand = new Random();
    }

    @Override
    protected String[] doInBackground(String... params) {

        try{
            String url = getCatUrl(params[0]);
            mImage = ImageOperations(url);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        mSpinner.setVisibility(View.GONE);
        mMainCat.setImageDrawable(mImage);
        mMainCat.setVisibility(View.VISIBLE);
        super.onPostExecute(strings);
    }

    @Override
    protected void onPreExecute() {
        mSpinner.setVisibility(View.VISIBLE);
        mMainCat.setVisibility(View.GONE);
        super.onPreExecute();
    }

    private Drawable ImageOperations(String url) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            return Drawable.createFromStream(is, "src");
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Object fetch(String address) throws MalformedURLException, IOException {
        URL url = new URL(address);
        return url.getContent();
    }

    private String getCatUrl(String type) {

        int start = mRand.nextInt(60);
        String q = "cat";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String catJsonStr = null;

        switch (type) {
            case "funny":
                q = "funny cat memes";
                break;
            case "angry":
                q = "angry cat";
                break;
            case "happy":
                q = "happy kitten";
                break;
            case "sad":
                q = "cute sad cat";
                break;
        }

        try {
            final String FORECAST_BASE_URL =
                    "https://ajax.googleapis.com/ajax/services/search/images?";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter("v", "1.0")
                    .appendQueryParameter("q", q)
                    .appendQueryParameter("start", Integer.toString(start))
                    .appendQueryParameter("imgtype", "photo")
                    //.appendQueryParameter("as_filetype", "jpg")
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            catJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e("Kitty", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Kitty", "Error closing stream", e);
                }
            }
        }

        try {
            return getCatUrlFromJson(catJsonStr);
        } catch (JSONException e) {
            Log.e("Kitty", e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    private String getCatUrlFromJson(String catJsonStr) throws JSONException {

        JSONObject catJson = new JSONObject(catJsonStr);
        JSONObject responseData = catJson.getJSONObject("responseData");
        JSONArray results = responseData.getJSONArray("results");
        JSONObject singleResult = results.getJSONObject(0);

        return (String)singleResult.get("unescapedUrl");
    }
}
