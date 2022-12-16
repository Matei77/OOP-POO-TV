package utils;

import engine.PlatformEngine;
import inputHandler.Input;
import inputHandler.MovieInput;
import inputHandler.UserInput;
import user.Movie;
import user.User;

import java.util.ArrayList;

import static utils.Constants.LOGGED_OUT_HOMEPAGE;

public final class Utils {
  private Utils() { }

  public static void setDatabases() {
    Input inputData = PlatformEngine.getEngine().getInputData();

    ArrayList<User> usersDatabase = new ArrayList<>();
    ArrayList<UserInput> inputUsers = inputData.getUsers();
    for (UserInput inputUser : inputUsers) {
      User user = new User(inputUser.getCredentials().getName(),
          inputUser.getCredentials().getPassword(), inputUser.getCredentials().getAccountType(),
          inputUser.getCredentials().getCountry(), inputUser.getCredentials().getBalance());
      usersDatabase.add(user);
    }

    ArrayList<Movie> moviesDatabase = new ArrayList<>();
    ArrayList<MovieInput> inputMovies = inputData.getMovies();
    for (MovieInput inputMovie : inputMovies) {
      Movie movie = new Movie(inputMovie.getName(), inputMovie.getYear(), inputMovie.getDuration(),
          inputMovie.getGenres(), inputMovie.getActors(), inputMovie.getCountriesBanned());
      moviesDatabase.add(movie);
    }

    PlatformEngine.getEngine().setUsersDatabase(usersDatabase);
    PlatformEngine.getEngine().setMoviesDatabase(moviesDatabase);
  }

  public static Movie findMovie(final String selectedMovieName) {
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();

    for (Movie movie : currentMoviesList) {
      if (movie.getName().equals(selectedMovieName)) {
        return movie;
      }
    }

    return null;
  }

  public static void setStartingState() {
    ArrayList<Movie> currentMoviesList = new ArrayList<>();
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
    PlatformEngine.getEngine().setCurrentUser(null);
  }

  public static User findUser(final String name) {
    ArrayList<User> usersDatabase = PlatformEngine.getEngine().getUsersDatabase();

    for (User user : usersDatabase) {
      if (user.getName().equals(name)) {
        return user;
      }
    }

    return null;
  }
}
