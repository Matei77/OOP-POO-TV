/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package pages.pageTypes;

import data.Movie;
import engine.PlatformEngine;
import pages.Page;
import pages.PageFactory;
import utils.OutputHandler;

import java.util.ArrayList;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.SUCCESS_STATUS;
import static utils.Constants.UPGRADES_PAGE;

/**
 * Represents the homepage when the user is logged in.
 */
public class LoggedInHomepage implements Page {

  /**
   * Go to another page.
   *
   * @param nextPage the page to go to
   */
  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
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

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Log out of the account.
   */
  @Override
  public void logout() {
    // reset the current movies list
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    // remove the current user
    PlatformEngine.getEngine().setCurrentUser(null);

    // change the current page
    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_OUT_HOMEPAGE));
  }

  /**
   * Go to the movies page
   */
  public void gotoMovies() {
    // reset the current movies list
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    // add the movies the user can see
    ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
    String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

    for (Movie movie : moviesDatabase) {
      if (!movie.getCountriesBanned().contains(userCountry)) {
        currentMoviesList.add(movie);
      }
    }

    // set the new current movies list
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    // change the current page
    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(MOVIES_PAGE));

    // output results
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
