package pages.pageTypes;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.CredentialsInput;
import pages.Page;
import pages.PageFactory;
import utils.OutputHandler;
import utils.Utils;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.REGISTER_PAGE;
import static utils.Constants.SUCCESS_STATUS;

/**
 * Represents the page where the user can log in from.
 */
public final class LoginPage implements Page {

  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_OUT_HOMEPAGE));
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(REGISTER_PAGE));
      return;
    }

    if (nextPage.equals(LOGIN_PAGE)) {
      return;
    }

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void login() {
    CredentialsInput credentials = PlatformActions.getCurrentAction().getCredentials();
    User loginUser = Utils.findUser(credentials.getName());

    // check if the user doesn't exist or his password doesn't match
    if (loginUser == null || !loginUser.getPassword().equals(credentials.getPassword())) {
      PlatformEngine.getEngine().setCurrentPage(new LoggedOutHomepage());
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // set the new current user
    PlatformEngine.getEngine().setCurrentUser(loginUser);

    // change the current page
    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_IN_HOMEPAGE));

    // output results
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
