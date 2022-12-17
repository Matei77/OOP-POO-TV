package engine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;
import data.Movie;
import data.User;
import pages.Page;
import utils.Utils;

import java.util.ArrayList;


/**
 * Contains the core elements of the platform. Implemented using the Singleton Design Pattern to
 * only allow one instantiation of the class.
 */
public final class PlatformEngine {
  private static PlatformEngine instance = null;

  private Input inputData;
  private ArrayNode output;

  private ArrayList<Movie> moviesDatabase;
  private ArrayList<User> usersDatabase;

  private User currentUser;
  private ArrayList<Movie> currentMoviesList;
  private Page currentPage;


  private PlatformEngine() { }

  /**
   * @return the PlatformEngine instance
   */
  public static PlatformEngine getEngine() {
    if (instance == null) {
      instance = new PlatformEngine();
    }
    return instance;
  }

  /**
   * Initialize the engine and execute all the actions.
   */
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

  public Page getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(final Page currentPage) {
    this.currentPage = currentPage;
  }
}
