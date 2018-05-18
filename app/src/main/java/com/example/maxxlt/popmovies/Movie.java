package com.example.maxxlt.popmovies;

public class Movie {
    private String thumbnail, movieTitle,releaseDate,backdropPath,overview;
    private double voteCount;
    private int movieID;

    public Movie() {
    }

    public Movie(String thumbnail, String movieTitle, String releaseDate, String backdropPath, String overview, double voteCount, int movieID) {
        this.thumbnail = thumbnail;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteCount = voteCount;
        this.movieID = movieID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPathPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(double voteCount) {
        this.voteCount = voteCount;
    }
}
