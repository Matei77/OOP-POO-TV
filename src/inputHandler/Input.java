package inputHandler;

import java.util.ArrayList;

public final class Input {
  private ArrayList<UserInput> users;
  private ArrayList<MovieInput> movies;
  private ArrayList<ActionInput> actions;


  public ArrayList<UserInput> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<UserInput> users) {
    this.users = users;
  }

  public ArrayList<MovieInput> getMovies() {
    return movies;
  }

  public void setMovies(ArrayList<MovieInput> movies) {
    this.movies = movies;
  }

  public ArrayList<ActionInput> getActions() {
    return actions;
  }

  public void setActions(ArrayList<ActionInput> actions) {
    this.actions = actions;
  }
}
