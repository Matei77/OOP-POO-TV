package pages.pageTypes;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.CredentialsInput;
import pages.Page;
import pages.PageFactory;
import utils.OutputHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.REGISTER_PAGE;
import static utils.Constants.SUCCESS_STATUS;

public final class RegisterPage implements Page {

  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_OUT_HOMEPAGE));
      return;
    }

    if (nextPage.equals(LOGIN_PAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGIN_PAGE));
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      return;
    }

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void register() {
    CredentialsInput credentials = PlatformActions.getCurrentAction().getCredentials();

    // check if user is already in the database
    if (Utils.findUser(credentials.getName()) != null) {
      PlatformEngine.getEngine().setCurrentPage(new LoggedOutHomepage());
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

    PageFactory pageFactory = new PageFactory();
    PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_IN_HOMEPAGE));

    // output the success of the action
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
