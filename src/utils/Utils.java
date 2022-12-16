package utils;

import inputHandler.Input;
import inputHandler.MovieInput;
import inputHandler.UserInput;
import user.Movie;
import user.User;

import java.util.ArrayList;

public final class Utils {
  private Utils() { }

  public static void setDatabases(Input inputData, ArrayList<Movie> moviesDatabase,
                                  ArrayList<User> usersDatabase) {
    ArrayList<UserInput> inputUsers = inputData.getUsers();
    for (UserInput inputUser : inputUsers) {
      User user = new User(inputUser.getCredentials().getName(),
          inputUser.getCredentials().getPassword(), inputUser.getCredentials().getAccountType(),
          inputUser.getCredentials().getCountry(), inputUser.getCredentials().getBalance());
      usersDatabase.add(user);
    }

    ArrayList<MovieInput> inputMovies = inputData.getMovies();
    for (MovieInput inputMovie : inputMovies) {
      Movie movie = new Movie(inputMovie.getName(), inputMovie.getYear(), inputMovie.getDuration(),
          inputMovie.getGenres(), inputMovie.getActors(), inputMovie.getCountriesBanned());
      moviesDatabase.add(movie);
    }
  }
}
