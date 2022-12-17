/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package utils.comparators;

import engine.PlatformActions;
import input.ActionInput;
import input.SortInput;
import data.Movie;

import java.util.Comparator;

/**
 * Comparator for movie duration.
 */
public final class DurationMovieComparator implements Comparator<Movie> {

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

