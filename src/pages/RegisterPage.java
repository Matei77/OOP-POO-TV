package pages;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import input.CredentialsInput;
import utils.OutputHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.*;
import static utils.Constants.ERROR_STATUS;

public final class RegisterPage extends Page {

  @Override
  public void changePage(String nextPage) {
    if (nextPage.equals(LOGGED_OUT_HOMEPAGE)) {
      Page loggedOutHomepage = new LoggedOutHomepage();
      PlatformEngine.getEngine().setCurrentPage(loggedOutHomepage);
      return;
    }

    if (nextPage.equals(LOGIN_PAGE)) {
      Page loginPage = new LoginPage();
      PlatformEngine.getEngine().setCurrentPage(loginPage);
      return;
    }

    if (nextPage.equals(REGISTER_PAGE)) {
      return;
    }

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
    PlatformEngine.getEngine().setCurrentPage(new LoggedInHomepage());

    // output the success of the action
    OutputHandler.updateOutput(SUCCESS_STATUS);
  }
}
