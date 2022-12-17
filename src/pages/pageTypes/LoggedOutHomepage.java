/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package pages.pageTypes;

import engine.PlatformEngine;
import pages.Page;
import pages.PageFactory;
import utils.OutputHandler;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.REGISTER_PAGE;

/**
 * Represents the homepage when the user is logged out.
 */
public final class LoggedOutHomepage implements Page {
  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
    if (nextPage.equals(LOGIN_PAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGIN_PAGE));
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(REGISTER_PAGE));
      return;
    }

    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      return;
    }

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }
}
