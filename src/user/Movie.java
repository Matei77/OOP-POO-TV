package user;

import java.util.ArrayList;

import static utils.Constants.*;

public final class Movie {
  private String name;
  private int year;
  private int duration;
  private ArrayList<String> genres;
  private ArrayList<String> actors;
  private ArrayList<String> countriesBanned;

  private int numLikes = INITIAL_LIKES;
  private double rating = INITIAL_RATING;
  private int numRatings = INITIAL_NUM_RATINGS;
  private ArrayList<Integer> ratings = new ArrayList<>();


  public Movie(final String name, final int year, final int duration,
               final ArrayList<String> genres, final ArrayList<String> actors,
               final ArrayList<String> countriesBanned) {
    this.name = name;
    this.year = year;
    this.duration = duration;
    this.genres = genres;
    this.actors = actors;
    this.countriesBanned = countriesBanned;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getYear() {
    return year;
  }

  public void setYear(final int year) {
    this.year = year;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(final int duration) {
    this.duration = duration;
  }

  public ArrayList<String> getGenres() {
    return genres;
  }

  public void setGenres(final ArrayList<String> genres) {
    this.genres = genres;
  }

  public ArrayList<String> getActors() {
    return actors;
  }

  public void setActors(final ArrayList<String> actors) {
    this.actors = actors;
  }

  public ArrayList<String> getCountriesBanned() {
    return countriesBanned;
  }

  public void setCountriesBanned(final ArrayList<String> countriesBanned) {
    this.countriesBanned = countriesBanned;
  }

  public int getNumLikes() {
    return numLikes;
  }

  public void setNumLikes(final int numLikes) {
    this.numLikes = numLikes;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(final double rating) {
    this.rating = rating;
  }

  public int getNumRatings() {
    return numRatings;
  }

  public void setNumRatings(final int numRatings) {
    this.numRatings = numRatings;
  }

  public ArrayList<Integer> getRatings() {
    return ratings;
  }

  public void setRatings(final ArrayList<Integer> ratings) {
    this.ratings = ratings;
  }
}
