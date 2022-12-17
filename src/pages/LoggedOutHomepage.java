package pages;

import engine.PlatformEngine;
import utils.OutputHandler;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.REGISTER_PAGE;

public final class LoggedOutHomepage implements Page {
  @Override
  public void changePage(final String nextPage) {
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

    OutputHandler.updateOutput(ERROR_STATUS);
  }
}
