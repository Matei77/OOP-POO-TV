package pages;

import data.Movie;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.ActionInput;
import utils.OutputHandler;
import utils.Utils;
import utils.comparators.DurationMovieComparator;
import utils.comparators.RatingMovieComparator;

import java.util.ArrayList;
import java.util.Comparator;

import static utils.Constants.*;

public final class MoviesPage extends LoggedInHomepage {

  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      Page loggedInHomePage = new LoggedInHomepage();
      PlatformEngine.getEngine().setCurrentPage(loggedInHomePage);
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

    // update the currentMoviesList and the current page
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();

    currentMoviesList.clear();
    currentMoviesList.add(selectedMovie);

    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);

    PlatformEngine.getEngine().setCurrentPage(new SeeDetailsPage());
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  @Override
  public void search() {
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

    String startsWith = PlatformActions.getCurrentAction().getStartsWith();

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

  @Override
  public void filter() {
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    currentMoviesList.clear();

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
}