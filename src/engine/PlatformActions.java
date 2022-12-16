package engine;

import inputHandler.ActionInput;

import java.util.ArrayList;

public final class PlatformActions {

  private static ActionInput currentAction;

  private PlatformActions() { }

  public static void executeActions(final ArrayList<ActionInput> actions) {
    for (ActionInput action : actions) {
      currentAction = action;
      String type = action.getType();

      // TODO execute actions
    }
  }
}
