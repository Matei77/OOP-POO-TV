package pages;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.CredentialsInput;
import utils.OutputHandler;
import utils.Utils;

import static utils.Constants.*;

public final class LoginPage extends Page {

  @Override
  public void changePage(String nextPage) {
    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      Page loggedOutHomepage = new LoggedOutHomepage();
      PlatformEngine.getEngine().setCurrentPage(loggedOutHomepage);
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      Page registerPage = new RegisterPage();
      PlatformEngine.getEngine().setCurrentPage(registerPage);
      return;
    }

    if (nextPage.equals(LOGIN_PAGE)) {
      return;
    }

    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void login() {
    CredentialsInput credentials = PlatformActions.getCurrentAction().getCredentials();
    User loginUser = Utils.findUser(credentials.getName());

    if (loginUser == null || !loginUser.getPassword().equals(credentials.getPassword())) {
      PlatformEngine.getEngine().setCurrentPage(new LoggedOutHomepage());
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    PlatformEngine.getEngine().setCurrentUser(loginUser);
    PlatformEngine.getEngine().setCurrentPage(new LoggedInHomepage());

    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
