package engine;

import inputHandler.ActionInput;
import inputHandler.CredentialsInput;
import user.Movie;
import user.User;
import utils.OutputHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.BUY_PREMIUM_ACCOUNT_FEATURE;
import static utils.Constants.BUY_TOKENS_FEATURE;
import static utils.Constants.CHANGE_PAGE;
import static utils.Constants.ERROR_STATUS;
import static utils.Constants.FILTER_FEATURE;
import static utils.Constants.LIKE_FEATURE;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_FEATURE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.ON_PAGE;
import static utils.Constants.PURCHASE_FEATURE;
import static utils.Constants.RATE_FEATURE;
import static utils.Constants.REGISTER_FEATURE;
import static utils.Constants.REGISTER_PAGE;
import static utils.Constants.SEARCH_FEATURE;
import static utils.Constants.SEE_DETAILS_PAGE;
import static utils.Constants.SUCCESS_STATUS;
import static utils.Constants.UPGRADES_PAGE;
import static utils.Constants.WATCH_FEATURE;

public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  public static void executeActions(final ArrayList<ActionInput> actions) {
    for (ActionInput action : actions) {
      currentAction = action;
      String type = action.getType();
      if (type.equals(CHANGE_PAGE)) {
        changePage();
      } else if (type.equals(ON_PAGE)) {
        String feature = action.getFeature();
        switch(feature) {
          case LOGIN_FEATURE -> login();
          case REGISTER_FEATURE -> register();
          case SEARCH_FEATURE -> search();
          case FILTER_FEATURE -> filter();
          case BUY_TOKENS_FEATURE -> buyTokens();
          case BUY_PREMIUM_ACCOUNT_FEATURE -> buyPremium();
          case PURCHASE_FEATURE -> purchase();
          case WATCH_FEATURE -> watch();
          case LIKE_FEATURE -> like();
          case RATE_FEATURE -> rate();
          default -> { }
        }
      }
      // TODO execute actions
    }
  }

  private static void changePage() {
    String currentPage = PlatformEngine.getEngine().getCurrentPage();
    String nextPage = currentAction.getPage();

    if ((currentPage.equals(LOGIN_PAGE)
        && !(nextPage.equals(REGISTER_PAGE) || nextPage.equals(LOGGED_OUT_HOMEPAGE)))

        || (currentPage.equals(REGISTER_PAGE)
        && !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(LOGGED_OUT_HOMEPAGE)))

        || (currentPage.equals(LOGGED_OUT_HOMEPAGE)
        && !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(REGISTER_PAGE)))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }


    if (nextPage.equals(LOGOUT_PAGE)) {
      PlatformEngine.getEngine().setCurrentUser(null);
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
      return;
    }

    if (currentPage.equals(MOVIES_PAGE) && nextPage.equals(SEE_DETAILS_PAGE)) {
      String selectedMovieName = currentAction.getMovie();
      Movie SelectedMovie = Utils.findMovie(selectedMovieName);

      if (SelectedMovie == null) {
        OutputHandler.updateOutput(ERROR_STATUS);
        return;
      }

      ArrayList<Movie> currentMoviesList = new ArrayList<>();
      currentMoviesList.add(SelectedMovie);
      PlatformEngine.getEngine().setCurrentMoviesList(currentMoviesList);
      PlatformEngine.getEngine().setCurrentPage(nextPage);

      OutputHandler.updateOutput(SUCCESS_STATUS);
    }

    if (currentPage.equals(LOGGED_IN_HOMEPAGE) && !(nextPage.equals(MOVIES_PAGE)
        || nextPage.equals(UPGRADES_PAGE))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentPage.equals(MOVIES_PAGE) && !(nextPage.equals(LOGGED_IN_HOMEPAGE)
        || nextPage.equals(SEE_DETAILS_PAGE))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentPage.equals(SEE_DETAILS_PAGE) && !(nextPage.equals(LOGGED_IN_HOMEPAGE)
        || nextPage.equals(MOVIES_PAGE) || nextPage.equals(UPGRADES_PAGE))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentPage.equals(UPGRADES_PAGE) && !(nextPage.equals(LOGGED_IN_HOMEPAGE)
        || nextPage.equals(MOVIES_PAGE))) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    PlatformEngine.getEngine().setCurrentPage(nextPage);
  }

  private static void login() {
    if (!PlatformEngine.getEngine().getCurrentPage().equals(LOGIN_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }
    CredentialsInput credentials = currentAction.getCredentials();
    User loginUser = Utils.findUser(credentials.getName());

    if (loginUser == null || !loginUser.getPassword().equals(credentials.getPassword())) {
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    PlatformEngine.getEngine().setCurrentUser(loginUser);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_IN_HOMEPAGE);

    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void register() {
    // check if the action can be performed from the current page
    if (!PlatformEngine.getEngine().getCurrentPage().equals(REGISTER_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    CredentialsInput credentials = currentAction.getCredentials();

    // check if user is already in the database
    if (Utils.findUser(credentials.getName()) != null) {
      PlatformEngine.getEngine().setCurrentPage(LOGGED_OUT_HOMEPAGE);
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // create a new user
    User newUser = new User(credentials.getName(), credentials.getPassword(),
        credentials.getAccountType(), credentials.getCountry(), credentials.getBalance());

    // add the user to the database
    ArrayList<User> usersDatabase = PlatformEngine.getEngine().getUsersDatabase();
    usersDatabase.add(newUser);

    // login the new user
    PlatformEngine.getEngine().setUsersDatabase(usersDatabase);
    PlatformEngine.getEngine().setCurrentUser(newUser);
    PlatformEngine.getEngine().setCurrentPage(LOGGED_IN_HOMEPAGE);

    // output the success of the action
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }

  private static void search() {

  }

  private static void filter() {

  }

  private static void buyTokens() {

  }

  private static void buyPremium() {

  }

  private static void purchase() {

  }

  private static void watch() {

  }

  private static void like() {

  }

  private static void rate() {

  }

  public static ActionInput getCurrentAction() {
    return currentAction;
  }
}
