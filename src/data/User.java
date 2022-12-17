package data;

import java.util.ArrayList;

import static utils.Constants.FREE_MOVIES;
import static utils.Constants.INITIAL_TOKENS;

public final class User {
  private String name;
  private String password;
  private String accountType;
  private String country;
  private int balance;

  private int numFreePremiumMovies = FREE_MOVIES;
  private int tokensCount = INITIAL_TOKENS;

  private ArrayList<Movie> purchasedMovies;
  private ArrayList<Movie> watchedMovies;
  private ArrayList<Movie> likedMovies;
  private ArrayList<Movie> ratedMovies;

  public User(final String name, final String password, final String accountType,
              final String country, final int balance) {
    this.name = name;
    this.password = password;
    this.accountType = accountType;
    this.country = country;
    this.balance = balance;
    purchasedMovies = new ArrayList<>();
    watchedMovies = new ArrayList<>();
    likedMovies = new ArrayList<>();
    ratedMovies = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(final String accountType) {
    this.accountType = accountType;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(final int balance) {
    this.balance = balance;
  }

  public int getNumFreePremiumMovies() {
    return numFreePremiumMovies;
  }

  public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
    this.numFreePremiumMovies = numFreePremiumMovies;
  }

  public int getTokensCount() {
    return tokensCount;
  }

  public void setTokensCount(final int tokensCount) {
    this.tokensCount = tokensCount;
  }

  public ArrayList<Movie> getPurchasedMovies() {
    return purchasedMovies;
  }

  public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
    this.purchasedMovies = purchasedMovies;
  }

  public ArrayList<Movie> getWatchedMovies() {
    return watchedMovies;
  }

  public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
    this.watchedMovies = watchedMovies;
  }

  public ArrayList<Movie> getLikedMovies() {
    return likedMovies;
  }

  public void setLikedMovies(final ArrayList<Movie> likedMovies) {
    this.likedMovies = likedMovies;
  }

  public ArrayList<Movie> getRatedMovies() {
    return ratedMovies;
  }

  public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
    this.ratedMovies = ratedMovies;
  }
}
