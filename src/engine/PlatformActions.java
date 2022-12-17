/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package engine;

import input.ActionInput;
import pages.Page;

import java.util.ArrayList;

import static utils.Constants.BUY_PREMIUM_ACCOUNT_FEATURE;
import static utils.Constants.BUY_TOKENS_FEATURE;
import static utils.Constants.CHANGE_PAGE;
import static utils.Constants.FILTER_FEATURE;
import static utils.Constants.LIKE_FEATURE;
import static utils.Constants.LOGIN_FEATURE;
import static utils.Constants.ON_PAGE;
import static utils.Constants.PURCHASE_FEATURE;
import static utils.Constants.RATE_FEATURE;
import static utils.Constants.REGISTER_FEATURE;
import static utils.Constants.SEARCH_FEATURE;
import static utils.Constants.WATCH_FEATURE;

/**
 * Implements the execution of actions that can be performed on the platform.
 */
public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  /**
   * Executes the actions from the actions ArrayList.
   *
   * @param actions array list of the actions that need to be performed
   */
  public static void executeActions(final ArrayList<ActionInput> actions) {
    for (ActionInput action : actions) {
      currentAction = action;
      Page currentPage = PlatformEngine.getEngine().getCurrentPage();

      String type = action.getType();
      if (type.equals(CHANGE_PAGE)) {
        currentPage.changePage(action.getPage());
      } else if (type.equals(ON_PAGE)) {

        String feature = action.getFeature();

        switch (feature) {
          case LOGIN_FEATURE -> currentPage.login();
          case REGISTER_FEATURE -> currentPage.register();
          case SEARCH_FEATURE -> currentPage.search();
          case FILTER_FEATURE -> currentPage.filter();
          case BUY_TOKENS_FEATURE -> currentPage.buyTokens();
          case BUY_PREMIUM_ACCOUNT_FEATURE -> currentPage.buyPremium();
          case PURCHASE_FEATURE -> currentPage.purchase();
          case WATCH_FEATURE -> currentPage.watch();
          case LIKE_FEATURE -> currentPage.like();
          case RATE_FEATURE -> currentPage.rate();
          default -> { }
        }
      }
    }
  }

  public static ActionInput getCurrentAction() {
    return currentAction;
  }
}
