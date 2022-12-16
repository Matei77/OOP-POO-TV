package engine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import inputHandler.ActionInput;
import inputHandler.Input;
import user.Movie;
import user.User;
import utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static utils.Constants.LOGGED_OUT_HOMEPAGE;

public final class PlatformEngine {
  private static PlatformEngine instance = null;

  private Input inputData;
  private ArrayNode output;

  private ArrayList<Movie> moviesDatabase;
  private ArrayList<User> usersDatabase;

  private User currentUser;
  private ArrayList<Movie> currentMoviesList;
  private String currentPage;


  private PlatformEngine() { }

  public static PlatformEngine getEngine() {
    if (instance == null) {
      instance = new PlatformEngine();
    }
    return instance;
  }

  public void runEngine() {
    Utils.setStartingState();
    Utils.setDatabases();
    PlatformActions.executeActions(inputData.getActions());
  }

  public Input getInputData() {
    return inputData;
  }

  public void setInputData(final Input inputData) {
    this.inputData = inputData;
  }

  public ArrayNode getOutput() {
    return output;
  }

  public void setOutput(final ArrayNode output) {
    this.output = output;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(final User currentUser) {
    this.currentUser = currentUser;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(final String currentPage) {
    this.currentPage = currentPage;
  }

  public ArrayList<Movie> getMoviesDatabase() {
    return moviesDatabase;
  }

  public void setMoviesDatabase(final ArrayList<Movie> moviesDatabase) {
    this.moviesDatabase = moviesDatabase;
  }

  public ArrayList<User> getUsersDatabase() {
    return usersDatabase;
  }

  public void setUsersDatabase(final ArrayList<User> usersDatabase) {
    this.usersDatabase = usersDatabase;
  }

  public ArrayList<Movie> getCurrentMoviesList() {
    return currentMoviesList;
  }

  public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
    this.currentMoviesList = currentMoviesList;
  }
}
