package engine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import inputHandler.ActionInput;
import inputHandler.Input;

import java.util.ArrayList;

public final class PlatformEngine {
  private static PlatformEngine instance = null;

  private Input inputData;
  private ArrayNode output;

  private PlatformEngine() { }

  public static PlatformEngine getEngine() {
    if (instance == null) {
      instance = new PlatformEngine();
    }
    return instance;
  }

  public void runEngine() {
    ArrayList<ActionInput> actions = inputData.getActions();
    PlatformActions.executeActions(actions);
  }

  public Input getInputData() {
    return inputData;
  }

  public void setInputData(Input inputData) {
    this.inputData = inputData;
  }

  public ArrayNode getOutput() {
    return output;
  }

  public void setOutput(ArrayNode output) {
    this.output = output;
  }
}
