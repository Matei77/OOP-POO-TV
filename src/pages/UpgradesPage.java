package pages;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import utils.OutputHandler;

import static utils.Constants.*;
import static utils.Constants.ERROR_STATUS;

public final class UpgradesPage extends LoggedInHomepage {

  @Override
  public void changePage(final String nextPage) {
    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      Page loggedInHomePage = new LoggedInHomepage();
      PlatformEngine.getEngine().setCurrentPage(loggedInHomePage);
      return;
    }

    if (nextPage.equals(LOGOUT_PAGE)) {
      logout();
      return;
    }

    if (nextPage.equals(MOVIES_PAGE)) {
      gotoMovies();
      return;
    }


    if (nextPage.equals(UPGRADES_PAGE)) {
      return;
    }

    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void buyPremium() {
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (currentUser.getTokensCount() < PREMIUM_ACCOUNT_PRICE) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    if (currentUser.getAccountType().equals(PREMIUM_ACCOUNT)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    currentUser.setAccountType(PREMIUM_ACCOUNT);
    currentUser.setTokensCount(currentUser.getTokensCount() - PREMIUM_ACCOUNT_PRICE);
  }

  @Override
  public void buyTokens() {
    int count = PlatformActions.getCurrentAction().getCount();
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    if (currentUser.getBalance() < count) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    currentUser.setBalance(currentUser.getBalance() - count);
    currentUser.setTokensCount(currentUser.getTokensCount() + count);
  }
}
