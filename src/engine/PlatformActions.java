package engine;

import input.ActionInput;
import input.CredentialsInput;
import data.Movie;
import data.User;
import utils.comparators.DurationMovieComparator;
import utils.OutputHandler;
import utils.comparators.RatingMovieComparator;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Constants.BUY_PREMIUM_ACCOUNT_FEATURE;
import static utils.Constants.BUY_TOKENS_FEATURE;
import static utils.Constants.CHANGE_PAGE;
import static utils.Constants.ERROR_STATUS;
import static utils.Constants.FILTER_FEATURE;
import static utils.Constants.LIKE_FEATURE;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_FEATURE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MAX_RATING;
import static utils.Constants.MIN_RATING;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.MOVIE_PRICE;
import static utils.Constants.ON_PAGE;
import static utils.Constants.PREMIUM_ACCOUNT;
import static utils.Constants.PREMIUM_ACCOUNT_PRICE;
import static utils.Constants.PURCHASE_FEATURE;
import static utils.Constants.RATE_FEATURE;
import static utils.Constants.REGISTER_FEATURE;
import static utils.Constants.REGISTER_PAGE;
import static utils.Constants.SEARCH_FEATURE;
import static utils.Constants.SEE_DETAILS_PAGE;
import static utils.Constants.SUCCESS_STATUS;
import static utils.Constants.UPGRADES_PAGE;
import static utils.Constants.WATCH_FEATURE;

/**
 * Implements the actions that can be performed on the platform.
 */
public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  /**
   * Executes the actions from the actions ArrayList.
   *
   * @param actions array list of the actions that need to be performed
   */
  public static void executeActions(final ArrayList<ActionInput> actions) {
    for (ActionInput action : actions) {
      currentAction = action;
      String type = action.getType();
      if (type.equals(CHANGE_PAGE)) {
        changePage();
      } else if (type.equals(ON_PAGE)) {

        String feature = action.getFeature();

        switch (feature) {
          case LOGIN_FEATURE -> login();
          case REGISTER_FEATURE -> register();
          case SEARCH_FEATURE -> search();
          case FILTER_FEATURE -> filter();
          case BUY_TOKENS_FEATURE -> buyTokens();
          case BUY_PREMIUM_ACCOUNT_FEATURE -> buyPremium();
          case PURCHASE_FEATURE -> purchase();
          case WATCH_FEATURE -> watch();
          case LIKE_FEATURE -> like();
          case RATE_FEATURE -> rate();
          default -> { }
        }
      }
    }
  }

  private static void changePage() {
    String currentPage = PlatformEngine.getEngine().getCurrentPage();
    String nextPage = currentAction.getPage();

    if ((currentPage.equals(LOGIN_PAGE)
        && !(nextPage.equals(REGISTER_PAGE) || nextPage.equals(LOGGED_OUT_HOMEPAGE)))

        || (currentPage.equals(REGISTER_PAGE)
        && !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(LOGGED_OUT_HOMEPAGE)))

        || (currentPage.equals(LOGGED_OUT_HOMEPAGE)
        && !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(REGISTER_PAGE)))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // check if the logged out page was accessed
    if (nextPage.equals(LOGOUT_PAGE)) {
      PlatformEngine.getEngine().setCurrentUser(null);
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);

      ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();

      currentMoviesList.clear();
      PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

      return;
    }

    if ((currentPage.equals(LOGGED_IN_HOMEPAGE)
        && !(nextPage.equals(MOVIES_PAGE) || nextPage.equals(UPGRADES_PAGE)))

        || (currentPage.equals(MOVIES_PAGE)
        && !(nextPage.equals(LOGGED_IN_HOMEPAGE) || nextPage.equals(SEE_DETAILS_PAGE)
        || nextPage.equals(MOVIES_PAGE)))

        || (currentPage.equals(SEE_DETAILS_PAGE)
        && !(nextPage.equals(LOGGED_IN_HOMEPAGE) || nextPage.equals(MOVIES_PAGE)
        || nextPage.equals(UPGRADES_PAGE)))

        || (currentPage.equals(UPGRADES_PAGE)
        && !(nextPage.equals(LOGGED_IN_HOMEPAGE) || nextPage.equals(MOVIES_PAGE)))) {

      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // check if the see details page was accessed
    if (nextPage.equals(SEE_DETAILS_PAGE)) {
      // check if the selected movie exists in the CurrentMoviesList
      String selectedMovieName = currentAction.getMovie();
      Movie selectedMovie = Utils.findMovie(selectedMovieName);
      if (selectedMovie == null) {
        OutputHandler.updateOutput(ERROR_STATUS);
        return;
      }

      // update the currentMoviesList and the current page
      ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();

      currentMoviesList.clear();
      currentMoviesList.add(selectedMovie);

      PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

      PlatformEngine.getEngine().setCurrentPage(nextPage);
      OutputHandler.updateOutput(SUCCESS_STATUS);
      return;
    }

    if (nextPage.equals(MOVIES_PAGE)) {
      ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
      currentMoviesList.clear();

      ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
      String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

      for (Movie movie : moviesDatabase) {
        if (!movie.getCountriesBanned().contains(userCountry)) {
          currentMoviesList.add(movie);
        }
      }

      PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

      PlatformEngine.getEngine().setCurrentPage(nextPage);
      OutputHandler.updateOutput(SUCCESS_STATUS);
      return;
    }

    if (!PlatformEngine.getEngine().getCurrentMoviesList().isEmpty()) {
      ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
      currentMoviesList.clear();
      PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
    }

    PlatformEngine.getEngine().setCurrentPage(nextPage);
  }

  private static void login() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(LOGIN_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }
    CredentialsInput credentials = currentAction.getCredentials();
    User loginUser = Utils.findUser(credentials.getName());

    if (loginUser == null || !loginUser.getPassword().equals(credentials.getPassword())) {
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    PlatformEngine.getEngine().setCurrentUser(loginUser);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_IN_HOMEPAGE);

    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void register() {
    // check if the action can be performed from the current page
    if (!PlatformEngine.getEngine().getCurrentPage().equals(REGISTER_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    CredentialsInput credentials = currentAction.getCredentials();

    // check if user is already in the database
    if (Utils.findUser(credentials.getName()) != null) {
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // create a new user
    User newUser = new User(credentials.getName(), credentials.getPassword(),
        credentials.getAccountType(), credentials.getCountry(), credentials.getBalance());

    // add the user to the database
    ArrayList<User> usersDatabase = PlatformEngine.getEngine().getUsersDatabase();
    usersDatabase.add(newUser);

    // login the new user
    PlatformEngine.getEngine().setUsersDatabase(usersDatabase);
    PlatformEngine.getEngine().setCurrentUser(newUser);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_IN_HOMEPAGE);

    // output the success of the action
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void search() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(MOVIES_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    String startsWith = currentAction.getStartsWith();

    ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
    String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

    for (Movie movie : moviesDatabase) {
      if (movie.getName().startsWith(startsWith)
          && !movie.getCountriesBanned().contains(userCountry)) {
        currentMoviesList.add(movie);
      }
    }

    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void filter() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(MOVIES_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
    String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

    if (currentAction.getFilters().getContains() != null) {
      ArrayList<String> actors = currentAction.getFilters().getContains().getActors();
      ArrayList<String> genres = currentAction.getFilters().getContains().getGenre();

      if (actors == null && genres == null) {
        for (Movie movie : moviesDatabase) {
          if (!movie.getCountriesBanned().contains(userCountry)) {
            currentMoviesList.add(movie);
          }
        }
      } else if (actors == null) {
        for (Movie movie : moviesDatabase) {
          if (movie.getGenres().containsAll(genres)
              && !movie.getCountriesBanned().contains(userCountry)) {
            currentMoviesList.add(movie);
          }
        }
      } else if (genres == null) {
        for (Movie movie : moviesDatabase) {
          if (movie.getActors().containsAll(actors)
              && !movie.getCountriesBanned().contains(userCountry)) {
            currentMoviesList.add(movie);
          }
        }
      } else {
        for (Movie movie : moviesDatabase) {
          if (movie.getActors().containsAll(actors) && movie.getGenres().containsAll(genres)
              && !movie.getCountriesBanned().contains(userCountry)) {
            currentMoviesList.add(movie);
          }
        }
      }
    } else {
        for (Movie movie : moviesDatabase) {
          if (!movie.getCountriesBanned().contains(userCountry)) {
           currentMoviesList.add(movie);
          }
        }
    }

      if (currentAction.getFilters().getSort() != null) {
        if (currentAction.getFilters().getSort().getRating() != null) {
          Comparator<Movie> ratingMovieComparator = new RatingMovieComparator();
          currentMoviesList.sort(ratingMovieComparator);
        }

        if (currentAction.getFilters().getSort().getDuration() != null) {
          Comparator<Movie> durationMovieComparator = new DurationMovieComparator();
          currentMoviesList.sort(durationMovieComparator);
        }
      }

    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void buyTokens() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(UPGRADES_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }
    int count = currentAction.getCount();
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (currentUser.getBalance() < count) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    currentUser.setBalance(currentUser.getBalance() - count);
    currentUser.setTokensCount(currentUser.getTokensCount() + count);
  }

  private static void buyPremium() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(UPGRADES_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (currentUser.getTokensCount() < PREMIUM_ACCOUNT_PRICE) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentUser.getAccountType().equals(PREMIUM_ACCOUNT)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    currentUser.setAccountType(PREMIUM_ACCOUNT);
    currentUser.setTokensCount(currentUser.getTokensCount() - PREMIUM_ACCOUNT_PRICE);
  }

  private static void purchase() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(SEE_DETAILS_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    User currentUser = PlatformEngine.getEngine().getCurrentUser();
    Movie selectedMovie = PlatformEngine.getEngine().getCurrentMoviesList().get(0);

    if (currentUser.getPurchasedMovies().contains(selectedMovie)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentUser.getAccountType().equals(PREMIUM_ACCOUNT)) {
      int numFreePremMovies = currentUser.getNumFreePremiumMovies();
      if (numFreePremMovies > 0) {
        numFreePremMovies--;
        currentUser.setNumFreePremiumMovies(numFreePremMovies);
      } else {
        if (currentUser.getTokensCount() < MOVIE_PRICE) {
          OutputHandler.updateOutput(ERROR_STATUS);
          return;
        }
        currentUser.setTokensCount(currentUser.getTokensCount() - MOVIE_PRICE);
      }
    } else {
      if (currentUser.getTokensCount() < MOVIE_PRICE) {
        OutputHandler.updateOutput(ERROR_STATUS);
        return;
      }
      currentUser.setTokensCount(currentUser.getTokensCount() - MOVIE_PRICE);
    }

    ArrayList<Movie> purchasedMovies = currentUser.getPurchasedMovies();
    purchasedMovies.add(selectedMovie);
    currentUser.setPurchasedMovies(purchasedMovies);

    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void watch() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(SEE_DETAILS_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    Movie selectedMovie = PlatformEngine.getEngine().getCurrentMoviesList().get(0);
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (!currentUser.getPurchasedMovies().contains(selectedMovie)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (!currentUser.getWatchedMovies().contains(selectedMovie)) {
      ArrayList<Movie> watchedMovies = currentUser.getWatchedMovies();
      watchedMovies.add(selectedMovie);
      currentUser.setWatchedMovies(watchedMovies);
    }
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void like() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(SEE_DETAILS_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    Movie selectedMovie = PlatformEngine.getEngine().getCurrentMoviesList().get(0);
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (!currentUser.getPurchasedMovies().contains(selectedMovie)
        || !currentUser.getWatchedMovies().contains(selectedMovie)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (!currentUser.getLikedMovies().contains(selectedMovie)) {
      ArrayList<Movie> likedMovies = currentUser.getLikedMovies();
      likedMovies.add(selectedMovie);
      currentUser.setLikedMovies(likedMovies);
      selectedMovie.setNumLikes(selectedMovie.getNumLikes() + 1);
    }
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void rate() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(SEE_DETAILS_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    User currentUser = PlatformEngine.getEngine().getCurrentUser();
    Movie selectedMovie = PlatformEngine.getEngine().getCurrentMoviesList().get(0);

    if (!currentUser.getPurchasedMovies().contains(selectedMovie)
        || !currentUser.getWatchedMovies().contains(selectedMovie)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentAction.getRate() > MAX_RATING || currentAction.getRate() < MIN_RATING) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }


    if (!currentUser.getRatedMovies().contains(selectedMovie)) {
      ArrayList<Movie> ratedMovies = currentUser.getRatedMovies();
      ratedMovies.add(selectedMovie);
      currentUser.setRatedMovies(ratedMovies);

      double ratingSum = 0;
      ArrayList<Integer> ratings = selectedMovie.getRatings();
      ratings.add(currentAction.getRate());

      for (Integer rating : ratings) {
        ratingSum += rating;
      }

      selectedMovie.setRatings(ratings);
      selectedMovie.setNumRatings(selectedMovie.getNumRatings() + 1);
      selectedMovie.setRating(ratingSum / selectedMovie.getNumRatings());
    }
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  public static ActionInput getCurrentAction() {
    return currentAction;
  }
}
