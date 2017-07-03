package com.example.dennissnov.popularmovies;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by dennissnov on 7/2/2017.
 */

public class MovieDetail extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    public ResultsJson mMovie;
    ImageView mBackdrop;
    ImageView mPoster;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mDescription;
    RatingBar mRatingBar;
    public static final String POSTER_URL_BASE_PATH ="http://image.tmdb.org/t/p/w185";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent movieIntent = getIntent();
        if(movieIntent.hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        }else {
            throw new IllegalArgumentException("Detail Activity must Parcelable");

        }
        mBackdrop = (ImageView) findViewById(R.id.iv_backdrop);
        mReleaseDate = (TextView) findViewById(R.id.tv_releaseDate);
        mTitle = (TextView) findViewById(R.id.tv_movie_title);
        mDescription = (TextView) findViewById(R.id.tv_movie_description);
        mPoster = (ImageView) findViewById(R.id.iv_movie_poster);
        mRatingBar = (RatingBar) findViewById(R.id.rb_ratingBar);
        mRating = (TextView) findViewById(R.id.tv_rating);

        String stringRating = String.valueOf(mMovie.getVote_average());
        mRating.setText(stringRating + "/10");
        Float ratingBagi = (mMovie.getVote_average()/2);


        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getRelease_date());
        mDescription.setText(mMovie.getOverview());
        mRatingBar.setRating(ratingBagi);
        Picasso.with(this)
                .load(POSTER_URL_BASE_PATH + mMovie.getBackdrop_path())
                .into(mBackdrop);
        Picasso.with(this)
                .load(POSTER_URL_BASE_PATH + mMovie.getPoster_path())
                .into(mPoster);

    }
}
