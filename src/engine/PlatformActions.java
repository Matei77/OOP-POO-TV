package engine;

import inputHandler.ActionInput;
import utils.ErrorHandler;

import java.util.ArrayList;

import static utils.Constants.*;

public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  private static void changePage() {
    String currentPage = PlatformEngine.getEngine().getCurrentPage();
    String nextPage = currentAction.getPage();

    if (currentPage.equals(LOGGED_OUT_HOMEPAGE) &&
        !(nextPage.equals(LOGIN_PAGE) || nextPage.equals(REGISTER_PAGE))) {
      ErrorHandler.updateOutput(ERROR_STATUS);
      return;
    }
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
