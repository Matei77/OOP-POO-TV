package engine;

import inputHandler.ActionInput;
import user.Movie;
import utils.OutputHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.*;

public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  private static void changePage() {
    String currentPage = PlatformEngine.getEngine().getCurrentPage();
    String nextPage = currentAction.getPage();

    if (currentPage.equals(LOGIN_PAGE) || currentPage.equals(REGISTER_PAGE)) {
      OutputHandler.updateOutput(ERROR_STATUS);
    }

    if (currentPage.equals(LOGGED_OUT_HOMEPAGE) &&
        !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(REGISTER_PAGE))) {
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

  public static void executeActions(final ArrayList<ActionInput> actions) {
    for (ActionInput action : actions) {
      currentAction = action;
      String type = action.getType();
      if (type.equals(CHANGE_PAGE)) {
        changePage();
      }
      // TODO execute actions
    }
  }

  public static ActionInput getCurrentAction() {
    return currentAction;
  }
}
