package utils;

import engine.PlatformActions;
import inputHandler.ActionInput;
import inputHandler.SortInput;
import user.Movie;

import java.util.Comparator;

public class DurationMovieComparator implements Comparator<Movie> {

  @Override
  public int compare(final Movie o1, final Movie o2) {
    ActionInput currentAction = PlatformActions.getCurrentAction();
    SortInput sort = currentAction.getFilters().getSort();
    if (sort.getDuration().equals("decreasing")) {
      return Integer.compare(o2.getDuration(), o1.getDuration());
    } else if (sort.getDuration().equals("increasing")) {
      return Integer.compare(o1.getDuration(), o2.getDuration());
    } else {
      return 0;
    }
  }
}

