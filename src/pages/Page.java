package pages;

import utils.OutputHandler;

import static utils.Constants.ERROR_STATUS;

public interface Page {

  /**
   * @param nextPage the page to go to
   */
  default void changePage(String nextPage) {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void login() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void register() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void search() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void filter() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void purchase() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void watch() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void like() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void rate() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void buyPremium() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void buyTokens() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }

  /**
   *
   */
  default void logout() {
    OutputHandler.updateOutput(ERROR_STATUS);
  }
}
