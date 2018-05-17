package com.example.maxxlt.popmovies;

public class Movie {
    private String thumbnail, movieTitle,releaseDate,backdropPath,overview;
    private int voteCount;

    public Movie() {
    }

    public Movie(String thumbnail, String movieTitle, String releaseDate, String backdropPath, String overview, int voteCount) {
        this.thumbnail = thumbnail;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteCount = voteCount;
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
