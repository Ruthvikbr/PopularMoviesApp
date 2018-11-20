package com.example.android.popularmovies_stage1;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MovieFetcher {
    private static final String TAG = "MovieFetcher";


    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String METHOD_MOVIE_POPULAR = "popular";
    private static final String METHOD_MOVIE_TOP_RATED = "top_rated";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_PAGE = "page";
    private static final String API_KEY = "d728a065dc01feed22564899c3437d24";
    private static final String LANGUAGE = "en-US";

    public static URL buildURL(int methodFlag, int pageNumber){
        String queryMethod;
        switch (methodFlag){
            case 0:
                queryMethod = METHOD_MOVIE_POPULAR;
                break;
            case 1:
                queryMethod = METHOD_MOVIE_TOP_RATED;
                break;
            default:
                queryMethod = METHOD_MOVIE_POPULAR;
                break;
        }

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(queryMethod)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(pageNumber))
                .build();

        URL url = null;

        try{

            url = new URL(builtUri.toString());
            Log.i(TAG, "The resulting built URL: " + url.toString());
        }
        catch (MalformedURLException mue){
            mue.printStackTrace();
        }

        return url;
    }


    public static String getHTTPResponse(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext())
                return scanner.next();
            else
                return null;
        }
        finally {
            connection.disconnect();
        }
    }


    private void parseMovies(String httpResponse, List moviesList) throws JSONException{
        JSONObject jsonRoot = new JSONObject(httpResponse);

        JSONArray jsonResults = jsonRoot.getJSONArray("results");

        for (int i = 0; i < jsonResults.length(); i++){
            JSONObject jsonMovie = jsonResults.getJSONObject(i);
            String title = jsonMovie.getString("title");
            int ID = jsonMovie.getInt("id");
            String posterPath = jsonMovie.getString("poster_path");
            String overview = jsonMovie.getString("overview");
            String releaseDate = jsonMovie.getString("release_date");
            int voteCount = jsonMovie.getInt("vote_count");
            float voteAverage = (float) jsonMovie.getDouble("vote_average");

            String backdropPath = jsonMovie.getString("backdrop_path");

            Movie movie = new Movie(title, ID, posterPath, overview, releaseDate, voteCount,
                    voteAverage, backdropPath);

            moviesList.add(movie);

        }
    }

    public List<Movie> fetchMovies(int methodFlag, int pageNumber){
        List<Movie> movies = new ArrayList<>();
        try {
            String httpResponse = getHTTPResponse(buildURL(methodFlag, pageNumber));
            parseMovies(httpResponse, movies);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(JSONException je){
            je.printStackTrace();
        }
        return movies;
    }
}
