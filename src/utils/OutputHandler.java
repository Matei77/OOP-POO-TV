/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.PlatformEngine;
import data.Movie;
import data.User;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.ERROR_STATUS;

public final class OutputHandler {

  private OutputHandler() {
  }

  /**
   * Add the required data to the output.
   *
   * @param status -1 if an error should be thrown or positive value for success output
   */
  public static void updateOutput(final int status) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode outputNode = mapper.createObjectNode();

    // output the error
    if (status <= ERROR_STATUS) {
      outputNode.put("error", "Error");
      outputNode.set("currentUser", null);
      outputNode.set("currentMoviesList", new ArrayNode(null));

      ArrayNode output = PlatformEngine.getEngine().getOutput();
      output.addAll(List.of(outputNode));
      return;
    }

    outputNode.set("error", null);

    // add current movies list to output
    ArrayList<Movie> currentMoviesList = PlatformEngine.getEngine().getCurrentMoviesList();
    ArrayNode currentMoviesOutput = mapper.createArrayNode();

    for (Movie movie : currentMoviesList) {
      ObjectNode movieObjectNode = createMovieOutput(mapper, movie);
      currentMoviesOutput.add(movieObjectNode);
    }
    outputNode.set("currentMoviesList", currentMoviesOutput);

    // add current user to output
    User currentUser = PlatformEngine.getEngine().getCurrentUser();
    ObjectNode currentUsersOutput = createUserOutput(mapper, currentUser);

    outputNode.set("currentUser", currentUsersOutput);

    // update the final output
    ArrayNode output = PlatformEngine.getEngine().getOutput();
    output.addAll(List.of(outputNode));
  }

  private static ObjectNode createMovieOutput(final ObjectMapper mapper, final Movie movie) {

    // create the output for a movie
    ObjectNode movieObjectNode = mapper.createObjectNode();

    movieObjectNode.put("name", movie.getName());
    movieObjectNode.put("year", movie.getYear());
    movieObjectNode.put("duration", movie.getDuration());
    movieObjectNode.put("numLikes", movie.getNumLikes());
    movieObjectNode.put("rating", movie.getRating());
    movieObjectNode.put("numRatings", movie.getNumRatings());

    // add genres
    ArrayNode genresOutput = mapper.createArrayNode();
    ArrayList<String> genres = movie.getGenres();
    for (String genre : genres) {
      genresOutput.add(genre);
    }
    movieObjectNode.set("genres", genresOutput);

    // add actors
    ArrayNode actorsOutput = mapper.createArrayNode();
    ArrayList<String> actors = movie.getActors();
    for (String actor : actors) {
      actorsOutput.add(actor);
    }
    movieObjectNode.set("actors", actorsOutput);

    // add countries banned
    ArrayNode countriesBannedOutput = mapper.createArrayNode();
    ArrayList<String> countriesBanned = movie.getCountriesBanned();
    for (String country : countriesBanned) {
      countriesBannedOutput.add(country);
    }
    movieObjectNode.set("countriesBanned", countriesBannedOutput);

    // return the created movieObjectNode
    return movieObjectNode;
  }

  private static ObjectNode createUserOutput(final ObjectMapper mapper, final User user) {

    // create the output for a user
    ObjectNode userObjectNode = mapper.createObjectNode();
    if (user == null) {
      return null;
    }

    // add user credentials
    ObjectNode credentialsObjectNode = mapper.createObjectNode();
    credentialsObjectNode.put("name", user.getName());
    credentialsObjectNode.put("password", user.getPassword());
    credentialsObjectNode.put("accountType", user.getAccountType());
    credentialsObjectNode.put("country", user.getCountry());
    credentialsObjectNode.put("balance", Integer.toString(user.getBalance()));

    userObjectNode.set("credentials", credentialsObjectNode);

    userObjectNode.put("tokensCount", user.getTokensCount());
    userObjectNode.put("numFreePremiumMovies", user.getNumFreePremiumMovies());

    // add purchased movies
    ArrayNode purchasedMoviesOutput = mapper.createArrayNode();
    ArrayList<Movie> purchasedMovies = user.getPurchasedMovies();
    for (Movie movie : purchasedMovies) {
      ObjectNode movieObjectNode = createMovieOutput(mapper, movie);
      purchasedMoviesOutput.add(movieObjectNode);
    }
    userObjectNode.set("purchasedMovies", purchasedMoviesOutput);

    // add watched movies
    ArrayNode watchedMoviesOutput = mapper.createArrayNode();
    ArrayList<Movie> watchedMovies = user.getWatchedMovies();
    for (Movie movie : watchedMovies) {
      ObjectNode movieObjectNode = createMovieOutput(mapper, movie);
      watchedMoviesOutput.add(movieObjectNode);
    }
    userObjectNode.set("watchedMovies", watchedMoviesOutput);

    // add liked movies
    ArrayNode likedMoviesOutput = mapper.createArrayNode();
    ArrayList<Movie> likedMovies = user.getLikedMovies();
    for (Movie movie : likedMovies) {
      ObjectNode movieObjectNode = createMovieOutput(mapper, movie);
      likedMoviesOutput.add(movieObjectNode);
    }
    userObjectNode.set("likedMovies", likedMoviesOutput);

    // add rated movies
    ArrayNode ratedMoviesOutput = mapper.createArrayNode();
    ArrayList<Movie> ratedMovies = user.getRatedMovies();
    for (Movie movie : ratedMovies) {
      ObjectNode movieObjectNode = createMovieOutput(mapper, movie);
      ratedMoviesOutput.add(movieObjectNode);
    }
    userObjectNode.set("ratedMovies", ratedMoviesOutput);

    // return the created userObjectNode
    return userObjectNode;
  }
}
