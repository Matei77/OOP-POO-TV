package pages;

import static utils.Constants.LOGGED_IN_HOMEPAGE;
import static utils.Constants.LOGGED_OUT_HOMEPAGE;
import static utils.Constants.LOGIN_PAGE;
import static utils.Constants.MOVIES_PAGE;
import static utils.Constants.REGISTER_PAGE;
import static utils.Constants.SEE_DETAILS_PAGE;
import static utils.Constants.UPGRADES_PAGE;

public final class PageFactory {

  /**
   * Return a new page of the specified type
   *
   * @param pageType the name of the type
   * @return the new page
   */
  public Page getPage(final String pageType) {
    if (pageType == null) {
      return null;
    }

    if (pageType.equals(LOGGED_OUT_HOMEPAGE)) {
      return new LoggedOutHomepage();
    }

    if (pageType.equals(LOGIN_PAGE)) {
      return new LoginPage();
    }

    if (pageType.equals(REGISTER_PAGE)) {
      return new RegisterPage();
    }

    if (pageType.equals(LOGGED_IN_HOMEPAGE)) {
      return new LoggedInHomepage();
    }

    if (pageType.equals(MOVIES_PAGE)) {
      return new MoviesPage();
    }

    if (pageType.equals(UPGRADES_PAGE)) {
      return new UpgradesPage();
    }

    if  (pageType.equals(SEE_DETAILS_PAGE)) {
      return new SeeDetailsPage();
    }

    return null;
  }
}
