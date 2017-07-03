package com.example.dennissnov.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    //----------------------PUT API KEY HERE------------------------//
    public static String API_KEY = "";
    //-------------------------------------------------------------//
    public static String LANG ="en-US";
    public static String CATEGORY ="popular";
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private ProgressBar mLoadingIndicator;
    public static String titleToolbar;
    public static int mColumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColumn = getResources().getInteger(R.integer.gallery_columns);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,mColumn));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loadingIndicator);
        isInternetConnected();
        if (isInternetConnected() == true) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            getMovies();
        }
    }

    private void getMovies() {

        changeTitle();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApiInterface myInterface = retrofit.create(TmdbApiInterface.class);
        Call<MovieResults> call = myInterface.listOfMovies(CATEGORY,API_KEY,LANG,PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults getMovieDetails = response.body();
                List<ResultsJson> listOfMovies = getMovieDetails.getResults();
                mAdapter.setMovieList(listOfMovies);
                getSupportActionBar().setTitle(titleToolbar);
                mLoadingIndicator.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void changeTitle() {
        titleToolbar = CATEGORY + " Movies";
        titleToolbar = titleToolbar.replace("_", " ").toUpperCase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_popular) {
            CATEGORY = "popular";
            getMovies();
            return true;
        } else if (id == R.id.item_top_rated){
            CATEGORY = "top_rated";
            getMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isInternetConnected() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {

            return false;
        }
    }
}
