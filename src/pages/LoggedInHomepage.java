package pages;

import data.Movie;
import engine.PlatformEngine;
import utils.OutputHandler;

import java.util.ArrayList;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.SUCCESS_STATUS;
import static utils.Constants.UPGRADES_PAGE;

public class LoggedInHomepage implements Page {

  /**
   * @param nextPage the page to go to
   */
  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(MOVIES_PAGE)) {
      gotoMovies();
      return;
    }

    if (nextPage.equals(UPGRADES_PAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(UPGRADES_PAGE));
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

  /**
   *
   */
  @Override
  public void logout() {

    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    PlatformEngine.getEngine().setCurrentUser(null);

    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_OUT_HOMEPAGE));
  }

  /**
   *
   */
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

    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(MOVIES_PAGE));
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
