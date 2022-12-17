package pages;

import engine.PlatformEngine;
import utils.OutputHandler;

import static utils.Constants.*;

public final class LoggedOutHomepage extends Page {
  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(LOGIN_PAGE)) {
      Page loginPage = new LoginPage();
      PlatformEngine.getEngine().setCurrentPage(loginPage);
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      Page registerPage = new RegisterPage();
      PlatformEngine.getEngine().setCurrentPage(registerPage);
      return;
    }

    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      return;
    }

    OutputHandler.updateOutput(ERROR_STATUS);
  }
}
