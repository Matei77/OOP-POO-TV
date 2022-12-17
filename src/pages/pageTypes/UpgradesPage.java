/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package pages.pageTypes;

import data.User;
import engine.PlatformActions;
import engine.PlatformEngine;
import pages.PageFactory;
import utils.OutputHandler;

import static utils.Constants.ERROR_STATUS;
import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGOUT_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.PREMIUM_ACCOUNT;
import static utils.Constants.PREMIUM_ACCOUNT_PRICE;
import static utils.Constants.UPGRADES_PAGE;

/**
 * Represents the page the user sees when he wants to upgrade his account type or buy tokens.
 */
public final class UpgradesPage extends LoggedInHomepage {

  @Override
  public void changePage(final String nextPage) {

    // change page according to its type
    if (nextPage.equals(LOGGED_IN_HOMEPAGE)) {
      PageFactory pageFactory = new PageFactory();
      PlatformEngine.getEngine().setCurrentPage(pageFactory.getPage(LOGGED_IN_HOMEPAGE));
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

    // output in case of error
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  @Override
  public void buyPremium() {
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    // check if the user has enough tokens to buy premium
    if (currentUser.getTokensCount() < PREMIUM_ACCOUNT_PRICE) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // check if the user's account is already premium
    if (currentUser.getAccountType().equals(PREMIUM_ACCOUNT)) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // update the user's account type and number of tokens
    currentUser.setAccountType(PREMIUM_ACCOUNT);
    currentUser.setTokensCount(currentUser.getTokensCount() - PREMIUM_ACCOUNT_PRICE);
  }

  @Override
  public void buyTokens() {
    int count = PlatformActions.getCurrentAction().getCount();
    User currentUser = PlatformEngine.getEngine().getCurrentUser();

    // check if the user can buy the number of tokens that he wants
    if (currentUser.getBalance() < count) {
      OutputHandler.updateOutput(ERROR_STATUS);
      return;
    }

    // update user's balance and number of tokens
    currentUser.setBalance(currentUser.getBalance() - count);
    currentUser.setTokensCount(currentUser.getTokensCount() + count);
  }
}
