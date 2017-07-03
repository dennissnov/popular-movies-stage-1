package com.example.dennissnov.popularmovies;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennissnov on 7/2/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<ResultsJson> mMovieList;
    private LayoutInflater inflater;
    private Context context;
    public static final String IMAGE_URL_BASE_PATH ="http://image.tmdb.org/t/p/w780";

    public MoviesAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_imageMovie;
        public MovieViewHolder(View itemView) {
            super(itemView);
            iv_imageMovie = (ImageView) itemView.findViewById(R.id.iv_imageMovie);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = inflater.inflate(R.layout.row_movie, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posisi = viewHolder.getAdapterPosition();
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra(MovieDetail.EXTRA_MOVIE, mMovieList.get(posisi));
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        //MovieResults movie = mMovieList.get(position);
        String image_url = IMAGE_URL_BASE_PATH + mMovieList.get(position)
                .getPoster_path();
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.iv_imageMovie);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList (List<ResultsJson> movieList) {
        this.mMovieList = new ArrayList<>();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }
}
