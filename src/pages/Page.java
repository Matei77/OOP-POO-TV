package pages;

import utils.OutputHandler;

import static utils.Constants.ERROR_STATUS;

/**
 * Interface for a page.
 */
public interface Page {

  /**
   * Go to another page.
   *
   * @param nextPage the page to go to
   */
  default void changePage(String nextPage) {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Log in the platform with an account.
   */
  default void login() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Register a new account on the platform.
   */
  default void register() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Search for movies starting with a certain string.
   */
  default void search() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Filter the movies seen on the platform.
   */
  default void filter() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Purchase the selected movie.
   */
  default void purchase() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Watch the selected movie.
   */
  default void watch() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Like the selected movie.
   */
  default void like() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Rate the selected movie.
   */
  default void rate() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Buy PooTV Premium for the account.
   */
  default void buyPremium() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Buy tokens using the points in balance.
   */
  default void buyTokens() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   * Log out of the account.
   */
  default void logout() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }
}
