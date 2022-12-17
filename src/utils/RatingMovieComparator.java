package utils;

import engine.PlatformActions;
import inputHandler.ActionInput;
import inputHandler.SortInput;
import user.Movie;

import java.util.Comparator;

public class RatingMovieComparator implements Comparator<Movie> {

  @Override
  public int compare(final Movie o1, final Movie o2) {
    ActionInput currentAction = PlatformActions.getCurrentAction();
    SortInput sort = currentAction.getFilters().getSort();
    if (sort.getRating().equals("decreasing")) {
      return Double.compare(o2.getRating(), o1.getRating());
    } else if (sort.getRating().equals("increasing")) {
      return Double.compare(o1.getRating(), o2.getRating());
    } else {
      return 0;
    }
  }
}
