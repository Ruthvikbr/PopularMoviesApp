package com.example.android.popularmovies_stage1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class MovieDetails extends AppCompatActivity {



    ImageView mMoviePoster;
    TextView mMovieTitle;
    TextView mMovieOverview;
    TextView mMovieReleaseDate;
    TextView mMovieVoteResults;
    ArrayList<Movie> mMoviesList;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        Intent receivedIntent = getIntent();

        if(receivedIntent.hasExtra(MovieSelection.EXTRA_PARCEL)){
            mMoviesList = receivedIntent.getParcelableArrayListExtra(MovieSelection.EXTRA_PARCEL);

            for(int i = 0; i < mMoviesList.size(); i++){
                Movie currentMovie = mMoviesList.get(i);

            }
        }

        else{
            mMoviesList = null;

        }

        mViewPager =  findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MoviePagerAdapter(this));


        int receivedMovieID = 0;
        if (receivedIntent.hasExtra(MovieSelection.EXTRA_ID))
            receivedMovieID = receivedIntent.getIntExtra(MovieSelection.EXTRA_ID, 0);

        for(int k = 0; k < mMoviesList.size(); k++){
            if(mMoviesList.get(k).getID() == receivedMovieID){
                mViewPager.setCurrentItem(k);
                break;
            }
        }


    }
    public class MoviePagerAdapter extends PagerAdapter{
        private Context mContext;

        public MoviePagerAdapter (Context context){
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position){


            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_movie_details,
                    collection, false);


            mMoviePoster =  layout.findViewById(R.id.iv_movie_poster);
            mMovieTitle =  layout.findViewById(R.id.tv_movie_title);
            mMovieOverview = layout.findViewById(R.id.tv_movie_overview);
            mMovieReleaseDate =  layout.findViewById(R.id.tv_movie_release_date);
            mMovieVoteResults =  layout.findViewById(R.id.tv_movie_vote_results);

            Movie receivedMovie = null;
            if(mMoviesList.size() != 0 && mMoviesList != null) {
                //Obtaining the Movie object with the ViewPager's current position
                receivedMovie = mMoviesList.get(position);


                Picasso.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/w185/" + receivedMovie.getPosterPath())
                        .into(mMoviePoster);

                mMovieTitle.setText(receivedMovie.getTitle());
                mMovieOverview.setText(receivedMovie.getOverview());

                String formattedVoteCount = NumberFormat.getIntegerInstance()
                        .format(receivedMovie.getVoteCount());

                mMovieVoteResults.setText(Float.toString(receivedMovie.getVoteAverage()) + "/10"
                        + " (" + formattedVoteCount + ")");


                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = null;
                try{
                    Date movieReleaseDate = dateFormatter.parse(receivedMovie.getReleaseDate());
                    formattedDate = new SimpleDateFormat("MMMM dd, yyyy").format(movieReleaseDate);
                }
                catch(ParseException pe){
                    pe.printStackTrace();

                }
                mMovieReleaseDate.setText("Release date: " + formattedDate);
            }
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            collection.removeView((View) view);
        }

        @Override
        public int getCount(){
            return mMoviesList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object){
            return view == object;
        }

    }
}
