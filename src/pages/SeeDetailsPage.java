package pages;

import data.Movie;
import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.ActionInput;
import utils.OutputHandler;

import java.util.ArrayList;

import static utils.Constants.*;

public final class SeeDetailsPage extends LoggedInHomepage {
  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      Page loggedInHomePage = new LoggedInHomepage();
      PlatformEngine.getEngine().setCurrentPage(loggedInHomePage);
      return;
    }

    if (nextPage.equals(UPGRADES_PAGE)) {
      Page upgradesPage = new UpgradesPage();
      PlatformEngine.getEngine().setCurrentPage(upgradesPage);
      return;
    }

    if (nextPage.equals(MOVIES_PAGE)) {
      gotoMovies();
      return;
    }

    if (nextPage.equals(LOGOUT_PAGE)) {
      logout();
      return;
    }

    if (nextPage.equals(SEE_DETAILS_PAGE)) {
      return;
    }

    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void purchase() {
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

  @Override
  public void watch() {
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

  @Override
  public void like() {
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

  @Override
  public void rate() {
    ActionInput currentAction = PlatformActions.getCurrentAction();
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

      selectedMovie.updateRating(currentAction.getRate());
    }

    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
