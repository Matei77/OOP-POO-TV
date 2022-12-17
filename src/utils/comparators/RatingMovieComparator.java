package utils.comparators;

import engine.PlatformActions;
import input.ActionInput;
import input.SortInput;
import data.Movie;

import java.util.Comparator;

public final class RatingMovieComparator implements Comparator<Movie> {

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
