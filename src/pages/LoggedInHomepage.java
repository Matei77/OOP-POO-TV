package pages;

import data.Movie;
import engine.PlatformEngine;
import utils.OutputHandler;

import java.util.ArrayList;

import static utils.Constants.*;
import static utils.Constants.ERROR_STATUS;

public class LoggedInHomepage extends Page {

  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(MOVIES_PAGE)) {
      gotoMovies();
      return;
    }

    if (nextPage.equals(UPGRADES_PAGE)) {
      Page upgradesPage = new UpgradesPage();
      PlatformEngine.getEngine().setCurrentPage(upgradesPage);
      return;
    }

    if (nextPage.equals(LOGOUT_PAGE)) {
      logout();
      return;
    }

    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      return;
    }

    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void logout() {

    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    PlatformEngine.getEngine().setCurrentUser(null);
    PlatformEngine.getEngine().setCurrentPage(new LoggedOutHomepage());
  }

  public void gotoMovies() {
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

    PlatformEngine.getEngine().setCurrentPage(new MoviesPage());
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
