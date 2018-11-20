
package com.example.android.popularmovies_stage1;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    String mTitle,mPosterPath,mReleaseDate,mBackdropPath,mOverview;
    int mID,mVoteCount;
    float mVoteAverage;


    public Movie(String title, int ID, String posterPath, String overview, String releaseDate,
                 int voteCount, float voteAverage, String backdropPath){
        mTitle = title;
        mID = ID;
        mPosterPath = posterPath;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mVoteCount = voteCount;
        mVoteAverage = voteAverage;
        mBackdropPath = backdropPath;
    }

    public Movie(Parcel in){
        mTitle = in.readString();
        mID = in.readInt();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteCount = in.readInt();
        mVoteAverage = in.readFloat();
        mBackdropPath = in.readString();
    }



    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate){
        this.mReleaseDate = mReleaseDate;
    }

    public int getVoteCount(){
        return mVoteCount;
    }

    public void setVoteCount(int mVoteCount){
        this.mVoteCount = mVoteCount;
    }

    public float getVoteAverage(){
        return mVoteAverage;
    }

    public void setVoteAverage(float mVoteAverage){
        this.mVoteAverage = mVoteAverage;
    }

    public String getBackdropPath(){
        return mBackdropPath;
    }

    public void setBackdropPath(String mBackdropPath){
        this.mBackdropPath = mBackdropPath;
    }

    @Override
    public String toString(){
        return "Title: " + mTitle
               + "\nID: " + Integer.toString(mID)
               + "\nPoster path: " + mPosterPath
               + "\nOverview: " + mOverview
               + "\nRelease date: " + mReleaseDate
               + "\nVote count: " + Integer.toString(mVoteCount)
               + "\nVote average: " + Float.toString(mVoteAverage)
               + "\nBackdrop path: " + mBackdropPath + "\n";
    }

    @Override
    public int describeContents(){
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out,int flags){
        out.writeString(mTitle);
        out.writeInt(mID);
        out.writeString(mPosterPath);
        out.writeString(mOverview);
        out.writeString(mReleaseDate);
        out.writeInt(mVoteCount);
        out.writeFloat(mVoteAverage);
        out.writeString(mBackdropPath);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }


        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };

}
