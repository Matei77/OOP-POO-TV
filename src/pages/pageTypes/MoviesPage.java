package pages.pageTypes;

import data.Movie;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.ActionInput;
import pages.PageFactory;
import utils.OutputHandler;
import utils.Utils;
import utils.comparators.DurationMovieComparator;
import utils.comparators.RatingMovieComparator;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.SEE_DETAILS_PAGE;
import static utils.Constants.SUCCESS_STATUS;

public final class MoviesPage extends LoggedInHomepage {

  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_IN_HOMEPAGE));
      return;
    }

    if (nextPage.equals(SEE_DETAILS_PAGE)) {
      seeDetails();
      return;
    }

    if (nextPage.equals(LOGOUT_PAGE)) {
      logout();
      return;
    }

    if (nextPage.equals(MOVIES_PAGE)) {
      gotoMovies();
      return;
    }

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  private void seeDetails() {
    // check if the selected movie exists in the CurrentMoviesList
    String selectedMovieName = PlatformActions.getCurrentAction().getMovie();
    Movie selectedMovie = Utils.findMovie(selectedMovieName);
    if (selectedMovie == null) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // update the currentMoviesList
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();
    currentMoviesList.add(selectedMovie);

    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    // update the current page
    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(SEE_DETAILS_PAGE));

    // output results
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  @Override
  public void search() {
    // reset the currentMoviesList
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    // add the movies that start with the selected string
    String startsWith = PlatformActions.getCurrentAction().getStartsWith();

    ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
    String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

    for (Movie movie : moviesDatabase) {
      if (movie.getName().startsWith(startsWith)
          && !movie.getCountriesBanned().contains(userCountry)) {
        currentMoviesList.add(movie);
      }
    }

    // update the currentMoviesList
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    // output results
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  @Override
  public void filter() {
    // reset the currentMoviesList
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    // add the movies that match the filters
    ArrayList<Movie> moviesDatabase = PlatformEngine.getEngine().getMoviesDatabase();
    String userCountry = PlatformEngine.getEngine().getCurrentUser().getCountry();

    ActionInput currentAction = PlatformActions.getCurrentAction();

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

    // sort the results according to the user preference
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

    // update the list of movies the user can see on the screen
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    // output results
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
