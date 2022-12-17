package utils;

import engine.PlatformEngine;
import input.Input;
import input.MovieInput;
import input.UserInput;
import data.Movie;
import data.User;

import java.util.ArrayList;

import static utils.Constants.LOGGED_OUT_HOMEPAGE;

public final class Utils {
  private Utils() { }

  /**
   * Set the starting state of the engine.
   */
  public static void setStartingState() {
    ArrayList<Movie> currentMoviesList = new ArrayList<>();
    PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
    PlatformEngine.getEngine().setCurrentUser(null);
  }

  /**
   * Set the databases for users and movies in the engine.
   */
  public static void setDatabases() {
    Input inputData = PlatformEngine.getEngine().getInputData();

    // create users database
    ArrayList<User> usersDatabase = new ArrayList<>();
    ArrayList<UserInput> inputUsers = inputData.getUsers();
    for (UserInput inputUser : inputUsers) {
      User user = new User(inputUser.getCredentials().getName(),
          inputUser.getCredentials().getPassword(), inputUser.getCredentials().getAccountType(),
          inputUser.getCredentials().getCountry(), inputUser.getCredentials().getBalance());
      usersDatabase.add(user);
    }

    // create movies database
    ArrayList<Movie> moviesDatabase = new ArrayList<>();
    ArrayList<MovieInput> inputMovies = inputData.getMovies();
    for (MovieInput inputMovie : inputMovies) {
      Movie movie = new Movie(inputMovie.getName(), inputMovie.getYear(), inputMovie.getDuration(),
          inputMovie.getGenres(), inputMovie.getActors(), inputMovie.getCountriesBanned());
      moviesDatabase.add(movie);
    }

    // add databases to engine
    PlatformEngine.getEngine().setUsersDatabase(usersDatabase);
    PlatformEngine.getEngine().setMoviesDatabase(moviesDatabase);
  }

  /**
   * Search a movie by name in the database.
   *
   * @param movieName the name of the movie searched
   * @return the Movie instance from the database if found or null otherwise
   */
  public static Movie findMovie(final String movieName) {
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();

    for (Movie movie : currentMoviesList) {
      if (movie.getName().equals(movieName)) {
        return movie;
      }
    }

    return null;
  }

  /**
   * Search a user by name in the database.
   *
   * @param userName the name of the user searched
   * @return the User instance from the database if found or null otherwise
   */
  public static User findUser(final String userName) {
    ArrayList<User> usersDatabase = PlatformEngine.getEngine().getUsersDatabase();

    for (User user : usersDatabase) {
      if (user.getName().equals(userName)) {
        return user;
      }
    }

    return null;
  }
}
